package com.chooongg.form.core.part

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class FormPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    private var data = FormPartData()

    fun create(data: FormPartData.() -> Unit) {
        create(FormPartData().apply(data))
    }

    fun create(data: FormPartData) {
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
        if (data.partName != null) {
            tempItems.add(InternalFormPartName(data.partName))
        }
        data.getItems().forEach {
            it.globalPosition = -1
            it.groupItemCount = -1
            it.groupIndex = -1
            it.itemCountInGroup = -1
            it.positionInGroup = -1
            if (it.isRealVisible(formAdapter.isEnabled)) tempItems.add(it)
        }
        tempItems.forEachIndexed { index, item ->
            item.groupItemCount = 1
            item.groupIndex = 0
            item.itemCountInGroup = tempItems.size
            item.positionInGroup = index
        }
        asyncDiffer.submitList(tempItems) { lastEnabled = formAdapter.isEnabled }
    }

    override fun findOfField(field: String, update: Boolean, block: (BaseForm) -> Unit): Boolean {
        itemList.forEach {
            if (it.field == field) {
                block(it)
                if (update) update()
                return true
            }
        }
        data.getItems().forEach {
            if (it.field == field) {
                block(it)
                if (update) update()
                return true
            }
        }
        return false
    }
}