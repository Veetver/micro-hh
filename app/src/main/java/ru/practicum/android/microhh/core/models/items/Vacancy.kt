package ru.practicum.android.microhh.core.models.items

import com.google.gson.annotations.SerializedName
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.domain.models.Department
import ru.practicum.android.microhh.core.domain.models.Employer
import ru.practicum.android.microhh.core.domain.models.Employment
import ru.practicum.android.microhh.core.domain.models.Experience
import ru.practicum.android.microhh.core.domain.models.InsiderInterview
import ru.practicum.android.microhh.core.domain.models.KeySkills
import ru.practicum.android.microhh.core.domain.models.Salary
import ru.practicum.android.microhh.core.domain.models.Type
import ru.practicum.android.microhh.core.domain.models.WorkFormat
import ru.practicum.android.microhh.core.domain.models.WorkingDay
import ru.practicum.android.microhh.core.domain.models.WorkingTimeInterval
import ru.practicum.android.microhh.core.domain.models.WorkingTimeMode

data class Vacancy(
    @SerializedName("id") val id: String,
    @SerializedName("premium") val premium: Boolean,
    @SerializedName("has_test") val hasTest: Boolean,
    @SerializedName("response_url") val responseUrl: String?,
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
    @SerializedName("show_logo_in_search") val showLogoInSearch: Boolean,
    @SerializedName("address_city") val addressCity: String?,
    @SerializedName("work_format") val workFormat: WorkFormat,
    @SerializedName("description") val description: String?,
    @SerializedName("key_skills") val keySkills: List<KeySkills>?
)
