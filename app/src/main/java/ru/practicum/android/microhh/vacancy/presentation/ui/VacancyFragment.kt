package ru.practicum.android.microhh.vacancy.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.TypedValue
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentVacancyBinding
import ru.practicum.android.microhh.vacancy.data.VacancyDetailsDto

class VacancyFragment : BaseFragment<FragmentVacancyBinding>(FragmentVacancyBinding::inflate) {
    private val viewModel by viewModel<VacancyViewModel>()
    private val currentVacancy: VacancyDetailsDto? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        requireArguments().getString(VACANCY_ID_KEY)?.let { viewModel.getVacancyById(it) }
    }

    private fun setupListeners() {
        binding.toolbar.setOnClickListener { findNavController().popBackStack() }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sharing_icon -> {
                    if (currentVacancy != null) {
                        shareVacancy(currentVacancy)
                    }
                    true
                }

                R.id.favorites_icon -> {
                    // to do
                    true
                }

                else -> false
            }
        }

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
            is VacancyState.ShowDetails -> showVacancy(state.result)
        }
    }

    private fun showVacancy(vacancy: VacancyDetailsDto) {
        binding.progressBar.isVisible = false
        binding.serverErrorImage.isVisible = false
        Glide
            .with(binding.vacancyCover)
            .load(vacancy.employer.logoUrls?.size90)
            .placeholder(R.drawable.placeholder_with_frame)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(2.0f, binding.vacancyCover.context)))
            .into(binding.vacancyCover)
        binding.vacancyName.text = vacancy.name
        binding.vacancySalary.text = showSalary(vacancy)
        binding.employerName.text = vacancy.employer.name
        binding.employerAddress.text = showAddress(vacancy)
        binding.requiredExp.text = vacancy.experience.name
        binding.workFormat.text = showWorkFormat(vacancy)

        if (vacancy.description.isNullOrEmpty()) {
            binding.vacancyDescriptionTitle.isVisible = false
        } else {
            binding.vacancyDescriptionTitle.isVisible = true
            binding.vacancyDescription.setText(Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT))
        }

        if (vacancy.keySkills.isNullOrEmpty()) {
            binding.keySkillsTitle.isVisible = false
        } else {
            binding.keySkillsTitle.isVisible = true
            binding.keySkills.text = vacancy.keySkills.joinToString(separator = "\n")
        }
    }

    private fun showError() {
        binding.serverErrorImage.isVisible = true
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun showSalary(vacancy: VacancyDetailsDto): String {
        return if (vacancy.salary == null) {
            R.string.salary_not_specified.toString()
        } else if (vacancy.salary.to == null) {
            String.format(R.string.salary_from.toString(), vacancy.salary.from, vacancy.salary.currency)
        } else if (vacancy.salary.from == null) {
            String.format(R.string.salary_to.toString(), vacancy.salary.to, vacancy.salary.currency)
        } else {
            String.format(
                R.string.salary_range.toString(),
                vacancy.salary.from,
                vacancy.salary.to,
                vacancy.salary.currency
            )
        }
    }

    private fun showAddress(vacancy: VacancyDetailsDto): String {
        return if (vacancy.addressCity.isNullOrEmpty()) {
            vacancy.area.name
        } else {
            vacancy.addressCity
        }
    }

    private fun showWorkFormat(vacancy: VacancyDetailsDto): String {
        return if (vacancy.employment.name.isNullOrEmpty() && vacancy.workFormat.name.isNullOrEmpty()) {
            ""
        } else if (vacancy.employment.name.isNullOrEmpty()) {
            vacancy.workFormat.name.toString()
        } else if (vacancy.workFormat.name.isNullOrEmpty()) {
            vacancy.employment.name
        } else {
            "${vacancy.workFormat.name}, ${vacancy.employment.name}"
        }
    }

    private fun shareVacancy(vacancy: VacancyDetailsDto) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, vacancy.url)
            type = "text/plain"
        }.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val shareIntent =
            Intent.createChooser(sendIntent, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(shareIntent)
    }

    companion object {
        const val VACANCY_ID_KEY = "VACANCY_ID_KEY"
    }
}
