package ru.practicum.android.microhh.core.resources

enum class QueryParams(val query: String) {
    TEXT("text"),
    PAGE("page"),
    PER_PAGE("per_page"),
    AREA("area"),
    INDUSTRY("industry"),
    SALARY("salary"),
    ONLY_WITH_SALARY("only_with_salary"),
}
