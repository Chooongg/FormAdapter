package com.chooongg.form.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.chooongg.form.core.nameProvider.BasePartNameProvider
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.style.BaseStyle
import com.chooongg.form.core.typeset.BaseTypeset

object FormUtils {

    fun getFontHeight(textView: TextView): Int {
        val fm = textView.paint.fontMetricsInt
        return textView.paint.getFontMetricsInt(fm)
    }

    fun getText(context: Context, any: Any?) = when (any) {
        is Int -> context.getString(any)
        is CharSequence -> any
        else -> any?.toString()
    }

    fun hideIme(view: View) {
        ContextCompat.getSystemService(view.context, InputMethodManager::class.java)
            ?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getActivity(context: Context): Activity? {
        if (context is Activity) return context
        var tempContext: Context? = context
        while (tempContext != null) {
            if (tempContext is Activity) return tempContext
            tempContext = (tempContext as? ContextWrapper)?.baseContext
        }
        return null
    }
}

fun BaseStyle.formTextAppearance(view: View, @AttrRes resId: Int): Int =
    getTextAppearance(view, resId)

fun BasePartNameProvider.formTextAppearance(view: View, @AttrRes resId: Int): Int =
    getTextAppearance(view, resId)

fun BaseTypeset.formTextAppearance(view: View, @AttrRes resId: Int): Int =
    getTextAppearance(view, resId)

fun BaseFormProvider.formTextAppearance(view: View, @AttrRes resId: Int): Int =
    getTextAppearance(view, resId)

internal fun getTextAppearance(view: View, @AttrRes resId: Int): Int =
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