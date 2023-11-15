package com.chooongg.form.core.part

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.data.FormDynamicPartData
import com.chooongg.form.core.data.FormGroupData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.VariantForm
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

    override fun getOriginalItemList(): List<List<BaseForm>> {
        data.getGroups().forEach { if (it.getItems().isEmpty()) data.getGroups().remove(it) }
        if (data.dynamicGroupCreateBlock != null) {
            while (data.getGroups().size < data.dynamicPartMinGroupCount) {
                val groupData = FormGroupData()
                data.dynamicGroupCreateBlock!!.invoke(groupData)
                data.getGroups().add(groupData)
            }
        }
        return if (data.isEnablePart) {
            ArrayList<List<BaseForm>>().apply {
                data.getGroups().forEach {
                    val group = ArrayList<BaseForm>()
                    if (data.partName != null || data.dynamicPartNameFormatter != null) {
                        group.add(it.getGroupNameItem { item ->
                            item.name = data.partName
                            item.dynamicPartNameFormatBlock = data.dynamicPartNameFormatter
                        })
                    } else it.clearGroupNameItem()
                    group.addAll(it.getItems())
                    add(group)
                }
            }
        } else emptyList()
    }

    fun executeUpdate(notifyBlock: () -> Unit) {
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
                groupList.add(group.getGroupNameItem {
                    it.name = data.partName
                    it.dynamicPartNameFormatBlock = data.dynamicPartNameFormatter
                })
            }
            var diffIndex = 0
            group.getItems().forEach {
                it.resetInternalValues()
                if (it is VariantForm) {
                    it.getItems().forEachIndexed { index, item ->
                        item.variantIndexInGroup = diffIndex
                        item.countInCurrentVariant = it.getItems().size
                        item.indexInCurrentVariant = index
                    }
                    diffIndex++
                } else if (it.isRealVisible(formAdapter.isEnabled)) groupList.add(it)
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
                item.groupCount = tempList.size
                item.groupIndex = index
                item.countInGroup = group.size
                item.positionInGroup = position
                if (item.loneLine && position > 0) {
                    group[position - 1].nextIsLoneLine = true
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
            group.getItems().forEach { item ->
                if (item.field == field) {
                    block(item)
                    if (update) notifyChangeItem(item, hasPayload)
                    return true
                }
                if (item is VariantForm) {
                    item.getItems().forEach {
                        if (it.field == field) {
                            block(it)
                            if (update) notifyChangeItem(it, hasPayload)
                            return true
                        }
                    }
                }
            }
        }
        return false
    }
}