package com.chooongg.form.item

import com.chooongg.form.part.BaseFormPartAdapter

class LinkageForm internal constructor(private val adapter: BaseFormPartAdapter?) {

    fun findItem(
        field: String,
        isGlobal: Boolean = false,
        update: Boolean = true,
        hasPayload: Boolean = false,
        block: (BaseForm) -> Unit
    ) {
        if (isGlobal) {
            adapter?.formAdapter?.findOfField(field, update, hasPayload, block)
        } else {
            adapter?.findOfField(field, update, hasPayload, block)
        }
    }
}