package com.chooongg.form.core.option

import android.content.Context
import android.text.SpannableString
import android.text.style.RelativeSizeSpan

interface IOption {
    fun getOptionName(): String?
    fun getOptionSecondaryName(): String?
    fun getValue(): String?

    fun getSpannableString(context: Context): SpannableString {
        val optionName = getOptionName()
        val optionSecondaryName =
            getOptionSecondaryName() ?: return SpannableString(getOptionName() ?: "")
        if (optionName == null) {
            return SpannableString(optionSecondaryName).apply {
                setSpan(RelativeSizeSpan(0.8f), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return SpannableString("$optionName $optionSecondaryName").apply {
            setSpan(
                RelativeSizeSpan(0.8f),
                optionName.length + 1,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}