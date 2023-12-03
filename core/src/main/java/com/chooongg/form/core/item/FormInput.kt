package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormInputProvider
import kotlin.reflect.KClass

open class FormInput : BaseOptionForm<CharSequence> {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    /**
     * 最小行数
     */
    var minLines: Int = 0

    /**
     * 最大行数
     */
    var maxLines: Int = Int.MAX_VALUE

    /**
     * 前缀: Int(StringRes), String, CharSequence
     */
    var prefix: Any? = null

    /**
     * 后缀: Int(StringRes), String, CharSequence
     */
    var suffix: Any? = null

    /**
     * 占位: Int(StringRes), String, CharSequence
     */
    var placeholder: Any? = null

    /**
     * 显示清除图标
     */
    var showClearIcon: Boolean = true

    /**
     * 计数器最大长度
     */
    var counterMaxLength: Int = Int.MAX_VALUE

    override fun hasOpenOperation() = false

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormInputProvider::class

}