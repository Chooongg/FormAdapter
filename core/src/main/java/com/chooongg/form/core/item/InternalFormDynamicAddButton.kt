package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.InternalFormDynamicAddButtonProvider

class InternalFormDynamicAddButton(name: Any?, field: String?) : FormButton(name, field) {
    override fun getProvider(adapter: FormAdapter) = InternalFormDynamicAddButtonProvider::class
}