package ru.practicum.android.microhh.core.db

import ru.practicum.android.microhh.core.domain.models.JobInfo

class JobInfoDBConvertor {
    fun map(jobInfo: JobInfo): JobInfoEntity {
        return JobInfoEntity(
            jobInfo.id,
            jobInfo.name,
            jobInfo.areaName,
            jobInfo.employerName,
            jobInfo.salaryFrom,
            jobInfo.salaryTo,
            jobInfo.currency,
            jobInfo.experience,
            jobInfo.employmentFormName,
            jobInfo.workFormatName,
            jobInfo.description,
            jobInfo.keySkills,
            System.currentTimeMillis().toString(),
            jobInfo.employerLogo
        )
    }

    fun map(jobInfo: JobInfoEntity?): JobInfo? {
        if (jobInfo == null) {
            return null
        }
        return JobInfo(
            jobInfo.id,
            jobInfo.name,
            jobInfo.areaName,
            jobInfo.employerName,
            jobInfo.salaryFrom,
            jobInfo.salaryTo,
            jobInfo.currency,
            jobInfo.experience,
            jobInfo.employmentFormName,
            jobInfo.workFormatName,
            jobInfo.description,
            jobInfo.keySkills,
            jobInfo.employerLogo
        )
    }
}
