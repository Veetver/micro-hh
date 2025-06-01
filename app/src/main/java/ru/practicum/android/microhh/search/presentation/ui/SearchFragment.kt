package ru.practicum.android.microhh.search.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.VacancyAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.SearchState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Debounce
import ru.practicum.android.microhh.core.utils.DtoConverter.toVacancyList
import ru.practicum.android.microhh.databinding.FragmentSearchBinding
import ru.practicum.android.microhh.search.data.dto.VacancyDto
import ru.practicum.android.microhh.search.presentation.SearchViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by viewModel<SearchViewModel>()
    private var vacancyAdapter: VacancyAdapter? = null
    private var searchRequest = ""
    private var isClickEnabled = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setListeners()
    }

    private fun setupUI() {
        vacancyAdapter = VacancyAdapter(
            { vacancy ->
                if (isClickEnabled) {
                    isClickEnabled = false
                    Debounce<Any>(Constants.BUTTON_ENABLED_DELAY, lifecycleScope) { isClickEnabled = true }.start()
                }
            },
        )

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

                if (hasFocus) viewModel.search(searchRequest)
            }
        }
    }

    private fun showSearchResults(list: List<VacancyDto>, count: Int) {
        vacancyAdapter?.submitVacancyList(list.toVacancyList(requireContext()))
        binding.counterContainer.isVisible = true
        binding.counter.text = requireContext()
            .resources
            .getQuantityString(R.plurals.vacancy, count, count)
    }

    private fun renderState(state: SearchState) {
        if (state.term != null && searchRequest != state.term) return

        when (state) {
            is SearchState.NoData -> {}
            is SearchState.Loading -> {}
            is SearchState.SearchResults -> showSearchResults(state.results, state.count)
            is SearchState.ConnectionError -> {}
            is SearchState.NothingFound -> {}
        }
    }
}
