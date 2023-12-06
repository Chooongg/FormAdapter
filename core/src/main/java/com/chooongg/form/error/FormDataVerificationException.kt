package com.chooongg.form.error

import com.chooongg.form.item.BaseForm

class FormDataVerificationException(
    val item: BaseForm,
    val type: ErrorType
) : RuntimeException() {
    sealed class ErrorType {
        data object Empty : ErrorType()
        data object Format : ErrorType()
        class Length(val size: Int) : ErrorType()
        class MinLength(val size: Int) : ErrorType()
        class MaxLength(val size: Int) : ErrorType()
        class Count(val count: Int) : ErrorType()
        class MinCount(val count: Int) : ErrorType()
        class MaxCount(val count: Int) : ErrorType()
        class Custom(val message: Any) : ErrorType()
    }
}