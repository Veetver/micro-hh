package ru.practicum.android.microhh.region.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.CatalogAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.CatalogListItemType
import ru.practicum.android.microhh.core.resources.VisibilityState.Placeholder
import ru.practicum.android.microhh.core.resources.VisibilityState.Results
import ru.practicum.android.microhh.core.resources.VisibilityState.ViewsList
import ru.practicum.android.microhh.core.resources.VisibilityState.VisibilityItem
import ru.practicum.android.microhh.databinding.FragmentRegionBinding
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel
import ru.practicum.android.microhh.region.presentation.RegionViewModel

class RegionFragment : BaseFragment<FragmentRegionBinding>(FragmentRegionBinding::inflate) {
    private val args: RegionFragmentArgs by navArgs()

    private val filterViewModel: FiltersViewModel by activityViewModel()
    private val viewModel: RegionViewModel by viewModel() {
        parametersOf(args.country)
    }

    private var regionAdapter: CatalogAdapter? = null
    private var visibility: ViewsList? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()

        lifecycleScope.launch {
            viewModel.state.collect {
                regionAdapter?.submitCatalogList(
                    list = it.regions,
                    type = CatalogListItemType.CHECK_BOX_ITEM.name,
                )
            }
        }

        binding.search.setOnTextChanged { text ->
            viewModel.filter(text)
//            if (text.isEmpty()) {
//                regionAdapter?.submitCatalogList(
//                    list = viewModel.state.value.regions,
//                    type = CatalogListItemType.ARROW_ITEM.name,
//                )

//            } else {
//                regionAdapter?.submitCatalogList(
//                    list = viewModel.state.value.regions.filter { it.name.contains(text, ignoreCase = true) },
//                    type = CatalogListItemType.ARROW_ITEM.name,
//                )
//            }
        }
    }

    private fun setupUI() {
        visibility = ViewsList(
            listOf(
                VisibilityItem(binding.statePlaceholder, Placeholder),
                VisibilityItem(binding.regionRv, Results),
            )
        )

        regionAdapter = CatalogAdapter { catalog ->

        }
        binding.regionRv.adapter = regionAdapter
    }

    private fun setupListeners() {
        binding.toolbar.setOnClickListener { findNavController().popBackStack() }
    }
}

