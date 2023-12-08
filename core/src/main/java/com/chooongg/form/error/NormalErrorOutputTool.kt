package com.chooongg.form.error

import android.content.Context
import android.widget.Toast

class NormalErrorOutputTool : ErrorOutputTool {

    override fun errorOutput(context: Context, message: CharSequence?) {
        if (message.isNullOrEmpty()) return
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun errorOutput(context: Context, messages: List<CharSequence>) {
        if (messages.isEmpty()) return
        Toast.makeText(context, buildString {
            messages.forEachIndexed { index, message ->
                if (index != 0) append('\n')
                append(message)
            }
        }, Toast.LENGTH_SHORT).show()
    }
}