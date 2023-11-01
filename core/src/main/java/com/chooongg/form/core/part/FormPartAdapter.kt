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
import kotlinx.coroutines.launch

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
        adapterScope.launch {
        }
        val tempList = mutableListOf<BaseForm>()
        if (data.partName != null) {
            tempList.add(InternalFormPartName(data.partName))
        }
        data.getItems().forEach {
            it.globalPosition = -1
            it.groupItemCount = -1
            it.groupIndex = -1
            it.itemCountInGroup = -1
            it.positionInGroup = -1
            it.nextItemLoneLine = false
            if (it.isRealVisible(formAdapter.isEnabled)) tempList.add(it)
        }
        while (tempList.size > 0 && !tempList[0].showAtEdge) {
            tempList.removeAt(0)
        }
        while (tempList.size > 1 && !tempList[tempList.lastIndex].showAtEdge) {
            tempList.removeAt(tempList.lastIndex)
        }
        tempList.forEachIndexed { index, item ->
            item.groupItemCount = 1
            item.groupIndex = 0
            item.itemCountInGroup = tempList.size
            item.positionInGroup = index
            if (item.loneLine && index > 0) {
                tempList[index - 1].nextItemLoneLine = true
            }
        }
        asyncDiffer.submitList(tempList) { notifyItemRangeChanged(0, itemCount) }
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