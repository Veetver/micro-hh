package ru.practicum.android.microhh.favorites.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.ItemAnimator
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.VacancyAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.FavoriteJobScreenState
import ru.practicum.android.microhh.core.resources.VisibilityState.Error
import ru.practicum.android.microhh.core.resources.VisibilityState.NoData
import ru.practicum.android.microhh.core.resources.VisibilityState.Results
import ru.practicum.android.microhh.core.resources.VisibilityState.ViewsList
import ru.practicum.android.microhh.core.resources.VisibilityState.VisibilityItem
import ru.practicum.android.microhh.core.utils.DtoConverter.toJobVacancyList
import ru.practicum.android.microhh.databinding.FragmentFavoritesBinding
import ru.practicum.android.microhh.favorites.presentation.FavoritesViewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModel()
    private var vacancyAdapter: VacancyAdapter? = null
    private var visibility: ViewsList? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.showFavorites()
    }

    private fun setListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getLoadingJobState().collect { screenState ->
                renderState(screenState)
            }
        }
    }

    private fun setupUI() {
        visibility = ViewsList(
            listOf(
                VisibilityItem(binding.noData, NoData),
                VisibilityItem(binding.unexpectedIssue, Error),
                VisibilityItem(binding.jobListContainer, Results),
            )
        )

        vacancyAdapter = VacancyAdapter()
        binding.jobList.adapter = vacancyAdapter
        binding.jobList.itemAnimator = ItemAnimator()
    }

    private fun renderState(screenState: FavoriteJobScreenState) {
        when (screenState) {
            is FavoriteJobScreenState.Empty -> visibility?.show(NoData)
            is FavoriteJobScreenState.Error -> visibility?.show(Error)
            is FavoriteJobScreenState.FavoriteContent -> {
                vacancyAdapter?.submitVacancyList(screenState.jobs.toJobVacancyList(), false) {
                    visibility?.show(Results)
                }
            }
            else -> {}
        }
    }
}
