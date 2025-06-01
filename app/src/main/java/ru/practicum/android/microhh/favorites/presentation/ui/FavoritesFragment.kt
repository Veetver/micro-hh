package ru.practicum.android.microhh.favorites.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.domain.models.VacancyListItem
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.VacancyAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentFavoritesBinding
import ru.practicum.android.microhh.favorites.presentation.ui.interfaces.FavoriteJobScreenState

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModel()
    private var jobs = listOf<VacancyListItem>()
    private var recyclerView: RecyclerView? = null
    private var adapter: VacancyAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLoadingJobLiveData().observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is FavoriteJobScreenState.FavoriteContent -> {
                    if (screenState.jobs == null) {
                        showResultNotFoundView(false)
                        showResultIssueView(true)
                        showJobList(false)
                    } else if (screenState.jobs.isEmpty()) {
                        showResultNotFoundView(true)
                        showResultIssueView(false)
                        showJobList(false)
                    } else {
                        showJobList(true)
                        showResultNotFoundView(false)
                        showResultIssueView(false)
                        jobs = screenState.jobs
                        recyclerView = binding.jobList
                        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
                        adapter = VacancyAdapter(jobs)
                        recyclerView?.adapter = adapter
                    }
                }
                else -> Unit
            }
        }
    }

    private fun showJobList(isVisible: Boolean) {
        binding.jobListContainer.isVisible = isVisible
    }

    private fun showResultIssueView(isVisible: Boolean) {
        binding.unexpectedIssue.isVisible = isVisible
    }

    private fun showResultNotFoundView(isVisible: Boolean) {
        binding.noData.isVisible = isVisible
    }
}
