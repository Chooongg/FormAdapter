package com.chooongg.form.core.option

import android.content.Context
import android.text.SpannableString
import android.text.style.RelativeSizeSpan

abstract class IOption {

    abstract fun getOptionName(): String?
    abstract fun getOptionSecondaryName(): String?
    abstract fun getValue(): String?

    open fun getSpannableString(context: Context): SpannableString {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IOption) return false

        if (getValue() != other.getValue()) return false

        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

}