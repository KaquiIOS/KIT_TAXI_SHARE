/*
 * Created by WonJongSeong on 2019-03-24
 */

package com.meongbyeol.taxishare.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class SuffixTextInputEditText : TextInputEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun getCompoundPaddingEnd(): Int {
        return super.getCompoundPaddingEnd()
    }

    private fun calculateSuffix() {
        val tag : String = this.tag as String


    }

}