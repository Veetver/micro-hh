package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.utils.Extensions.dpToPx
import ru.practicum.android.microhh.databinding.ItemLoadingBinding
import ru.practicum.android.microhh.databinding.ItemVacancyBinding
import ru.practicum.android.microhh.search.domain.model.VacancyListItem

fun trackItemDelegate(
    onClick: (Vacancy) -> Unit,
) = adapterDelegateViewBinding<VacancyListItem.VacancyItem, VacancyListItem, ItemVacancyBinding>(
    { layoutInflater, root -> ItemVacancyBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val vacancy = item.vacancy

        Glide.with(itemView)
            .load(vacancy.companyLogo)
            .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.company_logo_placeholder))
            .centerCrop()
            .transform(
                RoundedCorners(
                    itemView.context.resources.getDimension(R.dimen.corner_radius_12)
                        .dpToPx(itemView.context)
                )
            )
            .into(binding.companyLogo)

        binding.title.text = vacancy.title
        binding.companyName.text = vacancy.companyName
        binding.salary.text = vacancy.salaryDisplayText
        itemView.setOnClickListener { onClick(vacancy) }
    }
}

fun loadingItemDelegate() =
    adapterDelegateViewBinding<VacancyListItem.Loading, VacancyListItem, ItemLoadingBinding>(
        { layoutInflater, root -> ItemLoadingBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {}
    }
