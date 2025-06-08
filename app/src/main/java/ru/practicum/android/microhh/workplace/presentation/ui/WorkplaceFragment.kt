package ru.practicum.android.microhh.workplace.presentation.ui

import android.annotation.SuppressLint
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
import ru.practicum.android.microhh.databinding.FragmentWorkplaceBinding
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel
import ru.practicum.android.microhh.workplace.presentation.WorkplaceViewModel

class WorkplaceFragment : BaseFragment<FragmentWorkplaceBinding>(FragmentWorkplaceBinding::inflate) {
    private val viewModel: WorkplaceViewModel by activityViewModel()
    private val filtersViewModel: FiltersViewModel by activityViewModel()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it.workplaceFilter.country != null) {
                        binding.country.setText(it.workplaceFilter.country.name)
                    }

                    if (it.workplaceFilter.region != null) {
                        binding.region.setText(it.workplaceFilter.region.name)
                    }

                    binding.apply.isVisible = it.showApply
                }
            }
        }
    }

    private fun setupListeners() {
        binding.toolbar.setOnClickListener {
            viewModel.cancelChanges()
            findNavController().popBackStack()
        }
        binding.country.setOnClickListener {
            findNavController().navigate(
                WorkplaceFragmentDirections.actionWorkplaceFragmentToCountryFragment()
            )
        }
        binding.region.setOnClickListener {
            findNavController().navigate(
                WorkplaceFragmentDirections.actionWorkplaceFragmentToRegionFragment()
            )
        }

        binding.country.setOnClearText {
            lifecycleScope.launch {
                viewModel.updateCounty(null)
            }
        }

        binding.apply.setOnClickListener {
            lifecycleScope.launch {
                filtersViewModel.updateSettings(
                    newSettings = filtersViewModel.filterSettings.copy(
                        workplace = viewModel.state.value.workplaceFilter,
                    ),
                    updateSettings = false,
                    updateFiltersState = true,
                )
            }
            findNavController().popBackStack()
        }
    }
}

