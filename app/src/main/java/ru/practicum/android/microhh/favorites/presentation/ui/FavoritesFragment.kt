package ru.practicum.android.microhh.favorites.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentFavoritesBinding
import ru.practicum.android.microhh.favorites.presentation.ui.interfaces.FavoriteJobScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLoadingJobLiveData().observe(viewLifecycleOwner) { screenState ->
            if (screenState is FavoriteJobScreenState.FavoriteContent) {
                if (screenState.jobs.isNullOrEmpty()) {
                    showSearchNotFoundView(true)
                }
            }
        }
    }

    private fun showSearchNotFoundView(isVisible: Boolean) {
        binding.noData.isVisible = isVisible
    }
}
