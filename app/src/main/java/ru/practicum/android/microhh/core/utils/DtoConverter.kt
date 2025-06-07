package ru.practicum.android.microhh.core.utils

import android.content.Context
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.domain.models.Salary
import ru.practicum.android.microhh.core.resources.Currency
import ru.practicum.android.microhh.industry.data.dto.IndustryDto

object DtoConverter {

    private fun IndustryDto.toCatalog(): Catalog {
        return Catalog(
            id = this.id,
            name = this.name
        )
    }

    fun List<IndustryDto>.toCatalogList(): List<Catalog> {
        val catalogs = mutableListOf<Catalog>()

        this.map { upperItem ->
            catalogs += upperItem.toCatalog()

            upperItem.industries.map { innerItem ->
                catalogs += innerItem.toCatalog()
            }
        }

        return catalogs
    }

    fun Salary?.toSalaryDisplayText(context: Context): String {
        if (this == null) return context.getString(R.string.salary_not_specified)

        val currencySign = Currency.entries
            .firstOrNull { it.name == this.currency }?.sign

        return when {
            this.from != null && this.to != null -> {
                context.getString(
                    R.string.salary_range,
                    this.from.toString(),
                    this.to.toString(),
                    currencySign
                )
            }
            this.from != null && this.to == null -> {
                context.getString(
                    R.string.salary_from,
                    this.from.toString(),
                    currencySign
                )
            }
            this.from == null && this.to != null -> {
                context.getString(
                    R.string.salary_to,
                    this.to.toString(),
                    currencySign
                )
            }
            else -> context.getString(R.string.salary_not_specified)
        }
    }
}
