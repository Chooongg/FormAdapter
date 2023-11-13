package com.chooongg.form.core.item

import android.view.Gravity
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormColorStateListBlock
import com.chooongg.form.core.provider.FormButtonProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset
import com.google.android.material.button.MaterialButton

class FormButton : BaseForm {

    enum class ButtonStyle {
        DEFAULT, TEXT, TONAL, OUTLINED, ELEVATED, UN_ELEVATED
    }

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    var buttonStyle: ButtonStyle = ButtonStyle.DEFAULT

    @MaterialButton.IconGravity
    var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    var icon: Any? = null

    var iconSize: Int? = null

    var iconTint: FormColorStateListBlock? = null

    override var typeset: BaseTypeset? = NoneTypeset()

    override var gravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter) = FormButtonProvider::class

}