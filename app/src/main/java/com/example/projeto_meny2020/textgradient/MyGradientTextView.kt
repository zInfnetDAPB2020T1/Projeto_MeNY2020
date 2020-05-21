package com.example.projeto_meny2020.textgradient

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat

class MyGradientTextView : androidx.appcompat.widget.AppCompatTextView {

    var primaryColor: Int = 0
    var secondaryColor: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setColors(primaryColor: Int, secondaryColor: Int){
        this.primaryColor = primaryColor
        this.secondaryColor = secondaryColor
    }
    fun setColors2(primaryColor: Int, secondaryColor: Int){
        this.primaryColor = primaryColor
        this.secondaryColor = secondaryColor
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if(changed){
            paint.shader = LinearGradient(0f, 0f, width.toFloat(), height.toFloat(),
                ContextCompat.getColor(context, primaryColor),
                ContextCompat.getColor(context, secondaryColor),
                Shader.TileMode.CLAMP)
        }

    }
}