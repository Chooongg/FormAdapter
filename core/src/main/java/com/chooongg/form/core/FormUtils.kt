package com.chooongg.form.core

import android.content.Context
import android.graphics.Paint
import androidx.annotation.DimenRes
import androidx.annotation.StyleRes
import com.chooongg.utils.ext.resChildDimension
import com.chooongg.utils.ext.resDimension
import kotlin.math.ceil

object FormUtils {

    fun Context.getFontHeightInTextAppearance(@StyleRes resId: Int) = getFontHeight(
        resChildDimension(resId, android.R.attr.textSize, 0f)
    )

    fun Context.getFontHeightInResource(@DimenRes resId: Int) = getFontHeight(
        resDimension(resId)
    )

    fun getFontHeight(fontSize: Float): Int {
        val paint = Paint()
        paint.textSize = fontSize
        val fm = paint.fontMetrics
        return (ceil(fm.descent - fm.top) + fontSize).toInt()
    }
}