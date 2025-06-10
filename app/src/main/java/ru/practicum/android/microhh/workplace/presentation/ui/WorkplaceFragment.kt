package ru.practicum.android.microhh.workplace.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentWorkplaceBinding
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel
import ru.practicum.android.microhh.workplace.domain.models.WorkplaceFilter
import ru.practicum.android.microhh.workplace.presentation.WorkplaceViewModel
import ru.practicum.android.microhh.workplace.presentation.state.WorkplaceState

class WorkplaceFragment : BaseFragment<FragmentWorkplaceBinding>(FragmentWorkplaceBinding::inflate) {
    private val viewModel: WorkplaceViewModel by activityViewModel()
    private val filterViewModel: FiltersViewModel by activityViewModel()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .state
                    .combine(filterViewModel.filtersStateFlow) { workplaceState, filterState ->
                        if (filterState.workplace != workplaceState.workplaceFilter && !workplaceState.showApply) {
                            WorkplaceState(
                                workplaceFilter = WorkplaceFilter(),
                                showApply = false
                            )
                        } else {
                            workplaceState
                        }
                    }
                    .collect {
                        binding.country.setText(it.workplaceFilter.country?.name)
                        binding.region.setText(it.workplaceFilter.region?.name)

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
                WorkplaceFragmentDirections.actionWorkplaceFragmentToRegionFragment(
                    country = viewModel.state.value.workplaceFilter.country
                )
            )
        }

        binding.country.setOnCleared {
            viewModel.onClearedCountry()
        }

        binding.region.setOnCleared {
            viewModel.onClearedRegion()
        }

        binding.apply.setOnClickListener {
            viewModel.applied()
            filterViewModel.setWorkplaceFilter(viewModel.state.value.workplaceFilter)
            findNavController().popBackStack()
        }
    }
}

