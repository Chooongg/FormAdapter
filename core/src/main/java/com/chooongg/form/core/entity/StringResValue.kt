package com.chooongg.form.core.entity

import android.content.Context
import androidx.annotation.StringRes

class StringResValue {

    constructor(@StringRes resId: Int?) {
        value = resId
    }

    constructor(charSequence: CharSequence?) {
        value = charSequence
    }

    private val value: Any?

    fun getString(context: Context): CharSequence? {
        return when (value) {
            is Int -> context.getString(value)
            is CharSequence -> value
            else -> null
        }
    }
}