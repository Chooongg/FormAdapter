package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormColorStateListBlock
import com.chooongg.form.core.FormItemClickBlock
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.error.FormDataVerificationException
import com.chooongg.form.core.inputMode.InputMode
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormInputProvider
import kotlin.reflect.KClass

open class FormInput : BaseOptionForm<CharSequence> {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    /**
     * 输入模式
     */
    var inputMode: InputMode = FormManager.Default.inputMode

    /**
     * 最小行数
     */
    var minLines: Int = 0

    /**
     * 最大行数
     */
    var maxLines: Int = Int.MAX_VALUE

    /**
     * 最小长度
     */
    var minLength: Int? = null

    /**
     * 最大长度
     */
    var maxLength: Int? = null

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
     * 显示计数器
     */
    var showCounter: Boolean = false

    /**
     * 计数器最大长度
     */
    var counterMaxLength: Int? = null

    /**
     * 起始图标
     */
    var startIcon: Any? = null

    /**
     * 起始图标色调
     */
    var startIconTint: FormColorStateListBlock? = null

    private var startIconClickBlock: FormItemClickBlock? = null

    /**
     * 起始图标点击事件
     */
    fun startIconOnClickListener(block: FormItemClickBlock?) {
        startIconClickBlock = block
    }

    fun getStartIconOnClickListener() = startIconClickBlock

    override fun hasOpenOperation() = false

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormInputProvider::class

    override fun initValue(value: Any?) {
        content = value?.toString()
    }

    override fun dataVerification() {
        if (required) {
            if (content == null) {
                throw FormDataVerificationException(
                    this, FormDataVerificationException.ErrorType.Empty
                )
            }
        }
        if (content != null) {
            val length = content.toString().length
            if (minLength != null && length < minLength!!) {
                throw FormDataVerificationException(
                    this, FormDataVerificationException.ErrorType.MinLength(minLength!!)
                )
            }
            if (maxLength != null && length > maxLength!!) {
                throw FormDataVerificationException(
                    this, FormDataVerificationException.ErrorType.MaxLength(maxLength!!)
                )
            }
        }
    }
}