package com.chooongg.form.enum

/**
 * 效验状态
 */
sealed interface FormValidateStatus {

    /**
     * 成功
     */
    class Success(val message: CharSequence?) : FormValidateStatus

    /**
     * 错误
     */
    class Error(val message: CharSequence?) : FormValidateStatus
}