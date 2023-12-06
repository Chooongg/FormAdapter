package com.chooongg.form.format

import android.content.Context
import com.chooongg.form.item.BaseForm

/**
 * 名称格式化工具
 */
abstract class BaseNameFormatter {

    abstract fun format(context: Context, item: BaseForm): CharSequence?

    override fun equals(other: Any?): Boolean {
        return other is BaseNameFormatter && javaClass == other.javaClass
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}