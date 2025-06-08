package ru.practicum.android.microhh.core.utils

import android.os.Build
import android.os.Bundle
import ru.practicum.android.microhh.core.domain.models.Catalog

object Util {

    @Suppress("DEPRECATION")
    fun getParcelable(bundle: Bundle, key: String): Catalog? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Catalog::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }
}
