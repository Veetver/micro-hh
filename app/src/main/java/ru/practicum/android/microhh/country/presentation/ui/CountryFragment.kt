package ru.practicum.android.microhh.country.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentCountryBinding
import ru.practicum.android.microhh.workplace.presentation.ui.WorkplaceFragmentDirections

class CountryFragment : BaseFragment<FragmentCountryBinding>(FragmentCountryBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setupUI()
        setupListeners()
    }

    private fun setupListeners() {
        binding.toolbar.setOnClickListener { findNavController().popBackStack() }
    }
}
