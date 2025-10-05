package ru.hwdoc.pharmacysearch.presentation.screen.pharmacies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters
import ru.hwdoc.pharmacysearch.domain.usecase.GetAllPharmaciesUseCase
import ru.hwdoc.pharmacysearch.domain.usecase.GetPharmacyByNumberUseCase
import ru.hwdoc.pharmacysearch.domain.usecase.SearchPharmacyUseCase

@HiltViewModel
class PharmaciesViewModel @Inject constructor(
    private val getAllPharmaciesUseCase: GetAllPharmaciesUseCase,
    private val getPharmacyByNumberUseCase: GetPharmacyByNumberUseCase,
    private val searchPharmacyUseCase: SearchPharmacyUseCase
): ViewModel() {

    private val _state = MutableStateFlow(PharmaciesScreenState())
    val state = _state.asStateFlow()

    fun processCommand(command: PharmaciesCommand) {
        when(command) {
            is PharmaciesCommand.InputSearchQuery -> {
                viewModelScope.launch {
                    _state.update {previousState ->

                    }
                }
            }
        }
    }

}

sealed interface PharmaciesCommand {

    data class InputSearchQuery(val filters: PharmacyFilters): PharmaciesCommand
}

data class PharmaciesScreenState(
    val filters: PharmacyFilters = PharmacyFilters(),
    val pharmacies: List<Pharmacy> = listOf()
)