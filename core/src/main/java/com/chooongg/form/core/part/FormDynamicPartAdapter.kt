package com.chooongg.form.core.part

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.data.FormDynamicPartData
import com.chooongg.form.core.data.FormGroupData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName
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

    override fun update() {
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
                groupList.add(InternalFormPartName(data.partName).apply {
                    dynamicPartNameFormatBlock = data.dynamicPartNameFormatter
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
            notifyItemRangeChanged(0, itemCount)
        }
    }

    override fun findOfField(field: String, update: Boolean, block: (BaseForm) -> Unit): Boolean {
        itemList.forEach {
            if (it.field == field) {
                block(it)
                if (update) update()
                return true
            }
        }
        data.getGroups().forEach { group ->
            group.getItems().forEach {
                if (it.field == field) {
                    block(it)
                    if (update) update()
                    return true
                }
            }
        }
        return false
    }
}