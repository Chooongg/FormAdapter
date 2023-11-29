package com.chooongg.form.core.part

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormDataVerificationException
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.data.FormDynamicPartData
import com.chooongg.form.core.data.FormGroupData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormDynamicAddButton
import com.chooongg.form.core.item.VariantForm
import com.chooongg.form.core.style.BaseStyle
import com.chooongg.form.core.style.NotAlignmentStyle
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
        val item = getItem(position)
        if (item is InternalFormDynamicAddButton) {
            return formAdapter.getItemViewType4Pool(NotAlignmentStyle(), item)
        }
        return formAdapter.getItemViewType4Pool(style, item)
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
                data.getGroups().forEachIndexed { index, it ->
                    val group = ArrayList<BaseForm>()
                    group.add(it.getGroupNameItem { item ->
                        item.name = data.partName
                        item.dynamicGroupNameFormatBlock = data.dynamicGroupNameFormatter
                        if (data.dynamicPartMinGroupCount <= index) {
                            item.dynamicGroupDeletingBlock = {
                                data.getGroups().remove(it)
                                update()
                            }
                        } else {
                            item.dynamicGroupDeletingBlock = null
                        }
                    })
                    group.addAll(it.getItems())
                    add(group)
                }
                if (formAdapter.isEnabled && data.dynamicGroupCreateBlock != null && data.dynamicPartMaxGroupCount > data.getGroups().size) {
                    val group = ArrayList<BaseForm>()
                    val addName = if (context != null) {
                        data.dynamicGroupNameFormatter?.invoke(
                            context!!,
                            FormUtils.getText(context!!, data.partName),
                            data.getGroups().size,
                            data.getGroups().size + 1
                        ) ?: FormUtils.getText(context!!, data.partName)
                    } else null
                    group.add(InternalFormDynamicAddButton(addName, null).apply {
                        buttonStyle = data.addButtonStyle
                        iconGravity = data.addIconGravity
                        icon = data.addIcon
                    })
                    add(group)
                }
            }
        } else emptyList()
    }

    override fun getExtraGroupCount() =
        if (formAdapter.isEnabled && data.dynamicGroupCreateBlock != null && data.dynamicPartMaxGroupCount > data.getGroups().size) {
            1
        } else 0

    fun onItemAddClick(item: BaseForm) {
        if (data.dynamicGroupCreateBlock != null) {
            val group = FormGroupData()
            data.dynamicGroupCreateBlock!!.invoke(group)
            data.getGroups().add(group)
            update()
//            executeUpdate(false)
        } else {
            val indexOf = itemList.indexOf(item)
            if (indexOf >= 0) {
                item.errorNotify = System.currentTimeMillis()
                notifyItemChanged(indexOf, FormAdapter.ERROR_NOTIFY_FLAG)
            }
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

    override fun findOfId(
        id: String,
        update: Boolean,
        hasPayload: Boolean,
        block: BaseForm.() -> Unit
    ): Boolean {
        data.getGroups().forEach { group ->
            group.getItems().forEach { item ->
                if (item.id == id) {
                    block(item)
                    if (update) notifyChangeItem(item, hasPayload)
                    return true
                }
                if (item is VariantForm) {
                    item.getItems().forEach {
                        if (it.id == id) {
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

    @Throws(FormDataVerificationException::class)
    override fun executeDataVerification() {
        data.getGroups().forEach {
            it.getItems().forEach { item ->
                item.executeDataVerification(formAdapter)
            }
        }
    }

    override fun executeOutput(json: JSONObject) {
        data.getGroups().forEach {
            it.getItems().forEach { item ->
                item.executeOutput(formAdapter, json)
            }
        }
    }
}