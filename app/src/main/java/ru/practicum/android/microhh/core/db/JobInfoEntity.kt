package ru.practicum.android.microhh.core.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job_info_table")
data class JobInfoEntity(
    // для экрана отображения списка избранных вакансий
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "area_name")
    val areaName: String,
    @ColumnInfo(name = "employer_name")
    val employerName: String,
    @ColumnInfo(name = "salary_range_from")
    val salaryFrom: Int?,
    @ColumnInfo(name = "salary_range_to")
    val salaryTo: Int?,
    @ColumnInfo(name = "currency")
    val currency: String,
    // для экрана отображения деталей избранной вакансии без интернета
    @ColumnInfo(name = "experience")
    val experience: String,
    @ColumnInfo(name = "employment_form_name")
    val employmentFormName: String?,
    @ColumnInfo(name = "work_format_name")
    val workFormatName: String?,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "key_skills")
    val keySkills: String,
    @ColumnInfo(name = "job_added_toDB_at")
    val addedAt: String,
)

