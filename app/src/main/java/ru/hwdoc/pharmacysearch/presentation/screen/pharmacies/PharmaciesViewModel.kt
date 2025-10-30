@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.hwdoc.pharmacysearch.presentation.screen.pharmacies

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
import ru.hwdoc.pharmacysearch.domain.usecase.GetAllPharmaciesUseCase
import ru.hwdoc.pharmacysearch.domain.usecase.SearchPharmacyUseCase

@HiltViewModel
class PharmaciesViewModel @Inject constructor(
    private val getAllPharmaciesUseCase: GetAllPharmaciesUseCase,
    private val searchPharmacyUseCase: SearchPharmacyUseCase
): ViewModel() {

    private val filters = MutableStateFlow(PharmacyFilters())
    private val _state = MutableStateFlow(PharmaciesScreenState())
    val state = _state.asStateFlow()

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

                PharmaciesCommand.ClearSearch -> {
                    filters.update {
                        PharmacyFilters()
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
        isAll = filterType == FilterType.ALL && !isAll,
        isNumber = filterType == FilterType.NUMBER && !isNumber,
        isLocality = filterType == FilterType.LOCALITY && !isLocality,
        isAddress = filterType == FilterType.ADDRESS && !isAddress,
        isPhoneNumber = filterType == FilterType.PHONE && !isPhoneNumber,
        isVsa = filterType == FilterType.VSA && !isVsa,
        isInternetProvider = filterType == FilterType.INTERNET && !isInternetProvider
    )
}

enum class FilterType {
    ALL, NUMBER, LOCALITY, ADDRESS, PHONE, VSA, INTERNET
}

sealed interface PharmaciesCommand {

    data class InputSearchQuery(val filters: PharmacyFilters): PharmaciesCommand
    data class ToggleFilter(val filterType: FilterType) : PharmaciesCommand
    object ClearSearch : PharmaciesCommand
}

data class PharmaciesScreenState(
    val filters: PharmacyFilters = PharmacyFilters(),
    val pharmacies: List<Pharmacy> = listOf()
)