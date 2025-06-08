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
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Util
import ru.practicum.android.microhh.databinding.FragmentFiltersBinding
import ru.practicum.android.microhh.filters.domain.model.FilterSettings
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel
import ru.practicum.android.microhh.search.presentation.ui.SearchFragmentDirections

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
                    if (state.updateState) {
                        updatesUiWithSettings(state.filters)
                    }
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
            toolbar.setOnClickListener { findNavController().popBackStack() }
            parentFragmentManager.setFragmentResultListener(
                Constants.KEY_FILTER_INDUSTRY,
                viewLifecycleOwner
            ) { _, bundle ->
                val catalog = Util.getParcelable(bundle, Constants.KEY_FILTERS)

                industry.tag = catalog?.id ?: ""
                industry.setText(catalog?.name ?: "")
            }
            area.setOnTextChange { _ ->
                saveSettings()
            }
            industry.setOnTextChange { _ ->
                saveSettings()
            }
            industry.setOnClickListener {
                findNavController().navigate(
                    SearchFragmentDirections.openIndustry()
                )
            }
            salary.setOnTextChanged { _ ->
                saveSettings()
            }
            showNoSalary.setOnCheckedChangeListener { _, _ ->
                saveSettings()
            }
            apply.setOnClickListener {
                parentFragmentManager.setFragmentResult(Constants.KEY_FILTERS, Bundle())
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
        }
    }

    private fun updatesUiWithSettings(filters: FilterSettings) {
        with(binding) {
            area.setText(filters.areaName)
            industry.setText(filters.industryName)
            salary.text = filters.salary
            showNoSalary.isChecked = filters.showWithoutSalary
        }
    }

    private fun saveSettings() {
        viewModel.updateSettings(
            FilterSettings(
                areaId = binding.area.tag?.toString() ?: "",
                areaName = binding.area.text,
                industryId = binding.industry.tag?.toString() ?: "",
                industryName = binding.industry.text,
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        updatesUiWithSettings(viewModel.getSettings())
    }
}
