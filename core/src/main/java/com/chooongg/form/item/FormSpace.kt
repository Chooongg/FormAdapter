package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormSpaceProvider
import kotlin.reflect.KClass

class FormSpace : BaseForm(null, null) {
    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormSpaceProvider::class
}