package com.chooongg.form.error

import android.content.Context

/**
 * 错误格式化工具
 */
interface ErrorFormatter {
    fun format(context: Context, exception: FormDataVerificationException): CharSequence?
}