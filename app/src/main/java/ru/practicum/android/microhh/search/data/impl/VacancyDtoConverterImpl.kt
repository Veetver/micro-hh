package ru.practicum.android.microhh.search.data.impl

import android.content.Context
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.utils.DtoConverter.toSalaryDisplayText
import ru.practicum.android.microhh.search.data.dto.VacancyDto
import ru.practicum.android.microhh.search.data.dto.VacancyDtoConverter

class VacancyDtoConverterImpl(
    private val context: Context,
) : VacancyDtoConverter {

    override fun toVacancyList(list: List<VacancyDto>): List<Vacancy> {
        return list.map {
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
}
