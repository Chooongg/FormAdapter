package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.FormColorStateListBlock
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormMenuProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.NoneTypeset
import kotlin.reflect.KClass

open class FormMenu(name: Any?, field: String?) : BaseForm(name, field) {

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
     * 名称颜色
     */
    var nameColor: FormColorStateListBlock? = null

    /**
     * 内容颜色
     */
    var hintColor: FormColorStateListBlock? = null

    /**
     * 气泡文本
     * Int: > 99 显示 99+
     * String: 直接显示
     */
    var bubbleText: Any? = null

    /**
     * 气泡颜色
     */
    var bubbleColor: FormColorStateListBlock? = null

    /**
     * 气泡上的颜色
     */
    var bubbleOnColor: FormColorStateListBlock? = null

    /**
     * 显示下一步图标
     */
    var showNextIcon: Boolean = true

    override var typeset: BaseTypeset? = NoneTypeset()

    override var preventAdjustmentBoundaries: Boolean = true

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormMenuProvider::class

}