package ru.practicum.android.microhh.core.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface VacancyDetailsDao {

    @Upsert
    fun insertVacancy(vacancy: VacancyDetailsEntity)

    @Query("DELETE FROM job_info_table WHERE id = :id")
    fun deleteVacancy(id: Long)

    @Query(
        "SELECT id, name, area_name, employer_name, " +
            "salary, " +
            "experience, employment, work_format_name, " +
            "description, key_skills, job_added_toDB_at, url " +
            "FROM job_info_table " +
            "ORDER BY job_added_toDB_at DESC"
    )
    suspend fun getVacancies(): List<VacancyDetailsEntity>

    @Query(
        "SELECT id, name, area_name, employer_name, " +
            "salary, " +
            "experience, employment, work_format_name, " +
            "description, key_skills, job_added_toDB_at, url " +
            "FROM job_info_table " +
            "WHERE id = :id"
    )
    suspend fun getVacancyById(id: Long): VacancyDetailsEntity?

    @Query("SELECT EXISTS (SELECT 1 FROM job_info_table WHERE id = :id)")
    suspend fun isVacancyFavorite(id: Long): Boolean
}

