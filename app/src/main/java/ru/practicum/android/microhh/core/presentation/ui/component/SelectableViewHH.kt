package ru.practicum.android.microhh.core.presentation.ui.component

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.databinding.SelectableViewHhBinding

class SelectableViewHH @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.hhTextFieldStyle,
    @StyleRes defStyleRes: Int = R.style.TextFieldHHStyle,
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
    private var data: Int = 0
    private var onTextCleared: () -> Unit = {}

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SelectableViewHH,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                labelTextColor = getColor(R.styleable.SelectableViewHH_labelTextColor, 0)
                labelTextColorFilled = getColor(R.styleable.SelectableViewHH_textColorFilled, 0)
                textColor = getColor(R.styleable.SelectableViewHH_textColor, 0)
                textColorFilled = getColor(R.styleable.SelectableViewHH_textColorFilled, 0)
                data = getInt(R.styleable.SelectableViewHH_data, 0)

                binding.label.text = getText(R.styleable.SelectableViewHH_label)
                binding.textView.text = getText(R.styleable.SelectableViewHH_text)

                val _labelTextColor: Int
                val _textColor: Int
                val _trailingIcon: Drawable?

                when (data) {
                    0 -> {
                        _labelTextColor = labelTextColor
                        _textColor = textColor
                        _trailingIcon = arrowIcon
                    }

                    else -> {
                        _labelTextColor = labelTextColorFilled
                        _textColor = textColorFilled
                        _trailingIcon = clearIcon
                    }
                }

                with(binding) {
                    label.setTextColor(ColorStateList.valueOf(_labelTextColor))
                    textView.setTextColor(ColorStateList.valueOf(_textColor))
                    trailingIcon.setImageDrawable(_trailingIcon)
                }
            } finally {
                recycle()
            }
        }

        binding.trailingIcon.setOnClickListener {
            binding.textView.text = ""
            onTextCleared()
        }
    }

    fun setOnClearText(action: () -> Unit) {
        onTextCleared = action
    }
}
