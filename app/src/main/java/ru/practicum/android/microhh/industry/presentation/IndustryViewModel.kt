package ru.practicum.android.microhh.industry.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.Industry
import ru.practicum.android.microhh.core.resources.IndustryState
import ru.practicum.android.microhh.industry.domain.impl.IndustryListUseCase

class IndustryViewModel(
    private val industryList: IndustryListUseCase,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<IndustryState>(IndustryState.Loading)
    val stateFlow: StateFlow<IndustryState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            doRequest()
        }
    }

    private fun updateState(state: IndustryState) {
        _stateFlow.update {
            state
        }
    }

    private suspend fun doRequest() {
        updateState(IndustryState.Loading)

        industryList()
            .collect { result ->
                result.industries?.let { processResult(it, result.error) }
            }
    }

    private fun processResult(industries: List<Industry>, error: Int?) {
        updateState(
            if (industries.isNotEmpty()) {
                IndustryState.Success(industries)
            } else {
                IndustryState.Error(error)
            }
        )
    }
}
