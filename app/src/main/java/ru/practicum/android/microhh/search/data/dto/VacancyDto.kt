package ru.practicum.android.microhh.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.domain.models.Department
import ru.practicum.android.microhh.core.domain.models.Employer
import ru.practicum.android.microhh.core.domain.models.Employment
import ru.practicum.android.microhh.core.domain.models.Experience
import ru.practicum.android.microhh.core.domain.models.InsiderInterview
import ru.practicum.android.microhh.core.domain.models.Salary
import ru.practicum.android.microhh.core.domain.models.Type
import ru.practicum.android.microhh.core.domain.models.WorkingDay
import ru.practicum.android.microhh.core.domain.models.WorkingTimeInterval
import ru.practicum.android.microhh.core.domain.models.WorkingTimeMode

data class VacancyDto(
    val id: String,
    val premium: Boolean,
    @SerializedName("has_test") val hasTest: Boolean,
    @SerializedName("response_url") val responseUrl: String?,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("apply_alternate_url") val applyAlternateUrl: String,
    val department: Department,
    val salary: Salary?,
    val name: String,
    @SerializedName("insider_interview") val insiderInterview: InsiderInterview?,
    val area: Area,
    val url: String,
    @SerializedName("published_at") val publishedAt: String,
    val relations: List<Any>,
    val employer: Employer,
    @SerializedName("response_letter_required") val responseLetterRequired: Boolean,
    val type: Type,
    val archived: String,
    @SerializedName("working_days") val workingDays: List<WorkingDay>,
    @SerializedName("working_time_intervals") val workingTimeIntervals: List<WorkingTimeInterval>,
    @SerializedName("working_time_modes") val workingTimeModes: List<WorkingTimeMode>,
    @SerializedName("accept_temporary") val acceptTemporary: Boolean,
    val experience: Experience,
    val employment: Employment,
    @SerializedName("show_logo_in_search") val showLogoInSearch: Boolean
)
