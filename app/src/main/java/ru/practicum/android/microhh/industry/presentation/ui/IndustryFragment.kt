package ru.practicum.android.microhh.industry.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.microhh.core.domain.models.Industry
import ru.practicum.android.microhh.core.presentation.ui.fragment.BaseFragment
import ru.practicum.android.microhh.core.resources.IndustryState
import ru.practicum.android.microhh.databinding.FragmentIndustryBinding
import ru.practicum.android.microhh.industry.presentation.IndustryViewModel

class IndustryFragment : BaseFragment<FragmentIndustryBinding>(FragmentIndustryBinding::inflate) {
    private val viewModel by viewModel<IndustryViewModel>()

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

    private fun renderState(state: IndustryState) {
        when (state) {
            is IndustryState.Loading -> showLoading()
            is IndustryState.Error -> showError()
            is IndustryState.Success -> showIndustries(state.industries)
        }
    }

    private fun showIndustries(industries: List<Industry>?) {
        binding.progressBar.isVisible = false
        binding.serverErrorImage.isVisible = false
        println(industries)
    }

    private fun showError() {
        binding.serverErrorImage.isVisible = true
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

}
