package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.InternalFormDynamicAddButtonProvider

class InternalFormDynamicAddButton(name: Any?, field: String?) : FormButton(name, field) {
    override fun getProvider(adapter: FormAdapter) = InternalFormDynamicAddButtonProvider::class
}