package ru.practicum.android.microhh.core.utils

object Util {

    private const val FORM_SINGLE = 0
    private const val FORM_FEW = 1
    private const val FORM_MANY = 2
    private const val TENS = 10
    private const val HUNDREDS = 100
    
    fun formatValue(value: Int, valueName: String): String {
        val valueNameToFormat = valueName.split(",")
        val preLastNumber = value % HUNDREDS / TENS
        val lastNumber = value % TENS

        if (preLastNumber == 1) {
            return "$value ${valueNameToFormat[FORM_MANY]}"
        }

        return when (lastNumber) {
            1 -> "$value ${valueNameToFormat[FORM_SINGLE]}"
            2, 3, 4 -> "$value ${valueNameToFormat[FORM_FEW]}"
            else -> "$value ${valueNameToFormat[FORM_MANY]}"
        }
    }
}
