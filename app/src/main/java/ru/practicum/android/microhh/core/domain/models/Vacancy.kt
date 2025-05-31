package ru.practicum.android.microhh.core.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val companyName: String,
    val companyLogoUrl: String?,
    val salary: Salary?,
    val region: String?,
    val industry: String?
)

sealed class Salary {
    data class From(val amount: Long, val currency: Currency) : Salary()
    data class To(val amount: Long, val currency: Currency) : Salary()
    data class Range(val from: Long, val to: Long, val currency: Currency) : Salary()
    data object NotSpecified : Salary()
}

enum class Currency {
    RUB,
    RUR,
    BYR,
    USD,
    EUR,
    KZT,
    UAH,
    AZN,
    UZS,
    GEL,
    KGS,
}

data class VacancyListItem(
    val id: Long,
    val companyLogo: String?,
    val title: String,
    val companyName: String,
    val salaryDisplayText: String,
)
