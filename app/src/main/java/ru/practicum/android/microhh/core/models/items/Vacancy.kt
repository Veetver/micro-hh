package ru.practicum.android.microhh.core.models.items

import com.google.gson.annotations.SerializedName
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.domain.models.Employer
import ru.practicum.android.microhh.core.domain.models.Employment
import ru.practicum.android.microhh.core.domain.models.Experience
import ru.practicum.android.microhh.core.domain.models.KeySkills
import ru.practicum.android.microhh.core.domain.models.Salary
import ru.practicum.android.microhh.core.domain.models.WorkFormat

data class Vacancy(
    @SerializedName("id") val id: String,
    @SerializedName("salary") val salary: Salary?,
    @SerializedName("name") val name: String,
    @SerializedName("area") val area: Area,
    @SerializedName("url") val url: String,
    @SerializedName("employer") val employer: Employer,
    @SerializedName("experience") val experience: Experience,
    @SerializedName("employment") val employment: Employment,
    @SerializedName("address_city") val addressCity: String?,
    @SerializedName("work_format") val workFormat: WorkFormat,
    @SerializedName("description") val description: String?,
    @SerializedName("key_skills") val keySkills: List<KeySkills>?
)
