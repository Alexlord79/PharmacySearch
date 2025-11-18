@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.hwdoc.pharmacysearch.presentation.screen.pharmacies

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters
import ru.hwdoc.pharmacysearch.domain.usecase.CopyToClipboardUseCase
import ru.hwdoc.pharmacysearch.domain.usecase.CreateNavigationIntentUseCase
import ru.hwdoc.pharmacysearch.domain.usecase.CreatePhoneCallIntentUseCase
import ru.hwdoc.pharmacysearch.domain.usecase.GetAllPharmaciesUseCase
import ru.hwdoc.pharmacysearch.domain.usecase.SearchPharmacyUseCase
import ru.hwdoc.pharmacysearch.presentation.screen.pharmacies.PharmaciesUiEvent.*
import ru.hwdoc.pharmacysearch.presentation.screen.util.NavigatorEvent

@HiltViewModel
class PharmaciesViewModel @Inject constructor(
    private val getAllPharmaciesUseCase: GetAllPharmaciesUseCase,
    private val searchPharmacyUseCase: SearchPharmacyUseCase,
    private val createPhoneCallIntentUseCase: CreatePhoneCallIntentUseCase,
    private val createNavigationIntentUseCase: CreateNavigationIntentUseCase,
    private val copyToClipboardUseCase: CopyToClipboardUseCase
): ViewModel() {

    private val filters = MutableStateFlow(PharmacyFilters())

    private val _state = MutableStateFlow(PharmaciesScreenState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableStateFlow<PharmaciesUiEvent?>(null)
    val uiEvent = _uiEvents.asStateFlow()

    init {
        filters
            .onEach {newFilters ->
                _state.update {
                    it.copy(filters = newFilters)
                }
            }
            .flatMapLatest {currentFilters ->
                when {
                    currentFilters.hasQuery() && currentFilters.hasSpecificFilters() -> {
                        searchPharmacyUseCase(currentFilters)
                    }
                    currentFilters.hasQuery() -> {
                        searchPharmacyUseCase(currentFilters.copy(isAll = true))
                    }
                    else -> {
                        getAllPharmaciesUseCase()
                    }
                }
            }
            .onEach {pharmacies ->
                _state.update {
                    it.copy(
                        pharmacies = pharmacies
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: PharmaciesCommand) {
        viewModelScope.launch {
            when(command) {
                is PharmaciesCommand.InputSearchQuery -> {
                    filters.update {
                        it.copy(query = command.filters.query)
                    }
                }
                is PharmaciesCommand.ToggleFilter -> filters.update {
                    it.toggleFilter(command.filterType)
                }
                is PharmaciesCommand.MakePhoneCall -> {
                    val intent = createPhoneCallIntentUseCase(command.phoneNumber)
                    _uiEvents.value = LaunchIntent(intent)
                }
                is PharmaciesCommand.OpenNavigator -> {
                    val mapIntent = when(command.navigatorEvent) {
                        is NavigatorEvent.WithLink -> {
                            createNavigationIntentUseCase(command.navigatorEvent.yandexMapsLink)
                        }
                        is NavigatorEvent.WithAddress -> {
                            createNavigationIntentUseCase(
                                command.navigatorEvent.locality,
                                command.navigatorEvent.address
                            )
                        }
                        is NavigatorEvent.CopiInformation -> {
                            return@launch
                        }
                    }
                    val chooserIntent = Intent.createChooser(mapIntent,"Выберите навинатор")
                    _uiEvents.value = LaunchIntent(chooserIntent)
                }
                PharmaciesCommand.ClearSearch -> {
                    filters.update {pharmacyFilters ->
                        pharmacyFilters.copy(query = "")
                    }
                }
                PharmaciesCommand.ClearUiEvent -> {
                    _uiEvents.value = null
                }

                is PharmaciesCommand.CopyToClipboard -> {
                    when(command.copyEvent) {
                        is NavigatorEvent.CopiInformation -> {
                            _uiEvents.value = PharmaciesUiEvent.CopyToClipboard(command.copyEvent.formattedAddress)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

}

private fun PharmacyFilters.toggleFilter(filterType: FilterType): PharmacyFilters {
//    return when (filterType) {
//        FilterType.ALL -> copy(
//            isAll = !isAll,
//            isNumber = false,
//            isLocality = false,
//            isAddress = false,
//            isPhoneNumber = false,
//            isVsa = false,
//            isInternetProvider = false,
//        )
//        FilterType.NUMBER -> copy(isNumber = !isNumber, isAll = false)
//        FilterType.LOCALITY -> copy(isLocality = !isLocality, isAll = false)
//        FilterType.ADDRESS -> copy(isAddress = !isAddress, isAll = false)
//        FilterType.PHONE -> copy(isPhoneNumber = !isPhoneNumber, isAll = false)
//        FilterType.VSA -> copy(isVsa = !isVsa, isAll = false)
//        FilterType.INTERNET -> copy(isInternetProvider = !isInternetProvider, isAll = false)
//    }
    return copy(
        isAll = filterType == FilterType.ALL,
        isNumber = filterType == FilterType.NUMBER,
        isLocality = filterType == FilterType.LOCALITY,
        isAddress = filterType == FilterType.ADDRESS,
        isPhoneNumber = filterType == FilterType.PHONE,
        isVsa = filterType == FilterType.VSA,
        isInternetProvider = filterType == FilterType.INTERNET
    )
}

enum class FilterType {
    ALL, NUMBER, LOCALITY, ADDRESS, PHONE, VSA, INTERNET
}

sealed interface PharmaciesCommand {

    data class InputSearchQuery(val filters: PharmacyFilters): PharmaciesCommand
    data class ToggleFilter(val filterType: FilterType) : PharmaciesCommand
    data class MakePhoneCall(val phoneNumber: String): PharmaciesCommand
    data class OpenNavigator(val navigatorEvent: NavigatorEvent): PharmaciesCommand
    data class CopyToClipboard(val copyEvent: NavigatorEvent): PharmaciesCommand
    object ClearSearch : PharmaciesCommand
    object ClearUiEvent: PharmaciesCommand
}

sealed class PharmaciesUiEvent {
    data class LaunchIntent(val intent: Intent) : PharmaciesUiEvent()
    data class CopyToClipboard(val text: String) : PharmaciesUiEvent()
}

data class PharmaciesScreenState(
    val filters: PharmacyFilters = PharmacyFilters(),
    val pharmacies: List<Pharmacy> = listOf()
)