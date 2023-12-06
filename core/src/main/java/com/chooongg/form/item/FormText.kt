package com.chooongg.form.item

import androidx.annotation.StringRes
import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormTextProvider
import kotlin.reflect.KClass

class FormText : BaseForm {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormTextProvider::class

}