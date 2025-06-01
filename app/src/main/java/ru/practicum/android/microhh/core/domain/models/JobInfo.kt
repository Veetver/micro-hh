package ru.practicum.android.microhh.core.domain.models

data class JobInfo(
    val id: Long,
    val name: String,
    val areaName: String,
    val employerName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String,
    val experience: String,
    val employmentFormName: String?,
    val workFormatName: String?,
    val description: String,
    val keySkills: String
) : java.io.Serializable

fun JobInfo.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id.toString(),
        companyLogo = "",
        title = this.name,
        companyName = this.employerName,
        salaryDisplayText = when {
            this.salaryFrom != null && this.salaryTo != null -> "${this.salaryFrom} - ${this.salaryTo} ${this.currency}"
            this.salaryFrom != null -> "от ${this.salaryFrom} ${this.currency}"
            this.salaryTo != null -> "до ${this.salaryTo} ${this.currency}"
            else -> "Не указана"
        }
    )
}
