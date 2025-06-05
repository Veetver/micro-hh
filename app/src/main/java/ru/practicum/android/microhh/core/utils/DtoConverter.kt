package ru.practicum.android.microhh.core.utils

import android.content.Context
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.Currency
import ru.practicum.android.microhh.core.domain.models.JobInfo
import ru.practicum.android.microhh.core.domain.models.Salary
import ru.practicum.android.microhh.core.domain.models.Vacancy

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

    fun List<JobInfo>.toJobVacancyList(context: Context): List<Vacancy> {
        return map {
            val salary = Salary(
                from = it.salaryFrom,
                to = it.salaryTo,
                currency = it.currency,
                gross = false
            )

            Vacancy(
                id = it.id.toString(),
                companyLogo = it.employerLogo ?: "",
                title = it.name,
                companyName = it.employerName,
                salaryDisplayText = salary.toSalaryDisplayText(context),
            )
        }
    }
}
