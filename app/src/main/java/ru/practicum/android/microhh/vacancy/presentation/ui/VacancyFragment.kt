package ru.practicum.android.microhh.vacancy.presentation.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.utils.Extensions.dpToPx
import ru.practicum.android.microhh.databinding.FragmentVacancyBinding
import ru.practicum.android.microhh.vacancy.presentation.VacancyViewModel

class VacancyFragment : BaseFragment<FragmentVacancyBinding>(FragmentVacancyBinding::inflate) {
    private val args: VacancyFragmentArgs by navArgs()
    private val viewModel by viewModel<VacancyViewModel> {
        parametersOf(args.vacancyId)
    }
    private var isFavorite: Boolean? = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFavoriteFlow.collect { state ->
                    renderFavoriteState(state)
                }
            }
        }
    }

    private fun renderFavoriteState(state: VacancyFavoriteState) {
        when (state) {
            is VacancyFavoriteState.Error -> showError()
            is VacancyFavoriteState.VacancyFavorite -> showFavoriteVacancy(state.isFavorite)
            is VacancyFavoriteState.Loading -> {}
            is VacancyFavoriteState.Success -> {}
            is VacancyFavoriteState.VacancyNotFavorite -> showFavoriteVacancy(state.isFavorite)
        }
    }

    private fun showFavoriteVacancy(isFavorite: Boolean?) {
        this.isFavorite = isFavorite
        val favoritesItem = binding.toolbar.menu.findItem(R.id.favorites_icon)
        if (this.isFavorite == true) {
            favoritesItem.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorites_on)
        } else {
            favoritesItem.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorites_off)
        }
    }

    private fun renderState(state: VacancyState) {
        when (state) {
            is VacancyState.VacancyNotExist -> showError()
            is VacancyState.Loading -> showLoading()
            is VacancyState.ConnectionError -> showError()
            is VacancyState.ShowDetails -> showVacancy(state.result)
        }
    }

    private fun showVacancy(vacancy: VacancyDetails) {
        binding.progressBar.isVisible = false
        binding.serverErrorImage.isVisible = false
        showTitles(true)

        fillContent(vacancy)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sharing_icon -> {
                    shareVacancy(vacancy.url)
                    true
                }

                R.id.favorites_icon -> {
                    setFavorite(vacancy)
                    true
                }

                else -> false
            }
        }
    }

    private fun setFavorite(vacancy: VacancyDetails) {
        viewModel.updateFavorite(vacancy, this.isFavorite)
    }

    private fun fillContent(vacancy: VacancyDetails) {
        Glide
            .with(binding.vacancyCover)
            .load(vacancy.employerLogo)
            .placeholder(R.drawable.placeholder_with_frame)
            .centerCrop()
            .transform(RoundedCorners(2.0f.dpToPx(binding.vacancyCover.context)))
            .into(binding.vacancyCover)
        binding.vacancyName.text = vacancy.name
        binding.vacancySalary.text = vacancy.salaryDisplayText
        binding.employerName.text = vacancy.employerName
        binding.employerAddress.text = vacancy.areaName
        binding.requiredExp.text = vacancy.experience
        binding.workFormat.text = vacancy.workFormat

        vacancy.description.let {
            binding.vacancyDescriptionTitle.isVisible = true
            binding.vacancyDescription.isVisible = true
            binding.vacancyDescription.setBackgroundColor(Color.TRANSPARENT)
            binding.vacancyDescription.loadDataWithBaseURL(
                null,
                vacancy.description,
                "text/html",
                "utf-8",
                null
            )
        }

        vacancy.keySkills?.let {
            binding.keySkillsTitle.isVisible = true
            binding.keySkills.isVisible = true
            binding.keySkills.text = vacancy.keySkills
        }
    }

    private fun showError() {
        showTitles(false)
        binding.serverErrorImage.isVisible = true
    }

    private fun showLoading() {
        showTitles(false)
        binding.progressBar.isVisible = true
    }

    private fun shareVacancy(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val shareIntent =
            Intent.createChooser(sendIntent, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(shareIntent)
    }

    private fun showTitles(show: Boolean) {
        if (show) {
            binding.vacancyDescriptionTitle.isVisible = true
            binding.requiredExpTitle.isVisible = true
            binding.cardView.isVisible = true
        } else {
            binding.vacancyDescriptionTitle.isVisible = false
            binding.requiredExpTitle.isVisible = false
            binding.cardView.isVisible = false
        }
    }
}
