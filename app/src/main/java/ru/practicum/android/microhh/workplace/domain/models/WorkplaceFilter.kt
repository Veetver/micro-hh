package ru.practicum.android.microhh.workplace.domain.models

import kotlinx.serialization.Serializable
import ru.practicum.android.microhh.core.domain.models.Catalog

@Serializable
data class WorkplaceFilter(
    val country: Catalog? = null,
    val region: Catalog? = null,
) {
    val title: String
        get() {
            val sb = StringBuilder()

            country?.let {
                sb.append(country.name)
            }

            region?.let {
                sb.append(", ")
                sb.append(region.name)
            }

            return sb.toString()
        }

    @Transient
    val filterId: String? = region?.id ?: country?.id
}
