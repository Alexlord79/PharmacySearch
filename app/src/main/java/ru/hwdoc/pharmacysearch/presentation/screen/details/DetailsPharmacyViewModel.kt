package ru.hwdoc.pharmacysearch.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.usecase.GetPharmacyByNumberUseCase

@HiltViewModel(assistedFactory = DetailsPharmacyViewModel.Factory::class)
class DetailsPharmacyViewModel @AssistedInject constructor(
    private val getPharmacyByNumberUseCase: GetPharmacyByNumberUseCase,
    @Assisted("number") private val number: Int
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsPharmacyState>(DetailsPharmacyState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                val pharmacy = getPharmacyByNumberUseCase(number)
                DetailsPharmacyState.Success(pharmacy)
            }
        }
    }

    fun processCommand(command: DetailsPharmacyCommand) {
        when (command) {
            DetailsPharmacyCommand.Back -> {
                _state.update {
                    DetailsPharmacyState.Finished
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("number") number: Int
        ): DetailsPharmacyViewModel
    }
}

sealed interface DetailsPharmacyCommand {
    data object Back : DetailsPharmacyCommand
}

sealed interface DetailsPharmacyState {
    data object Initial : DetailsPharmacyState
    data class Success(val pharmacy: Pharmacy) : DetailsPharmacyState
    data object Finished : DetailsPharmacyState
}