package com.chooongg.form.core.part

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.data.FormDynamicPartData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class FormDynamicPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    private var data = FormDynamicPartData()

    fun create(data: FormDynamicPartData.() -> Unit) {
        create(FormDynamicPartData().apply(data))
    }

    fun create(data: FormDynamicPartData) {
        this.data = data
    }

    override fun update() {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        if (!data.isEnablePart) {
            asyncDiffer.submitList(null)
            return
        }
        val tempItems = mutableListOf<BaseForm>()
        data.getGroups().forEach { group ->
            group.getItems().forEach {
                tempItems.add(it)
            }
        }
        asyncDiffer.submitList(tempItems) { lastEnabled = formAdapter.isEnabled }
    }
}