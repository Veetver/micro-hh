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
    val isNull = country == null && region == null

    @Transient
    val isNotNull = !isNull
    val ids: String
        get() {
            val sb = StringBuilder()

            country?.let {
                sb.append(country.id)
            }

            region?.let {
                sb.append(", ")
                sb.append(region.id)
            }

            return sb.toString()
        }
}
