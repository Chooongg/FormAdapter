package com.chooongg.form.core.format

import android.content.Context
import com.chooongg.form.core.item.BaseForm

abstract class BaseNameFormatter {
    abstract fun format(context: Context, item: BaseForm): CharSequence?

    override fun equals(other: Any?): Boolean {
        return other is BaseNameFormatter && javaClass == other.javaClass
    }
}