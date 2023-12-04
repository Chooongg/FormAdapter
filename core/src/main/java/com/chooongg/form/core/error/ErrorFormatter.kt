package com.chooongg.form.core.error

import android.content.Context

interface ErrorFormatter {
    fun errorOutput(context: Context, exception: FormDataVerificationException)
}