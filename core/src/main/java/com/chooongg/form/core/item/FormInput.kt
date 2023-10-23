package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormInputProvider
import com.chooongg.form.core.provider.FormTextProvider

open class FormInput : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    var placeholder: CharSequence? = null

    override fun getProvider(adapter: FormAdapter) = if (adapter.isEnabled) {
        FormInputProvider::class
    } else FormTextProvider::class

}