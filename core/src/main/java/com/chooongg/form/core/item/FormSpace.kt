package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormSpaceProvider
import kotlin.reflect.KClass

class FormSpace : BaseForm(null, null) {
    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormSpaceProvider::class
}