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
    private val serverErrorText: String?
    private val noListText: String?

    private val defaultImage: Drawable?
    private val loadingImage: Drawable?
    private val connectionErrorImage: Drawable?
    private val nothingFoundImage: Drawable?
    private val serverErrorImage: Drawable?
    private val noListImage: Drawable?

    private val rotate = RotateAnimation(
        ANIM_FROM_DEGREES,
        ANIM_TO_DEGREES,
        Animation.RELATIVE_TO_SELF,
        ANIM_PIVOT_X,
        Animation.RELATIVE_TO_SELF,
        ANIM_PIVOT_Y,
    ).apply {
        duration = ANIM_DURATION
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
                serverErrorText = getString(R.styleable.StatePlaceholder_server_error_text)
                noListText = getString(R.styleable.StatePlaceholder_no_data_text)

                defaultImage = getDrawable(R.styleable.StatePlaceholder_default_image)
                loadingImage = getDrawable(R.styleable.StatePlaceholder_loading_image)
                connectionErrorImage = getDrawable(R.styleable.StatePlaceholder_connection_error_image)
                nothingFoundImage = getDrawable(R.styleable.StatePlaceholder_nothing_found_image)
                serverErrorImage = getDrawable(R.styleable.StatePlaceholder_server_error_image)
                noListImage = getDrawable(R.styleable.StatePlaceholder_no_data_image)

                binding.textPlaceholder.setTextColor(getColor(R.styleable.StatePlaceholder_textColor, 0))
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
                StatePlaceholderMode.NothingFound -> {
                    imagePlaceholder.setImageDrawable(nothingFoundImage)
                    textPlaceholder.text = nothingFoundText
                    textPlaceholder.isVisible = true
                }
                StatePlaceholderMode.ServerError -> {
                    imagePlaceholder.setImageDrawable(serverErrorImage)
                    textPlaceholder.text = serverErrorText
                    textPlaceholder.isVisible = true
                }
                StatePlaceholderMode.NoData -> {
                    imagePlaceholder.setImageDrawable(noListImage)
                    textPlaceholder.text = noListText
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
        private const val STATE_SERVER_ERROR = 4
        private const val STATE_NO_DATA = 5

        private const val ANIM_DURATION = 900L
        private const val ANIM_FROM_DEGREES = 0F
        private const val ANIM_TO_DEGREES = 360F
        private const val ANIM_PIVOT_X = 0.5F
        private const val ANIM_PIVOT_Y = 0.5F
    }

    sealed interface StatePlaceholderMode {
        object Default : StatePlaceholderMode
        object Loading : StatePlaceholderMode
        object ConnectionError : StatePlaceholderMode
        object NothingFound : StatePlaceholderMode
        object ServerError : StatePlaceholderMode
        object NoData : StatePlaceholderMode

        companion object {
            fun fromInt(value: Int): StatePlaceholderMode {
                return when (value) {
                    STATE_DEFAULT -> Default
                    STATE_LOADING -> Loading
                    STATE_CONNECTION_ERROR -> ConnectionError
                    STATE_NOTHING_FOUND -> NothingFound
                    STATE_SERVER_ERROR -> ServerError
                    STATE_NO_DATA -> NoData
                    else -> throw IllegalArgumentException("Unsupported mode value: $value")
                }
            }
        }
    }
}