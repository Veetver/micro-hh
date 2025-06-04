package ru.practicum.android.microhh.vacancy.presentation.mapper

import android.content.Context
import android.text.Html
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.core.utils.DtoConverter.toSalaryDisplayText
import ru.practicum.android.microhh.vacancy.presentation.models.VacancyDetailsUi

fun VacancyDetails.toVacancyDetailsUi(
    context: Context,
): VacancyDetailsUi = VacancyDetailsUi(
    url = url,
    keySkills = if (!keySkills.isNullOrEmpty()) {
        keySkills.joinToString(separator = "\n   •   ", prefix = "   •   ") { it.name }
    } else {
        null
    },
    description = Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT).toString(),
    experience = experience,
    companyName = companyName,
    title = companyName,
    salaryDisplayText = salary?.toSalaryDisplayText(context)
        ?: context.getString(R.string.salary_not_specified),
    companyLogo = companyLogo?.size90,
    region = area.name,
    workFormats = if (!workFormats.isNullOrEmpty()) {
        workFormats.joinToString { it.name.toString() }
    } else {
        null
    },
    salaryFrom = salary?.from,
    salaryTo = salary?.to,
    currency = salary?.currency,
)
