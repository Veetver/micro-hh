package ru.practicum.android.microhh.filters.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.FiltersButtonState
import ru.practicum.android.microhh.core.resources.FiltersState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.databinding.FragmentFiltersBinding
import ru.practicum.android.microhh.filters.domain.model.FilterSettings
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel

class FiltersFragment : BaseFragment<FragmentFiltersBinding>(FragmentFiltersBinding::inflate) {

    private val viewModel by viewModel<FiltersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filtersStateFlow.collect { state ->
                    updatesUiWithSettings(state)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.buttonsStateFlow.collect { state ->
                    renderState(state)
                }
            }
        }

        with (binding) {
            toolbar.setOnClickListener { findNavController().popBackStack() }
            salary.setOnTextChanged { text ->
                viewModel.updateButtons(viewModel.filterSettings.copy(salary = text))
            }
            showNoSalary.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateButtons(viewModel.filterSettings.copy(showWithoutSalary = isChecked))
            }
            apply.setOnClickListener {
                saveSettings()
                parentFragmentManager.setFragmentResult(Constants.KEY_FILTERS, Bundle())
                findNavController().popBackStack()
            }
            clear.setOnClickListener {
                viewModel.clearSettings()
            }
        }
    }

    private fun updatesUiWithSettings(state: FiltersState) {
        if (state.updateState) {
            with(binding) {
                salary.text = state.filters.salary
                showNoSalary.isChecked = state.filters.showWithoutSalary
            }
        }
    }

    private fun saveSettings() {
        viewModel.updateSettings(
            FilterSettings(
                salary = binding.salary.text,
                showWithoutSalary = binding.showNoSalary.isChecked,
            ),
            updateSettings = true,
            updateFiltersState = false,
        )
    }

    private fun renderState(state: FiltersButtonState) {
        when (state) {
            is FiltersButtonState.Apply -> binding.apply.isVisible = state.isVisible
            is FiltersButtonState.Clear -> binding.clear.isVisible = state.isVisible
            else -> {}
        }
    }

    override fun onPause() {
        super.onPause()
        saveSettings()
    }
}