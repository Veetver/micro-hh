package ru.practicum.android.microhh.core.utils

import android.content.Context
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.Currency
import ru.practicum.android.microhh.core.domain.models.JobInfo
import ru.practicum.android.microhh.core.domain.models.Salary

object DtoConverter {

    fun Salary.toSalaryDisplayText(context: Context): String {
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

    fun List<VacancyDto>.toVacancyList(context: Context): List<Vacancy> {
        return map {
            Vacancy(
                id = it.id,
                companyLogo = it.employer.logoUrls?.size90 ?: "",
                title = it.name,
                companyName = it.employer.name,
                salaryDisplayText = it.salary?.toSalaryDisplayText(context)
                    ?: context.getString(R.string.salary_not_specified),
            )
        }
    }

    fun List<JobInfo>.toJobVacancyList(context: Context): List<Vacancy> {
        return map {
            Vacancy(
                id = it.id.toString(),
                companyLogo = "",
                title = it.name,
                companyName = it.employerName,
                salaryDisplayText = "",
            )
        }
    }
}
