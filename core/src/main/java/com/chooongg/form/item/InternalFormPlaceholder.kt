package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.InternalFormPlaceholderProvider

class InternalFormPlaceholder internal constructor() : BaseForm(null, null) {

    override var loneLine: Boolean = true

    override fun getProvider(adapter: FormAdapter) = InternalFormPlaceholderProvider::class
}