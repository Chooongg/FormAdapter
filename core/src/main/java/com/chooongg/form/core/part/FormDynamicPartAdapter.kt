package com.chooongg.form.core.part

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.data.FormDynamicPartData
import com.chooongg.form.core.data.FormGroupData
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

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun executeUpdate(notifyBlock: () -> Unit) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        if (!data.isEnablePart) {
            asyncDiffer.submitList(null)
            return
        }
        val tempList = mutableListOf<MutableList<BaseForm>>()
        data.getGroups().forEach { if (it.getItems().isEmpty()) data.getGroups().remove(it) }
        if (data.dynamicGroupCreateBlock != null) {
            while (data.getGroups().size < data.dynamicPartMinGroupCount) {
                val groupData = FormGroupData()
                data.dynamicGroupCreateBlock!!.invoke(groupData)
                data.getGroups().add(groupData)
            }
        }
        data.getGroups().forEach { group ->
            val groupList = mutableListOf<BaseForm>()
            if (data.partName != null) {
                groupList.add(group.getPartNameItem {
                    it.name = data.partName
                    it.dynamicPartNameFormatBlock = data.dynamicPartNameFormatter
                })
            }
            group.getItems().forEach {
                it.globalPosition = -1
                it.groupItemCount = -1
                it.groupIndex = -1
                it.itemCountInGroup = -1
                it.positionInGroup = -1
                it.nextItemLoneLine = false
                if (it.isRealVisible(formAdapter.isEnabled)) groupList.add(it)
            }
            while (groupList.size > 0 && !groupList[0].showAtEdge) {
                groupList.removeAt(0)
            }
            while (groupList.size > 1 && !groupList[groupList.lastIndex].showAtEdge) {
                groupList.removeAt(groupList.lastIndex)
            }
            tempList.add(groupList)
        }
        tempList.forEachIndexed { index, group ->
            group.forEachIndexed { position, item ->
                item.groupItemCount = tempList.size
                item.groupIndex = index
                item.itemCountInGroup = group.size
                item.positionInGroup = position
                if (item.loneLine && position > 0) {
                    group[position - 1].nextItemLoneLine = true
                }
            }
        }
        asyncDiffer.submitList(ArrayList<BaseForm>().apply { tempList.forEach { addAll(it) } }) {
            notifyBlock.invoke()
        }
    }

    override fun findOfField(
        field: String,
        update: Boolean,
        hasPayload: Boolean,
        block: (BaseForm) -> Unit
    ): Boolean {
        data.getGroups().forEach { group ->
            group.getItems().forEachIndexed { index, item ->
                if (item.field == field) {
                    block(item)
                    if (update && itemList.contains(item)) {
                        val tempEmpty = itemList.isEmpty()
                        if (hasPayload) {
                            notifyItemChanged(index, FormAdapter.UPDATE_PAYLOAD_FLAG)
                        } else {
                            update()
                            if (tempEmpty != itemList.isEmpty()) {
                                val partIndex = formAdapter.partAdapters.indexOf(this)
                                if (partIndex > 0) {
                                    formAdapter.partAdapters[partIndex - 1].update()
                                }
                                if (partIndex < formAdapter.partAdapters.size - 1) {
                                    formAdapter.partAdapters[partIndex + 1].update()
                                }
                            }
                        }
                    }
                    return true
                }
            }
        }
        return false
    }
}