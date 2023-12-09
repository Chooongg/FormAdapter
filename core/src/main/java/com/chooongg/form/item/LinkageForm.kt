package com.chooongg.form.item

import com.chooongg.form.part.BaseFormPartAdapter

class LinkageForm internal constructor(private val adapter: BaseFormPartAdapter) {

    fun findItem(field: String, isGlobal: Boolean = false): BaseForm? {
        return if (isGlobal) {
            adapter.formAdapter.findItemOfField(field)
        } else adapter.findOfField(field)
    }

    fun updateItem(
        field: String,
        isGlobal: Boolean = false,
        hasPayload: Boolean = false,
        block: (BaseForm) -> Unit
    ) {
        if (isGlobal) {
            adapter.formAdapter.updateItemOfField(field, hasPayload, block)
        } else {
            val item = adapter.findOfField(field)
            if (item != null) {
                block.invoke(item)
                if (hasPayload) adapter.notifyChangeItem(item, true) else adapter.update()
            }
        }
    }
}