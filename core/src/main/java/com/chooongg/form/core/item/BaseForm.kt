package com.chooongg.form.core.item

import android.content.Context
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.enum.FormValidateStatus
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.typeset.BaseTypeset
import kotlin.reflect.KClass

abstract class BaseForm(
    /**
     * 名称: Int(StringRes), String, CharSequence
     */
    var name: Any?
) : AbstractForm() {

    abstract fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider>

    /**
     * 字段
     */
    var field: String? = null

    /**
     * 提示: Int(StringRes), String, CharSequence
     */
    var hint: Any? = null

    /**
     * 内容
     */
    var content: Any? = null

    /**
     * 是否为必填项
     */
    var required: Boolean? = null

    /**
     * 验证表单项
     */
    var validate: Boolean = false

    /**
     * 效验的状态
     */
    var validateStatus: FormValidateStatus? = null

    /**
     * 排版
     */
    open var typeset: BaseTypeset? = null

    open fun getContentString(context: Context): CharSequence? = when (content) {
        is Int -> context.getString(content as Int)
        is CharSequence -> content as CharSequence
        else -> content?.toString()
    }

    open fun getHintString(context: Context): CharSequence? = when (hint) {
        is Int -> context.getString(hint as Int)
        is CharSequence -> hint as CharSequence
        else -> hint?.toString()
    }

    fun getNameString(context: Context): CharSequence? {
        return when (val temp = name) {
            is String -> temp
            is CharSequence -> temp
            is Int -> try {
                context.getString(temp)
            } catch (e: Exception) {
                null
            }

            else -> temp?.toString()
        }
    }
}