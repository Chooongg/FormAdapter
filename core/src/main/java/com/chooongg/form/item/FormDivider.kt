package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.FormColorBlock
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormDividerProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.NoneTypeset
import kotlin.reflect.KClass

open class FormDivider : BaseForm(null, null) {

    /**
     * 分割线颜色
     */
    var color: FormColorBlock? = null

    /**
     * 分割线厚度
     */
    var thickness: Int? = null

    /**
     * 匹配父布局边缘
     */
    var matchParentEdge: Boolean = false

    /**
     * 匹配父布局起始边缘
     */
    var matchParentStartEdge: Boolean? = null

    /**
     * 匹配父布局结束边缘
     */
    var matchParentEndEdge: Boolean? = null

    override var loneLine: Boolean = true

    override var showAtEdge: Boolean = false

    override var typeset: BaseTypeset? = NoneTypeset()

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormDividerProvider::class
}