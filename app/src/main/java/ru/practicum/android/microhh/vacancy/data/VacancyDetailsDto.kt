package ru.practicum.android.microhh.vacancy.data

import com.google.gson.annotations.SerializedName
import ru.practicum.android.microhh.core.domain.models.Salary
import ru.practicum.android.microhh.core.models.items.Area
import ru.practicum.android.microhh.core.models.items.Department
import ru.practicum.android.microhh.core.models.items.Employer
import ru.practicum.android.microhh.core.models.items.Employment
import ru.practicum.android.microhh.core.models.items.Experience
import ru.practicum.android.microhh.core.models.items.InsiderInterview
import ru.practicum.android.microhh.core.models.items.KeySkills
import ru.practicum.android.microhh.core.models.items.Type
import ru.practicum.android.microhh.core.models.items.WorkFormat
import ru.practicum.android.microhh.core.models.items.WorkingDay
import ru.practicum.android.microhh.core.models.items.WorkingTimeInterval
import ru.practicum.android.microhh.core.models.items.WorkingTimeMode

data class VacancyDetailsDto(
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
    @SerializedName("show_logo_in_search") val showLogoInSearch: Boolean,
    @SerializedName("address_city") val addressCity: String?,
    @SerializedName("work_format") val workFormat: WorkFormat,
    @SerializedName("description") val description: String?,
    @SerializedName("key_skills") val keySkills: List<KeySkills>?
)
