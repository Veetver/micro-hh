package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.VacancyListItem
import ru.practicum.android.microhh.core.utils.Extensions.dpToPx
import ru.practicum.android.microhh.databinding.ItemVacancyBinding

class VacancyViewHolder(
    private val binding: ItemVacancyBinding,
    private val onClick: (VacancyListItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: VacancyListItem) {
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
