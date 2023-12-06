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
        class SelectSize(val count: Int) : ErrorType()
        class SelectMinSize(val count: Int) : ErrorType()
        class SelectMaxSize(val count: Int) : ErrorType()
        class Custom(val message: Any) : ErrorType()
    }
}