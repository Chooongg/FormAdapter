package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormInputProvider
import com.chooongg.form.core.provider.FormTextProvider

open class FormInput : BaseOptionForm<CharSequence> {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

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
     * 计数器最大长度
     */
    var counterMaxLength: Int = Int.MAX_VALUE

    override fun hasOpenOperation() = false

    override fun getProvider(adapter: FormAdapter) = if (adapter.isEnabled) {
        FormInputProvider::class
    } else FormTextProvider::class

}