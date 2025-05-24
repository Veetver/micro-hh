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

