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

    /**
     * 按钮主题
     */
    var buttonStyle: ButtonStyle = ButtonStyle.DEFAULT

    /**
     * 图标重力
     */
    @MaterialButton.IconGravity
    var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    /**
     * 图标
     */
    var icon: Any? = null

    /**
     * 图标大小
     */
    var iconSize: Int? = null

    /**
     * 图标色调 (如果不想使用默认色调, 请实现Block返回null)
     */
    var iconTint: FormColorStateListBlock? = null

    /**
     * 文本重力
     */
    var textGravity: Int? = null

    override var typeset: BaseTypeset? = NoneTypeset()

    override var gravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormButtonProvider::class

}