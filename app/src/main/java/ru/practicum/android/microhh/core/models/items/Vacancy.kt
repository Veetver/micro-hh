package ru.practicum.android.microhh.core.models.items

import com.google.gson.annotations.SerializedName

data class Vacancy(
    @SerializedName("id") val id: String,
    @SerializedName("premium") val premium: Boolean,
    @SerializedName("has_test") val hasTest: Boolean,
    @SerializedName("response_url") val responseUrl: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("apply_alternate_url") val applyAlternateUrl: String,
    @SerializedName("department") val department: Department,
    @SerializedName("salary") val salary: Salary?,
    @SerializedName("name") val name: String,
    @SerializedName("insider_interview") val insiderInterview: InsiderInterview?,
    @SerializedName("area") val area: Area,
    @SerializedName("url") val url: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("relations") val relations: List<Any>,
    @SerializedName("employer") val employer: Employer,
    @SerializedName("response_letter_required") val responseLetterRequired: Boolean,
    @SerializedName("type") val type: Type,
    @SerializedName("archived") val archived: String,
    @SerializedName("working_days") val workingDays: List<WorkingDay>,
    @SerializedName("working_time_intervals") val workingTimeIntervals: List<WorkingTimeInterval>,
    @SerializedName("working_time_modes") val workingTimeModes: List<WorkingTimeMode>,
    @SerializedName("accept_temporary") val acceptTemporary: Boolean,
    @SerializedName("experience") val experience: Experience,
    @SerializedName("employment") val employment: Employment,
    @SerializedName("show_logo_in_search") val showLogoInSearch: Boolean
)
