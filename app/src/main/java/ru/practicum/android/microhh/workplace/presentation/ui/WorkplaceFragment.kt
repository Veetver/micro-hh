package ru.practicum.android.microhh.workplace.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentWorkplaceBinding

class WorkplaceFragment : BaseFragment<FragmentWorkplaceBinding>(FragmentWorkplaceBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setupUI()
        setupListeners()
    }

    private fun setupListeners() {
        binding.toolbar.setOnClickListener { findNavController().popBackStack() }
        binding.country.setOnClickListener {
            findNavController().navigate(
                WorkplaceFragmentDirections.actionWorkplaceFragmentToCountryFragment()
            )
        }
        binding.region.setOnClickListener {
            findNavController().navigate(
                WorkplaceFragmentDirections.actionWorkplaceFragmentToRegionFragment()
            )
        }
    }
}

