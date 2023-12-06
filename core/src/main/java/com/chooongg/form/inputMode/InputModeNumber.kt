package com.chooongg.form.inputMode

import android.text.InputFilter
import android.text.InputType

class InputModeNumber : InputMode {
    override fun getInputType(): Int =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL

    override fun getFilters(): ArrayList<InputFilter> = ArrayList<InputFilter>().apply {
//        DigitsKeyListener.getInstance("1234567890")
    }
}