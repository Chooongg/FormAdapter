package com.chooongg.form.error

import android.content.Context
import android.widget.Toast
import com.chooongg.form.FormUtils
import com.chooongg.form.core.R

class DefaultErrorOutputTool : ErrorOutputTool {
    override fun errorOutput(context: Context, exception: FormDataVerificationException) {
        val name = FormUtils.getText(context, exception.item.name)
        when (exception.type) {
            is FormDataVerificationException.ErrorType.Empty -> Toast.makeText(
                context,
                context.getString(R.string.formErrorEmpty, name),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.Format -> Toast.makeText(
                context,
                context.getString(R.string.formErrorFormat, name),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.Length -> Toast.makeText(
                context,
                context.getString(R.string.formErrorLength, name, exception.type.size),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.MinLength -> Toast.makeText(
                context,
                context.getString(R.string.formErrorLengthMin, name, exception.type.size),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.MaxLength -> Toast.makeText(
                context,
                context.getString(R.string.formErrorLengthMax, name, exception.type.size),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.Count -> Toast.makeText(
                context,
                context.getString(R.string.formErrorSize, name, exception.type.count),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.MinCount -> Toast.makeText(
                context,
                context.getString(R.string.formErrorSizeMin, name, exception.type.count),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.MaxCount -> Toast.makeText(
                context,
                context.getString(R.string.formErrorSizeMax, name, exception.type.count),
                Toast.LENGTH_SHORT
            ).show()

            is FormDataVerificationException.ErrorType.Custom -> Toast.makeText(
                context,
                FormUtils.getText(context, exception.type.message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}