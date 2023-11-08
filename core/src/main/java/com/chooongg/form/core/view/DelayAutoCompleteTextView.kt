package com.chooongg.form.core.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import com.google.android.material.textfield.MaterialAutoCompleteTextView


class DelayAutoCompleteTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialAutoCompleteTextView(context, attributeSet, defStyleAttr) {

    companion object {
        const val MESSAGE_TEXT_CHANGED = 100
        const val DEFAULT_AUTOCOMPLETE_DELAY = 750L
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super@DelayAutoCompleteTextView.performFiltering(msg.obj as CharSequence, msg.arg1)
        }
    }

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
        handler.sendMessageDelayed(
            handler.obtainMessage(MESSAGE_TEXT_CHANGED, text),
            DEFAULT_AUTOCOMPLETE_DELAY
        )
    }

    override fun onFilterComplete(count: Int) {
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
        super.onFilterComplete(count)
    }
}