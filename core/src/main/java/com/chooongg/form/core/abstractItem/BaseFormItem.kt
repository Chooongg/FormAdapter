package com.chooongg.form.core.abstractItem

import com.chooongg.form.core.enum.FormValidateStatus

open class BaseFormItem(
    /**
     * 标签文本
     */
    var label: CharSequence?
) : AbstractFormItem() {

    /**
     * 提示
     */
    var hint: CharSequence? = null

    /**
     * 表单的内容
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

}