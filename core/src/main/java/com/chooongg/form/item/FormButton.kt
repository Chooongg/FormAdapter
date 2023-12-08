package com.chooongg.form.item

import android.view.Gravity
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormColorStateListBlock
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormButtonProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.NoneTypeset
import com.google.android.material.button.MaterialButton
import kotlin.reflect.KClass

open class FormButton(name: Any?, field: String?) : BaseForm(name, field) {

    enum class ButtonStyle {
        ELEVATED, UN_ELEVATED,
        PRIMARY, PRIMARY_TONAL, PRIMARY_TEXT, PRIMARY_OUTLINED,
        SECONDARY, TONAL, TEXT, OUTLINED,
        TERTIARY, TERTIARY_TONAL, TERTIARY_TEXT, TERTIARY_OUTLINED,
        ERROR, ERROR_TONAL, ERROR_TEXT, ERROR_OUTLINED,
        CUSTOM1, CUSTOM2, CUSTOM3
    }

    /**
     * 按钮主题
     */
    var buttonStyle: ButtonStyle? = null

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