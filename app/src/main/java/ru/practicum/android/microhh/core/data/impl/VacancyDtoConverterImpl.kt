package ru.practicum.android.microhh.core.data.impl

import android.content.Context
import androidx.core.content.ContextCompat
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.data.dto.VacancyDtoConverter
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.core.utils.DtoConverter.toSalaryDisplayText
import ru.practicum.android.microhh.search.data.dto.VacancyDto
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsDto
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsResponse
import java.util.Locale

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

    override fun fromVacancyDetailsToVacancyList(list: List<VacancyDetails>): List<Vacancy> {
        return list.map {
            Vacancy(
                id = it.id.toString(),
                companyLogo = it.employerLogo ?: "",
                title = it.name,
                companyName = it.employerName,
                salaryDisplayText = it.salaryDisplayText,
            )
        }
    }

    override fun toVacancyDetails(vacancy: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            vacancy.id.toLong(),
            vacancy.name,
            vacancy.area.name,
            vacancy.employer.name,
            vacancy.salary.toSalaryDisplayText(context),
            vacancy.experience?.name ?: "",
            vacancy.employment?.name ?: "",
            if (!vacancy.workFormat.isNullOrEmpty()) {
                vacancy.workFormat.joinToString {
                    it.name.toString()
                }
            } else {
                null
            },
            vacancy.description?.let { editHtml(it) } ?: "",
            if (!vacancy.keySkills.isNullOrEmpty()) {
                vacancy.keySkills.joinToString(separator = "\n   •   ", prefix = "   •   ") { it.name }
            } else {
                null
            },
            vacancy.employer.logoUrls?.size90,
            url = vacancy.url ?: ""
        )
    }

    override fun toVacancyDetails(vacancy: VacancyDetailsResponse): VacancyDetails {
        return VacancyDetails(
            vacancy.id.toLong(),
            vacancy.name,
            vacancy.area.name,
            vacancy.employer.name,
            vacancy.salary.toSalaryDisplayText(context),
            vacancy.experience?.name ?: "",
            vacancy.employment?.name ?: "",
            if (!vacancy.workFormat.isNullOrEmpty()) {
                vacancy.workFormat.joinToString {
                    it.name.toString()
                }
            } else {
                null
            },
            vacancy.description?.let { editHtml(it) } ?: "",
            if (!vacancy.keySkills.isNullOrEmpty()) {
                vacancy.keySkills.joinToString(separator = "\n   •   ", prefix = "   •   ") { it.name }
            } else {
                null
            },
            vacancy.employer.logoUrls?.size90,
            url = vacancy.alternateUrl
        )
    }

    private fun editHtml(description: String): String {
        return "<html>\n" +
            "        <head>\n" +
            "            <style type='text/css'>\n" +
            "                body {color: ${
                String.format(
                    Locale.getDefault(),
                    "#%06X",
                    WHITE_COLOR_CODE and ContextCompat.getColor(context, R.color.black)
                )
            }}; }\n" +
            "            </style>\n" +
            "        </head>\n" +
            "        <body>\n" +
            "            ${description}\n" +
            "        </body>\n" +
            "    </html>"
    }

    companion object {
        private const val WHITE_COLOR_CODE = 0xFDFDFD
    }
}
