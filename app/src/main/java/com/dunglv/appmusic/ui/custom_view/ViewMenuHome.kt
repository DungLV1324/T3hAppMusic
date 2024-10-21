package com.dunglv.appmusic.ui.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ViewMenuHomeBinding

class ViewMenuHome : FrameLayout {

    private var binding: ViewMenuHomeBinding =
        ViewMenuHomeBinding.inflate(LayoutInflater.from(context), this, true)
    private var textBottom: String = ""
        set(value) {
            field = value
            binding.tvName.text = value
            when (value) {
                context.getString(R.string.favourite) -> setView(R.drawable.ic_favorite,R.drawable.custom_color_favorite)
                context.getString(R.string.playlist) -> setView(R.drawable.ic_song,R.drawable.custom_color_song)
                context.getString(R.string.recently) -> setView(R.drawable.ic_clock,R.drawable.custom_color_clock)
            }
        }

    private fun setView(image:Int,background: Int) {
        binding.imImage.setBackgroundResource(image)
        binding.vBackground.setBackgroundResource(background)
    }

    private var imTop: Int = 0
        set(value) {
            field = value
            binding.imImage.setImageResource(value)
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typeArray: TypedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ViewMenuHome, 0, 0)
        textBottom = typeArray.getString(R.styleable.ViewMenuHome_textBottom).toString()
        imTop= typeArray.getIndex(R.styleable.ViewMenuHome_imTop)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)
}