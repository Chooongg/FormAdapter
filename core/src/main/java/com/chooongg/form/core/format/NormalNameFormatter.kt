package com.chooongg.form.core.format

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.res.use
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.R

class NormalNameFormatter : BaseNameFormatter() {
    override fun format(context: Context, item: BaseForm): CharSequence? {
        val name = item.getNameString(context)
        if (!item.required) return name
        return SpannableString("⋆$name").apply {
            val foregroundColorSpan = ForegroundColorSpan(
                context.obtainStyledAttributes(
                    intArrayOf(R.attr.colorError)
                ).use { it.getColor(0, Color.GRAY) }
            )
            setSpan(foregroundColorSpan, 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}