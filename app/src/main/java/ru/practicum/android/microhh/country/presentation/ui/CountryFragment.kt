package ru.practicum.android.microhh.country.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.presentation.ui.component.StatePlaceholder.StatePlaceholderMode
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.CountryAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.VisibilityState.Placeholder
import ru.practicum.android.microhh.core.resources.VisibilityState.Results
import ru.practicum.android.microhh.core.resources.VisibilityState.ViewsList
import ru.practicum.android.microhh.core.resources.VisibilityState.VisibilityItem
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Debounce
import ru.practicum.android.microhh.country.presentation.CountryViewModel
import ru.practicum.android.microhh.country.presentation.state.CountryState
import ru.practicum.android.microhh.databinding.FragmentCountryBinding
import ru.practicum.android.microhh.workplace.presentation.WorkplaceViewModel

class CountryFragment : BaseFragment<FragmentCountryBinding>(FragmentCountryBinding::inflate) {

    private val viewModel by viewModel<CountryViewModel>()
    private val workplaceViewModel: WorkplaceViewModel by activityViewModel()
    private var countryAdapter: CountryAdapter? = null
    private var visibility: ViewsList? = null
    private var isClickEnabled = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        visibility = ViewsList(
            listOf(
                VisibilityItem(binding.statePlaceholder, Placeholder),
                VisibilityItem(binding.countryRv, Results)
            )
        )

        countryAdapter = CountryAdapter { country ->
            if (isClickEnabled) {
                isClickEnabled = false
                Debounce<Any>(Constants.BUTTON_ENABLED_DELAY, lifecycleScope) { isClickEnabled = true }.start()
            }
            workplaceViewModel.updateCountry(country)
            findNavController().popBackStack()
        }
        binding.countryRv.adapter = countryAdapter
    }

    private fun setupListeners() {
        binding.toolbar.setOnClickListener { findNavController().popBackStack() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: CountryState) {
        when (state) {
            is CountryState.NoCountries -> showPlaceholder(StatePlaceholderMode.NoList)
            is CountryState.Loading -> showPlaceholder(StatePlaceholderMode.Loading)
            is CountryState.ConnectionError -> showPlaceholder(StatePlaceholderMode.ConnectionError)
            is CountryState.ShowCountries -> showCountries(state.result)
        }
    }

    private fun showPlaceholder(state: StatePlaceholderMode) {
        binding.statePlaceholder.mode = state
        visibility?.show(Placeholder)
    }

    private fun showCountries(countries: List<Catalog>) {
        countryAdapter?.countriesList = countries.toMutableList()
        countryAdapter?.notifyDataSetChanged()
        visibility?.show(Results)
    }
}
