package ru.practicum.android.microhh.core.presentation.ui.component

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.databinding.SalaryViewHhBinding

class SalaryViewHH @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.hhSelectableViewStyle,
    @StyleRes defStyleRes: Int = R.style.SalaryViewHHStyle,
) : LinearLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes,
) {

    private var binding: SalaryViewHhBinding = SalaryViewHhBinding.inflate(LayoutInflater.from(context), this)
    private var labelTextColor: Int = 0
    private var labelTextColorFocused: Int = 0
    private var textColor: Int = 0
    private var hintColor: Int = 0

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SalaryViewHH,
            defStyleAttr,
            defStyleRes,
        ).apply {
            try {
                labelTextColor = getColor(R.styleable.SalaryViewHH_labelTextColor, 0)
                labelTextColorFocused = getColor(R.styleable.SalaryViewHH_labelTextColorFocused, 0)
                textColor = getColor(R.styleable.SalaryViewHH_textColor, 0)
                hintColor = getColor(R.styleable.SalaryViewHH_hintTextColor, 0)

                with(binding) {
                    label.text = getText(R.styleable.SalaryViewHH_label)
                    text.hint = getText(R.styleable.SalaryViewHH_hintText)
                    label.setTextColor(ColorStateList.valueOf(labelTextColor))
                    text.setTextColor(ColorStateList.valueOf(textColor))
                    text.setHintTextColor(hintColor)

                    text.setOnFocusChangeListener { _, hasFocus ->
                        val textColor = if (hasFocus) {
                            labelTextColorFocused
                        } else {
                            labelTextColor
                        }

                        label.setTextColor(textColor)
                    }

                    text.doOnTextChanged { text, _, _, _ ->
                        val salary = text.toString()
                        trailingIcon.isVisible = salary.isNotEmpty()
                    }

                    trailingIcon.setOnClickListener {
                        text.text.clear()
                    }
                }
            } finally {
                recycle()
            }
        }

        this.orientation = HORIZONTAL
        this.gravity = Gravity.CENTER_VERTICAL
        background = AppCompatResources.getDrawable(context, R.drawable.rounded_shape_12)
    }
}
