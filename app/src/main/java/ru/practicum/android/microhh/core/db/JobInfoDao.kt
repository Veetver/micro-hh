package ru.practicum.android.microhh.core.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface JobInfoDao {

    @Upsert
    fun insertJob(jobInfoEntity: JobInfoEntity)

    @Query("DELETE FROM job_info_table WHERE id = :id")
    fun deleteJob(id: Long)

    @Query(
        "SELECT id, name, area_name, employer_name, " +
            "salary_range_from, salary_range_to, currency, " +
            "experience, employment_form_name, work_format_name, " +
            "description, key_skills, job_added_toDB_at " +
            "FROM job_info_table " +
            "ORDER BY job_added_toDB_at DESC"
    )
    suspend fun getJobs(): List<JobInfoEntity>

    @Query(
        "SELECT id, name, area_name, employer_name, " +
            "salary_range_from, salary_range_to, currency, " +
            "experience, employment_form_name, work_format_name, " +
            "description, key_skills, job_added_toDB_at " +
            "FROM job_info_table " +
            "WHERE id = :id"
    )
    suspend fun getJobById(id: Long): JobInfoEntity?
}

