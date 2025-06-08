package ru.practicum.android.microhh.core.presentation.ui.component

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.resources.FiltersDataState
import ru.practicum.android.microhh.databinding.SelectableViewHhBinding

class SelectableViewHH @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.hhSelectableViewStyle,
    @StyleRes defStyleRes: Int = R.style.SelectableViewHHStyle,
) : LinearLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes,
) {

    private var binding: SelectableViewHhBinding = SelectableViewHhBinding.inflate(LayoutInflater.from(context), this)
    private var labelTextColor: Int = 0
    private var labelTextColorFilled: Int = 0
    private var textColor: Int = 0
    private var textColorFilled: Int = 0
    private val arrowIcon: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_arrow_forward)
    private val clearIcon: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_close)
    private var labelText: String = ""
    private var state: Int = 0
    private var onTextChanged: (String) -> Unit = {}
    val text: String
        get() {
            val currentText = binding.text.text.toString()
            return if (currentText != labelText) currentText else ""
        }

    init {
        this.orientation = HORIZONTAL
        this.gravity = Gravity.CENTER_VERTICAL

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SelectableViewHH,
            defStyleAttr,
            defStyleRes,
        ).apply {
            try {
                labelTextColor = getColor(R.styleable.SelectableViewHH_labelTextColor, 0)
                labelTextColorFilled = getColor(R.styleable.SelectableViewHH_textColorFilled, 0)
                textColor = getColor(R.styleable.SelectableViewHH_textColor, 0)
                textColorFilled = getColor(R.styleable.SelectableViewHH_textColorFilled, 0)
                state = getInt(R.styleable.SelectableViewHH_dataState, 0)

                labelText = getText(R.styleable.SelectableViewHH_text).toString()
                binding.text.text = labelText
            } finally {
                recycle()
            }
        }

        updateFiltersDataState()
        binding.trailingIcon.setOnClickListener {
            onTextCleared()
        }
    }

    fun setText(text: String) {
        if (text.isEmpty()) {
            onTextCleared()
            return
        }

        binding.label.text = labelText
        binding.text.text = text
        state = FiltersDataState.FILLED.code
        updateFiltersDataState()
    }

    fun setOnTextChange(action: (String) -> Unit) {
        onTextChanged = action
    }

    private fun onTextCleared() {
        binding.text.text = labelText
        binding.label.text = ""
        state = FiltersDataState.EMPTY.code
        updateFiltersDataState()
    }

    private fun updateFiltersDataState() {
        var labelTextColorVar = 0
        var textColorVar = 0
        var trailingIconVar: Drawable? = null

        onTextChanged(text)

        when (state) {
            FiltersDataState.EMPTY.code -> {
                labelTextColorVar = labelTextColor
                textColorVar = textColor
                trailingIconVar = arrowIcon
            }
            FiltersDataState.FILLED.code -> {
                labelTextColorVar = labelTextColorFilled
                textColorVar = textColorFilled
                trailingIconVar = clearIcon
            }
        }

        with(binding) {
            label.setTextColor(ColorStateList.valueOf(labelTextColorVar))
            text.setTextColor(ColorStateList.valueOf(textColorVar))
            trailingIcon.setImageDrawable(trailingIconVar)
            label.isVisible = state == FiltersDataState.FILLED.code
        }
    }
}
