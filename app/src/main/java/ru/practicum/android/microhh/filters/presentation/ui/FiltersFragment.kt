package ru.practicum.android.microhh.filters.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.FiltersButtonState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.databinding.FragmentFiltersBinding
import ru.practicum.android.microhh.filters.domain.model.FilterSettings
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel
import ru.practicum.android.microhh.search.presentation.ui.SearchFragmentDirections

class FiltersFragment : BaseFragment<FragmentFiltersBinding>(FragmentFiltersBinding::inflate) {

    private val viewModel by activityViewModel<FiltersViewModel>()

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

        with(binding) {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
                viewModel.cancelChanges()
            }

            industry.setOnClickListener {
                findNavController().navigate(
                    SearchFragmentDirections.openIndustry()
                )
            }

            showNoSalary.setOnCheckedChangeListener { _, checked ->
                viewModel.setShowNoSalary(checked)
            }

            salary.setOnTextChanged {
                if (it.isNotBlank()) {
                    viewModel.setSalaryFilter(it)
                } else {
                    viewModel.setSalaryFilter(null)
                }
            }

            apply.setOnClickListener {
                parentFragmentManager.setFragmentResult(Constants.KEY_FILTERS, Bundle())
                viewModel.updateSettings()
                findNavController().popBackStack()
            }

            clear.setOnClickListener {
                viewModel.clearSettings()
            }

            area.setOnClickListener {
                findNavController().navigate(
                    FiltersFragmentDirections.actionFiltersFragmentToWorkplaceFragment()
                )
            }

            area.setOnCleared {
                viewModel.setWorkplaceFilter(null)
            }

            industry.setOnCleared {
                viewModel.setIndustryFilter(null)
            }
        }
    }

    private fun updatesUiWithSettings(filters: FilterSettings) {
        with(binding) {
            area.setText(filters.workplace?.title)
            industry.setText(filters.industry?.name)
            if (filters.salary != binding.salary.text) {
                binding.salary.text = filters.salary ?: ""
            }
            showNoSalary.isChecked = filters.showWithoutSalary
        }
    }

    private fun renderState(state: FiltersButtonState) {
        binding.apply.isVisible = state.isApplyVisible
        binding.clear.isVisible = state.isClearVisible
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        updatesUiWithSettings(viewModel.filterSettings())
    }
}
