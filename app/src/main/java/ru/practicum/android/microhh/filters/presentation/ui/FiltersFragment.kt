package ru.practicum.android.microhh.filters.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
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
                viewModel.stateFlow.collect { state ->
                    updatesUiWithSettings(state)
                }
            }
        }

        with (binding) {
            toolbar.setOnClickListener { findNavController().popBackStack() }
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

    private fun updatesUiWithSettings(state: FilterSettings) {
        with(binding) {
            salary.text = state.salary
            showNoSalary.isChecked = state.showWithoutSalary
        }
    }

    private fun saveSettings() {
        viewModel.updateSettings(
            true,
            FilterSettings(
                salary = binding.salary.text,
                showWithoutSalary = binding.showNoSalary.isChecked,
            )
        )
    }

    override fun onPause() {
        super.onPause()
        saveSettings()
    }
}
