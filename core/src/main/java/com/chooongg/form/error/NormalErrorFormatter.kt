package com.chooongg.form.error

import android.content.Context
import com.chooongg.form.FormUtils
import com.chooongg.form.core.R

class NormalErrorFormatter : ErrorFormatter {
    override fun format(context: Context, exception: FormDataVerificationException): CharSequence {
        val name = FormUtils.getText(context, exception.item.name)
        return when (exception.type) {
            is FormDataVerificationException.ErrorType.Empty -> {
                context.getString(R.string.formErrorEmpty, name)
            }

            is FormDataVerificationException.ErrorType.Format -> {
                context.getString(R.string.formErrorFormat, name)
            }

            is FormDataVerificationException.ErrorType.Length -> {
                context.getString(R.string.formErrorLength, name, exception.type.size)
            }

            is FormDataVerificationException.ErrorType.MinLength -> {
                context.getString(R.string.formErrorLengthMin, name, exception.type.size)
            }

            is FormDataVerificationException.ErrorType.MaxLength -> {
                context.getString(R.string.formErrorLengthMax, name, exception.type.size)
            }

            is FormDataVerificationException.ErrorType.Count -> {
                context.getString(R.string.formErrorSize, name, exception.type.count)
            }

            is FormDataVerificationException.ErrorType.MinCount -> {
                context.getString(R.string.formErrorSizeMin, name, exception.type.count)
            }

            is FormDataVerificationException.ErrorType.MaxCount -> {
                context.getString(R.string.formErrorSizeMax, name, exception.type.count)
            }

            is FormDataVerificationException.ErrorType.Custom -> {
                FormUtils.getText(context, exception.type.message)!!
            }
        }
    }
}