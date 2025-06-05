package ru.practicum.android.microhh.region.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.databinding.FragmentRegionBinding

class RegionFragment : BaseFragment<FragmentRegionBinding>(FragmentRegionBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setupUI()
        setupListeners()
    }

    private fun setupListeners() {
        binding.toolbar.setOnClickListener { findNavController().popBackStack() }
    }
}

