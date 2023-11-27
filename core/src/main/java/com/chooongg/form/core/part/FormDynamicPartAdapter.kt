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
import org.json.JSONObject

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
                    if (data.partName != null || data.dynamicGroupNameFormatter != null) {
                        group.add(it.getGroupNameItem { item ->
                            item.name = data.partName
                            item.dynamicGroupNameFormatBlock = data.dynamicGroupNameFormatter
                            item.dynamicGroupCreateBlock = data.dynamicGroupCreateBlock
                        })
                    } else it.clearGroupNameItem()
                    group.addAll(it.getItems())
                    add(group)
                }
            }
        } else emptyList()
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

    override fun executeDataVerification() {

        TODO("Not yet implemented")
    }

    override fun executeOutput(json: JSONObject) {
        TODO("Not yet implemented")
    }
}