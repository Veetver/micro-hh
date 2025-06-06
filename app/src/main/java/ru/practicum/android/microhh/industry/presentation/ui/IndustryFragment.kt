package ru.practicum.android.microhh.industry.presentation.ui

import android.os.Bundle
import android.view.View
import ru.practicum.android.microhh.core.presentation.ui.component.recycler.VacancyAdapter
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentIndustryBinding

class IndustryFragment : BaseFragment<FragmentIndustryBinding>(FragmentIndustryBinding::inflate) {

    private var vacancyAdapter: VacancyAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        vacancyAdapter = VacancyAdapter()
        binding.recycler.adapter = vacancyAdapter
    }
}
