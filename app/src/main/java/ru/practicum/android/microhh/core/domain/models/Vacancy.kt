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

enum class Currency(val code: String) {
    RUB("RUB"),
    RUR("RUR"),
    BYR("BYR"),
    USD("USD"),
    EUR("EUR"),
    KZT("KZT"),
    UAH("UAH"),
    AZN("AZN"),
    UZS("UZS"),
    GEL("GEL"),
    KGS("KGS");
}

data class VacancyListItem(
    val vacancyId: String,
    val title: String,
    val companyName: String,
    val salaryDisplayText: String,
    val logoState: LogoState
)

sealed class LogoState {
    object Placeholder
    data class Loaded(val imageUrl: String) : LogoState()
    object Error
}
