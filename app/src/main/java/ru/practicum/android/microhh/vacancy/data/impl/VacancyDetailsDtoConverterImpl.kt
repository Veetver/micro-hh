package ru.practicum.android.microhh.vacancy.data.impl

import android.content.Context
import ru.practicum.android.microhh.core.models.items.Vacancy
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsDto
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsDtoConverter

class VacancyDetailsDtoConverterImpl(
    private val context: Context,
) : VacancyDetailsDtoConverter {

    override fun toVacancy(vacancyDetailsDto: VacancyDetailsDto): Vacancy {
        return Vacancy(
            id = vacancyDetailsDto.id,
            salary = vacancyDetailsDto.salary,
            name = vacancyDetailsDto.name,
            area = vacancyDetailsDto.area,
            url = vacancyDetailsDto.url,
            employer = vacancyDetailsDto.employer,
            experience = vacancyDetailsDto.experience,
            employment = vacancyDetailsDto.employment,
            addressCity = vacancyDetailsDto.address?.city,
            workFormat = vacancyDetailsDto.workFormat,
            description = vacancyDetailsDto.description,
            keySkills = vacancyDetailsDto.keySkills
        )

    }
}
