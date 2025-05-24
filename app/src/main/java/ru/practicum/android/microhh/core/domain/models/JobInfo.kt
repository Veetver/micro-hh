package ru.practicum.android.microhh.core.domain.models

data class JobInfo(
    val id: Int,
    val name: String,
    val areaName: String,
    val employerName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String,
    var experience: String,
    var employmentFormName: String?,
    var workFormatName: String?,
    var description: String,
    var keySkills: String
) : java.io.Serializable

