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
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout.BOX_BACKGROUND_FILLED
import com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.databinding.TextFieldHhBinding

class TextFieldHH @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.hhTextFieldStyle,
    @StyleRes defStyleRes: Int = R.style.TextFieldHHStyle,
) : LinearLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {
    private var binding: TextFieldHhBinding = TextFieldHhBinding.inflate(LayoutInflater.from(context), this)

    private var placeholderColor: Int = 0
    private var hintTxtColor: Int = 0
    private var hintTextColorFocused: Int = 0
    private var hintTextColorFilled: Int = 0
    private var textColor: Int = 0
    private var iconColor: Int = 0
    private var backgroundColor: Int = 0
    private var mode: Int = 0

    private val closeIcon: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_close)
    private val searchIcon: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_search)

    private var onTextChanged: (String) -> Unit = {}
    private var onTextCleared: () -> Unit = {}

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TextFieldHH,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                placeholderColor = getColor(R.styleable.TextFieldHH_placeholderTextColor, 0)
                hintTxtColor = getColor(R.styleable.TextFieldHH_hintTextColor, 0)
                hintTextColorFocused = getColor(R.styleable.TextFieldHH_hintTextColorFocused, 0)
                hintTextColorFilled = getColor(R.styleable.TextFieldHH_hintTextColorFilled, 0)
                textColor = getColor(R.styleable.TextFieldHH_textColor, 0)
                iconColor = getColor(R.styleable.TextFieldHH_iconColor, 0)
                backgroundColor = getColor(R.styleable.TextFieldHH_backgroundColor, 0)
                mode = getInt(R.styleable.TextFieldHH_mode, 0)

                binding.filledTextInputLayout.endIconMode = END_ICON_CUSTOM
                when (mode) {
                    0 -> {
                        with(binding.filledTextInputLayout) {
                            hintTextColor = ColorStateList.valueOf(hintTextColorFocused)
                            endIconDrawable = closeIcon
                            hint = getText(R.styleable.TextFieldHH_hintText)
                            defaultHintTextColor = if (!binding.filledTextInputLayout.editText?.text.isNullOrEmpty()) {
                                ColorStateList.valueOf(hintTextColorFilled)
                            } else {
                                ColorStateList.valueOf(hintTxtColor)
                            }
                            isEndIconVisible =
                                !binding.filledTextInputLayout.editText?.text.isNullOrEmpty()
                        }
                    }

                    else -> {
                        with(binding.filledTextInputLayout) {
                            endIconDrawable = searchIcon
                            editText?.let { editText ->
                                editText.setPadding(
                                    editText.paddingLeft,
                                    editText.paddingBottom,
                                    editText.paddingRight,
                                    editText.paddingBottom
                                )
                            }
                        }
                    }
                }

                with(binding.filledTextInputLayout) {
                    placeholderTextColor = ColorStateList.valueOf(placeholderColor)
                    editText?.setTextColor(textColor)
                    setEndIconTintList(ColorStateList.valueOf(iconColor))
                    boxBackgroundMode = BOX_BACKGROUND_FILLED
                    boxBackgroundColor = backgroundColor
                    placeholderText = getText(R.styleable.TextFieldHH_placeholderText)
                    editText?.setText(getText(R.styleable.TextFieldHH_text))
                }
            } finally {
                recycle()
            }
        }

        binding.filledTextInputLayout.editText?.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.filledTextInputLayout.hintTextColor = ColorStateList.valueOf(hintTextColorFocused)
            } else {
                if (!binding.filledTextInputLayout.editText?.text.isNullOrEmpty()) {
                    binding.filledTextInputLayout.defaultHintTextColor = ColorStateList.valueOf(hintTextColorFilled)
                } else {
                    binding.filledTextInputLayout.defaultHintTextColor = ColorStateList.valueOf(hintTxtColor)
                }
            }
        }

        binding.filledTextInputLayout.editText?.doOnTextChanged { text, _, _, _ ->
            onTextChanged(text.toString())
            if (binding.filledTextInputLayout.editText?.isFocused == false) {
                if (!text.isNullOrEmpty()) {
                    binding.filledTextInputLayout.defaultHintTextColor = ColorStateList.valueOf(hintTextColorFilled)

                } else if (binding.filledTextInputLayout.editText?.isFocused == false) {
                    binding.filledTextInputLayout.defaultHintTextColor = ColorStateList.valueOf(hintTxtColor)

                }
            }

            if (!text.isNullOrEmpty()) {
                when (mode) {
                    0 -> {
                        binding.filledTextInputLayout.isEndIconVisible = true
                    }

                    1 -> {
                        binding.filledTextInputLayout.endIconDrawable = closeIcon
                    }
                }
            } else {
                when (mode) {
                    0 -> {
                        binding.filledTextInputLayout.isEndIconVisible = false
                    }

                    1 -> {
                        binding.filledTextInputLayout.endIconDrawable = searchIcon
                    }
                }
            }
        }

        binding.filledTextInputLayout.setEndIconOnClickListener {
            binding.filledTextInputLayout.editText?.text?.clear()
            onTextCleared()
        }
    }

    fun setOnTextChanged(action: (String) -> Unit) {
        onTextChanged = action
    }

    fun setOnClearText(action: () -> Unit) {
        onTextCleared = action
    }
}
