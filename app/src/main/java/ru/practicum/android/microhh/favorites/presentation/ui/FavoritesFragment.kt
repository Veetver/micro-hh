package ru.practicum.android.microhh.favorites.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentFavoritesBinding
import ru.practicum.android.microhh.favorites.presentation.ui.interfaces.FavoriteJobScreenState

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getLoadingJobState().collect { screenState ->
                renderState(screenState)
            }
        }
    }

    private fun renderState(screenState: FavoriteJobScreenState) {
        when (screenState) {
            is FavoriteJobScreenState.FavoriteContent -> {
                if (screenState.jobs.isEmpty()) {
                    showSearchNotFoundView(true)
                }
            }
            else -> Unit
        }
    }

    private fun showSearchNotFoundView(isVisible: Boolean) {
        binding.noData.isVisible = isVisible
    }
}
