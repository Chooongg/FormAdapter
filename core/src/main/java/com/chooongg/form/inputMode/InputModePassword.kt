package com.chooongg.form.inputMode

import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod

class InputModePassword : InputMode {
    override fun getInputType(): Int =
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

    override fun getTransformationMethod(): TransformationMethod? =
        PasswordTransformationMethod.getInstance()

    override fun isNeedPasswordToggle(): Boolean = true
    override fun getFilters(): ArrayList<InputFilter> = arrayListOf(EmojiExcludeFilter())
}