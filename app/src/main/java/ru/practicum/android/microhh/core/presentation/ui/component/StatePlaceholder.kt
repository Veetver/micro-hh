package ru.practicum.android.microhh.core.presentation.ui.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.isVisible
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.databinding.StatePlaceholderHhBinding

class StatePlaceholder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.statePlaceholderStyle,
    @StyleRes defStyleRes: Int = R.style.StatePlaceholderHHStyle,
) : LinearLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {
    private var binding: StatePlaceholderHhBinding =
        StatePlaceholderHhBinding.inflate(LayoutInflater.from(context), this)

    var mode: StatePlaceholderMode = StatePlaceholderMode.Loading
        set(value) {
            processMode(value)
            isVisible = true
        }

    private val connectionErrorText: String?
    private val nothingFoundText: String?
    private val defaultImage: Drawable?
    private val loadingImage: Drawable?
    private val connectionErrorImage: Drawable?
    private val nothingFoundImage: Drawable?

    private val rotate = RotateAnimation(
        0F, 360F,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    ).apply {
        duration = 900
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StatePlaceholder,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                orientation = VERTICAL
                mode = StatePlaceholderMode.fromInt(getInt(R.styleable.StatePlaceholder_state, STATE_LOADING))

                connectionErrorText = getString(R.styleable.StatePlaceholder_connection_error_text)
                nothingFoundText = getString(R.styleable.StatePlaceholder_nothing_found_text)

                defaultImage = getDrawable(R.styleable.StatePlaceholder_default_image)
                loadingImage = getDrawable(R.styleable.StatePlaceholder_loading_image)
                connectionErrorImage = getDrawable(R.styleable.StatePlaceholder_connection_error_image)
                nothingFoundImage = getDrawable(R.styleable.StatePlaceholder_nothing_found_image)

                binding.textPlaceholder.setTextColor(getColor(R.styleable.StatePlaceholder_text_color, 0))
            } finally {
                recycle()
            }
        }
    }

    private fun processMode(mode: StatePlaceholderMode) {
        with(binding) {
            binding.imagePlaceholder.clearAnimation()

            when (mode) {
                StatePlaceholderMode.Default -> {
                    imagePlaceholder.setImageDrawable(defaultImage)
                    textPlaceholder.isVisible = false
                }

                StatePlaceholderMode.Loading -> {
                    imagePlaceholder.setImageDrawable(loadingImage)
                    imagePlaceholder.startAnimation(rotate)
                    textPlaceholder.isVisible = false
                }

                StatePlaceholderMode.ConnectionError -> {
                    imagePlaceholder.setImageDrawable(connectionErrorImage)
                    textPlaceholder.text = connectionErrorText
                    textPlaceholder.isVisible = true
                }

                StatePlaceholderMode.NothingFound -> {
                    imagePlaceholder.setImageDrawable(nothingFoundImage)
                    textPlaceholder.text = nothingFoundText
                    textPlaceholder.isVisible = true
                }
            }
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_LOADING = 1
        private const val STATE_CONNECTION_ERROR = 2
        private const val STATE_NOTHING_FOUND = 3
    }

    sealed interface StatePlaceholderMode {
        object Default : StatePlaceholderMode
        object Loading : StatePlaceholderMode
        object ConnectionError : StatePlaceholderMode
        object NothingFound : StatePlaceholderMode

        companion object {
            fun fromInt(value: Int): StatePlaceholderMode {
                return when (value) {
                    STATE_DEFAULT -> Default
                    STATE_LOADING -> Loading
                    STATE_CONNECTION_ERROR -> ConnectionError
                    STATE_NOTHING_FOUND -> NothingFound
                    else -> throw IllegalArgumentException("Unsupported mode value: $value")
                }
            }
        }
    }
}
