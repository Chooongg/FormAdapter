package com.chooongg.form.error

import android.content.Context

/**
 * 错误输出工具
 */
interface ErrorOutputTool {

    /**
     * 错误输出
     */
    fun errorOutput(context: Context, message: CharSequence?)

    /**
     * 多项错误输出
     */
    fun errorOutput(context: Context, messages: List<CharSequence>)
}