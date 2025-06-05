package ru.practicum.android.microhh.core.db.impl

import ru.practicum.android.microhh.core.db.VacancyDetailsEntity
import ru.practicum.android.microhh.core.db.api.VacancyDetailsDBConvertor
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.domain.models.VacancyDetails

class VacancyDetailsDBConvertorImpl : VacancyDetailsDBConvertor {

    override fun toVacancyDetailsEntity(vacancy: VacancyDetails): VacancyDetailsEntity {
        return VacancyDetailsEntity(
            vacancy.id,
            vacancy.name,
            vacancy.areaName,
            vacancy.employerName,
            vacancy.salaryDisplayText,
            vacancy.experience,
            vacancy.employment,
            vacancy.workFormat,
            vacancy.description,
            vacancy.keySkills ?: "",
            System.currentTimeMillis().toString(),
            vacancy.employerLogo,
            vacancy.url,
        )
    }

    override fun toVacancyDetails(vacancy: VacancyDetailsEntity?): VacancyDetails? {
        return if (vacancy == null) {
            null
        } else {
            VacancyDetails(
                vacancy.id,
                vacancy.name,
                vacancy.areaName,
                vacancy.employerName,
                vacancy.salaryDisplayText,
                vacancy.experience,
                vacancy.employment,
                vacancy.workFormat,
                vacancy.description,
                vacancy.keySkills,
                vacancy.employerLogo,
                url = vacancy.url
            )
        }
    }

    override fun toVacancy(vacancy: VacancyDetailsEntity): Vacancy {
        return Vacancy(
            id = vacancy.id.toString(),
            companyLogo = vacancy.employerLogo ?: "",
            title = vacancy.name,
            companyName = vacancy.employerName,
            salaryDisplayText = vacancy.salaryDisplayText,
        )
    }
}
