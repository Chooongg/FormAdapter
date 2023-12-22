package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.FormGroupNameFormatter
import com.chooongg.form.provider.InternalFormDynamicAddButtonProvider

class InternalFormDynamicAddButton internal constructor() : FormButton(null, null) {

    var dynamicGroupNameFormatter: FormGroupNameFormatter? = null
        internal set

    var addBlock: (() -> Unit)? = null
        internal set

    override var loneLine: Boolean = true

    override fun getProvider(adapter: FormAdapter) = InternalFormDynamicAddButtonProvider::class
}