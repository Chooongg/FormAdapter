package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormTextProvider
import kotlin.reflect.KClass

class FormText(name: Any?, field: String?) : BaseForm(name, field) {

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormTextProvider::class

}