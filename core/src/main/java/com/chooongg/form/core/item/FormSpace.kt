package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormSpaceProvider

class FormSpace : BaseForm(null, null) {
    override fun getProvider(adapter: FormAdapter) = FormSpaceProvider::class
}