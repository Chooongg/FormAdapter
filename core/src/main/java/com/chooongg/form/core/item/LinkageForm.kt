package com.chooongg.form.core.item

import com.chooongg.form.core.part.BaseFormPartAdapter

class LinkageForm internal constructor(private val adapter: BaseFormPartAdapter?) {

    fun findItem(
        field: String, update: Boolean = true, isGlobal: Boolean = false, block: (BaseForm) -> Unit
    ) {
        if (isGlobal) {
            adapter?.formAdapter?.findOfField(field, update, block)
        } else {
            adapter?.findOfField(field, update, block)
        }
    }
}