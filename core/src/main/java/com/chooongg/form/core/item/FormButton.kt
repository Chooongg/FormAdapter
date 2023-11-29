package com.chooongg.form.core.item

import android.view.Gravity
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormColorStateListBlock
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormButtonProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset
import com.google.android.material.button.MaterialButton
import kotlin.reflect.KClass

open class FormButton : BaseForm {

    enum class ButtonStyle {
        DEFAULT, TEXT, TONAL, OUTLINED, ELEVATED, UN_ELEVATED
    }

    internal constructor(name: Any?, field: String?) : super(name, field)
    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    var buttonStyle: ButtonStyle = ButtonStyle.DEFAULT

    @MaterialButton.IconGravity
    var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    var icon: Any? = null

    var iconSize: Int? = null

    var iconTint: FormColorStateListBlock? = null

    override var typeset: BaseTypeset? = NoneTypeset()

    override var gravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormButtonProvider::class

}