package ru.practicum.android.microhh.industry.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.IndustryState
import ru.practicum.android.microhh.industry.domain.api.IndustryRepository

class IndustryListUseCase(
    private val repository: IndustryRepository
) {

    operator fun invoke(): Flow<IndustryState> {
        return repository.getIndustries()
    }
}
