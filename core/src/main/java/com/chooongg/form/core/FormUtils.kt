package com.chooongg.form.core

import android.graphics.Paint
import android.view.View
import androidx.annotation.AttrRes
import androidx.core.content.res.use
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.style.BaseStyle
import com.chooongg.form.core.typeset.BaseTypeset
import kotlin.math.ceil

object FormUtils {

//    fun Context.getFontHeightInTextAppearance(@StyleRes resId: Int) = getFontHeight(
//        resChildDimension(resId, android.R.attr.textSize, 0f)
//    )
//
//    fun Context.getFontHeightInResource(@DimenRes resId: Int) = getFontHeight(
//        resDimension(resId)
//    )

    fun getFontHeight(fontSize: Float): Int {
        val paint = Paint()
        paint.textSize = fontSize
        val fm = paint.fontMetrics
        return (ceil(fm.descent - fm.top) + fontSize).toInt()
    }
}

fun BaseFormProvider.formTextAppearance(view: View, @AttrRes resId: Int): Int =
    getTextAppearance(view, resId)

fun BaseTypeset.formTextAppearance(view: View, @AttrRes resId: Int): Int =
    getTextAppearance(view, resId)

fun BaseStyle.formTextAppearance(view: View, @AttrRes resId: Int): Int =
    getTextAppearance(view, resId)

private fun getTextAppearance(view: View, @AttrRes resId: Int): Int =
    view.context.obtainStyledAttributes(intArrayOf(resId)).use {
        it.getResourceId(
            0, when (resId) {
                R.attr.formTextAppearanceName -> R.style.Form_TextAppearance_Name
                R.attr.formTextAppearanceContent -> R.style.Form_TextAppearance_Content
                R.attr.formTextAppearanceHint -> R.style.Form_TextAppearance_Hint
                R.attr.formTextAppearancePrefix -> R.style.Form_TextAppearance_Prefix
                R.attr.formTextAppearanceSuffix -> R.style.Form_TextAppearance_Suffix
                R.attr.formTextAppearancePlaceholder -> R.style.Form_TextAppearance_Placeholder
                R.attr.formTextAppearanceLabel -> R.style.Form_TextAppearance_Label
                R.attr.formTextAppearanceTip -> R.style.Form_TextAppearance_Tip
                else -> 0
            }
        )
    }