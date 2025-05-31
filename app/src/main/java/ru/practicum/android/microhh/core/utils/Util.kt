package ru.practicum.android.microhh.core.utils

object Util {

    fun formatValue(value: Int, valueName: String): String {
        val valueNameToFormat = valueName.split(",")
        val preLastNumber = value % 100 / 10
        val lastNumber = value % 10

        if (preLastNumber == 1) {
            return "$value ${valueNameToFormat[2]}"
        }

        return when (lastNumber) {
            1 -> "$value ${valueNameToFormat[0]}"
            2, 3, 4 -> "$value ${valueNameToFormat[1]}"
            else -> "$value ${valueNameToFormat[2]}"
        }
    }
}
