package ru.practicum.android.microhh.workplace.domain.models

import kotlinx.serialization.Serializable
import ru.practicum.android.microhh.core.domain.models.Area

@Serializable
data class WorkplaceFilter(
    val country: Area? = null,
    val region: Area? = null,
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

    val isNull = country == null && region == null
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
