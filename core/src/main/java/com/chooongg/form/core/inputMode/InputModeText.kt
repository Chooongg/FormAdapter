package com.chooongg.form.core.inputMode

import android.text.InputFilter
import android.text.InputType

class InputModeText(private val emojiExclude: Boolean = false) : InputMode {
    override fun getInputType(): Int = InputType.TYPE_CLASS_TEXT
    override fun getFilters(): ArrayList<InputFilter> = ArrayList<InputFilter>().apply {
        if (emojiExclude) {
            add(EmojiExcludeFilter())
        }
    }
}