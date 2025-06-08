package ru.practicum.android.microhh.industry.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.presentation.ui.component.StatePlaceholder.StatePlaceholderMode
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.CatalogAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.CatalogListItemType
import ru.practicum.android.microhh.core.resources.CatalogSearchState
import ru.practicum.android.microhh.core.resources.VisibilityState.Placeholder
import ru.practicum.android.microhh.core.resources.VisibilityState.Results
import ru.practicum.android.microhh.core.resources.VisibilityState.ViewsList
import ru.practicum.android.microhh.core.resources.VisibilityState.VisibilityItem
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.databinding.FragmentIndustryBinding
import ru.practicum.android.microhh.industry.presentation.IndustryViewModel

class IndustryFragment : BaseFragment<FragmentIndustryBinding>(FragmentIndustryBinding::inflate) {

    private val viewModel by viewModel<IndustryViewModel>()
    private var vacancyAdapter: CatalogAdapter? = null
    private var visibility: ViewsList? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        visibility = ViewsList(
            listOf(
                VisibilityItem(binding.statePlaceholder, Placeholder),
                VisibilityItem(binding.recycler, Results),
            )
        )

        vacancyAdapter = CatalogAdapter { catalog ->
            viewModel.onIndustrySelected(catalog)

            if (!binding.choose.isVisible) {
                binding.choose.isVisible = true
            }
        }
        binding.recycler.adapter = vacancyAdapter
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    renderState(state)
                }
            }
        }

        binding.search.setOnTextChanged { text ->
            viewModel.filter(text)
        }
        binding.choose.setOnClickListener {
            val industry = Bundle().apply {
                putParcelable(Constants.KEY_FILTERS, viewModel.catalog)
            }

            parentFragmentManager.setFragmentResult(Constants.KEY_FILTER_INDUSTRY, industry)
            findNavController().popBackStack()
        }
    }

    private fun showPlaceholder(state: StatePlaceholderMode) {
        binding.statePlaceholder.mode = state
        visibility?.show(Placeholder)
    }

    private fun showSearchResults(list: List<Catalog>) {
        vacancyAdapter?.submitCatalogList(list, CatalogListItemType.CHECK_BOX_ITEM.name) {
            visibility?.show(Results)
        }
    }

    private fun renderState(state: CatalogSearchState) {
        when (state) {
            is CatalogSearchState.Loading -> showPlaceholder(StatePlaceholderMode.Loading)
            is CatalogSearchState.NoData -> showPlaceholder(StatePlaceholderMode.ServerError)
            is CatalogSearchState.Results -> showSearchResults(state.catalog)
            else -> {}
        }
    }
}
