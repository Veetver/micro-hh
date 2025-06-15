package ru.practicum.android.microhh.industry.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.CatalogState
import ru.practicum.android.microhh.industry.domain.api.IndustryRepository

class IndustryListUseCase(
    private val repository: IndustryRepository
) {

    operator fun invoke(): Flow<CatalogState> {
        return repository.getIndustries()
    }
}
