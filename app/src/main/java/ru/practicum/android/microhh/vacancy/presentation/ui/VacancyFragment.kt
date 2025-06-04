package ru.practicum.android.microhh.vacancy.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.utils.Extensions.dpToPx
import ru.practicum.android.microhh.databinding.FragmentVacancyBinding
import ru.practicum.android.microhh.vacancy.presentation.mapper.toVacancyDetailsUi
import ru.practicum.android.microhh.vacancy.presentation.models.VacancyDetailsUi

class VacancyFragment : BaseFragment<FragmentVacancyBinding>(FragmentVacancyBinding::inflate) {
    private val args: VacancyFragmentArgs by navArgs()
    private val viewModel by viewModel<VacancyViewModel>() {
        parametersOf(args.vacancyId)
    }

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
    }

    private fun renderState(state: VacancyState) {
        when (state) {
            is VacancyState.VacancyNotExist -> showError()
            is VacancyState.Loading -> showLoading()
            is VacancyState.ConnectionError -> showError()
            is VacancyState.ShowDetails -> showVacancy(
                state.result.toVacancyDetailsUi(
                    requireContext()
                )
            )
        }
    }

    private fun showVacancy(vacancy: VacancyDetailsUi) {
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
                    // to do
                    true
                }

                else -> false
            }
        }
    }

    private fun fillContent(vacancy: VacancyDetailsUi) {
        Glide
            .with(binding.vacancyCover)
            .load(vacancy.companyLogo)
            .placeholder(R.drawable.placeholder_with_frame)
            .centerCrop()
            .transform(RoundedCorners(2.0f.dpToPx(binding.vacancyCover.context)))
            .into(binding.vacancyCover)
        binding.vacancyName.text = vacancy.title
        binding.vacancySalary.text = vacancy.salaryDisplayText
        binding.employerName.text = vacancy.companyName
        binding.employerAddress.text = vacancy.region
        binding.requiredExp.text = vacancy.experience
        binding.workFormat.text = vacancy.workFormats

        vacancy.description?.let {
            binding.vacancyDescriptionTitle.isVisible = true
            binding.vacancyDescription.isVisible = true
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
