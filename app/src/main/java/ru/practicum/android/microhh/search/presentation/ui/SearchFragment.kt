package ru.practicum.android.microhh.search.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.presentation.ui.component.StatePlaceholder.StatePlaceholderMode
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.VacancyAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.SearchState
import ru.practicum.android.microhh.core.resources.VisibilityState.Placeholder
import ru.practicum.android.microhh.core.resources.VisibilityState.Results
import ru.practicum.android.microhh.core.resources.VisibilityState.ViewsList
import ru.practicum.android.microhh.core.resources.VisibilityState.VisibilityItem
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Debounce
import ru.practicum.android.microhh.databinding.FragmentSearchBinding
import ru.practicum.android.microhh.search.presentation.SearchViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by viewModel<SearchViewModel>()
    private var vacancyAdapter: VacancyAdapter? = null
    private var visibility: ViewsList? = null
    private var searchRequest = ""
    private var isClickEnabled = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setListeners()
    }

    private fun setupUI() {
        visibility = ViewsList(
            listOf(
                VisibilityItem(binding.statePlaceholder, Placeholder),
                VisibilityItem(binding.recycler, Results),
                VisibilityItem(binding.counterContainer, Results),
            )
        )

        vacancyAdapter = VacancyAdapter { vacancy ->
            if (isClickEnabled) {
                isClickEnabled = false
                Debounce<Any>(Constants.BUTTON_ENABLED_DELAY, lifecycleScope) { isClickEnabled = true }.start()
            }
            findNavController().navigate(
                SearchFragmentDirections.openVacancyDetails(vacancy.id)
            )
        }

        binding.recycler.adapter = vacancyAdapter
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    renderState(state)
                }
            }
        }

        binding.search.also { editText ->
            editText.setOnTextChanged { text ->
                val hasFocus = editText.hasFocus()

                searchRequest = text

                if (hasFocus) {
                    viewModel.search(text)
                }

                if (text.isEmpty()) {
                    showPlaceholder(StatePlaceholderMode.Default)
                }
            }
        }

        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.recycler.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter?.itemCount ?: 0
                    if (pos >= itemsCount - 1) {
                        viewModel.search(searchRequest, isNextPage = true)
                    }
                }
            }
        })

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter_icon -> {
                    findNavController().navigate(
                        R.id.open_filters
                    )

                    true
                }
                else -> false
            }
        }

        parentFragmentManager.setFragmentResultListener(Constants.KEY_FILTERS, viewLifecycleOwner) { _, _ ->
            viewModel.search(searchRequest)
        }
    }

    private fun showSearchResults(list: List<Vacancy>, count: Int, isNextPage: Boolean = false) {
        val vacancies = viewModel.updateList(list)

        binding.counter.text = requireContext()
            .resources
            .getQuantityString(R.plurals.vacancy, count, count)
        vacancyAdapter?.submitVacancyList(vacancies, isNextPage) {
            visibility?.show(Results)
        }
    }

    private fun showPlaceholder(state: StatePlaceholderMode) {
        binding.statePlaceholder.mode = state
        visibility?.show(Placeholder)
    }

    private fun showErrorLoadingNextPage() {
        vacancyAdapter?.hideLoading()
        requireContext().also {
            Toast.makeText(it, it.getString(R.string.connection_error), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun renderState(state: SearchState) {
        if (state.term != null && searchRequest != state.term && state !is SearchState.NextPage) return

        when (state) {
            is SearchState.NoData -> showPlaceholder(StatePlaceholderMode.Default)
            is SearchState.Loading -> showPlaceholder(StatePlaceholderMode.Loading)
            is SearchState.SearchResults -> showSearchResults(state.results, state.vacanciesCount, state.canLoadMore)
            is SearchState.NextPage -> showSearchResults(state.results, state.vacanciesCount, state.canLoadMore)
            is SearchState.ConnectionError -> {
                if (state.isNextPage) {
                    showErrorLoadingNextPage()
                } else {
                    showPlaceholder(StatePlaceholderMode.ConnectionError)
                }
            }
            is SearchState.NothingFound -> showPlaceholder(StatePlaceholderMode.NothingFound)
        }
    }
}
