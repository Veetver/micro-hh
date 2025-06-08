package ru.practicum.android.microhh.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Catalog(
    val id: String = "",
    val name: String = "",
) : Parcelable
