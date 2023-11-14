package com.chooongg.form.core.part

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.ChildrenForm
import com.chooongg.form.core.item.MultiColumnForm
import com.chooongg.form.core.item.SingleLineForm
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

    override fun executeUpdate(notifyBlock: () -> Unit) {
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
            tempList.add(data.getGroupNameItem {
                it.name = data.partName
            })
        }
        var diffIndex = 0
        data.getItems().forEach {
            it.resetInternalValues()
            if (it is ChildrenForm) {
                it.getItems().forEachIndexed { index, item ->
                    item.variantIndexInGroup = diffIndex
                    item.countInCurrentVariant = it.getItems().size
                    item.indexInCurrentVariant = index
                    if (it is SingleLineForm) {
                        item.variantColumnCount
                    } else if (it is MultiColumnForm) {

                    }
                }
                diffIndex++
            } else if (it.isRealVisible(formAdapter.isEnabled)) tempList.add(it)
        }
        while (tempList.size > 0 && !tempList[0].showAtEdge) {
            tempList.removeAt(0)
        }
        while (tempList.size > 1 && !tempList[tempList.lastIndex].showAtEdge) {
            tempList.removeAt(tempList.lastIndex)
        }
        tempList.forEachIndexed { index, item ->
            item.groupCount = 1
            item.groupIndex = 0
            item.countInGroup = tempList.size
            item.positionInGroup = index
            if (index > 0 && (item.loneLine)) {
                tempList[index - 1].nextItemLoneLine = true
            }
        }
        asyncDiffer.submitList(tempList) { notifyBlock.invoke() }
    }

    override fun findOfField(
        field: String,
        update: Boolean,
        hasPayload: Boolean,
        block: (BaseForm) -> Unit
    ): Boolean {
        data.getItems().forEach { item ->
            if (item.field == field) {
                block(item)
                if (update) notifyChangeItem(item, hasPayload)
                return true
            }
            if (item is ChildrenForm) {
                item.getItems().forEach {
                    if (it.field == field) {
                        block(it)
                        if (update) notifyChangeItem(it, hasPayload)
                        return true
                    }
                }
            }
        }
        return false
    }
}