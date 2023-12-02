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
import org.json.JSONArray
import org.json.JSONObject

class FormDynamicPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    private var data = FormDynamicPartData()

    private val addButton = InternalFormDynamicAddButton(null, null)

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
                        item.isHasDeleteConfirm = data.isHasDeleteConfirm
                        item.dynamicGroupNameFormatBlock = data.dynamicGroupNameFormatter
                        if (data.dynamicPartMinGroupCount <= index) {
                            item.dynamicGroupDeletingBlock = {
                                data.getGroups().remove(it)
                                update()
                            }
                        } else {
                            item.dynamicGroupDeletingBlock = null
                        }
                        item.menu = data.menu
                        item.menuVisibilityMode = data.menuVisibilityMode
                        item.menuEnableMode = data.menuEnableMode
                        item.menuShowTitle = data.menuShowTitle
                        item.menuCreateOptionCallback = data.getMenuCreateOptionCallback()
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
                    group.add(addButton.apply {
                        name = addName
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
        if (data.partField != null) {
            val jsonArray = JSONArray()
            data.getGroups().forEach {
                val childJson = JSONObject()
                it.getItems().forEach { item -> item.executeOutput(formAdapter, childJson) }
                jsonArray.put(childJson)
            }
            json.put(data.partField!!, jsonArray)
        } else {
            data.getGroups().forEach {
                it.getItems().forEach { item ->
                    val tempJson = JSONObject()
                    item.executeOutput(formAdapter, tempJson)
                    tempJson.keys().forEach { key ->
                        val jsonArray = json.optJSONArray(key)
                        if (jsonArray != null) {
                            jsonArray.put(tempJson.get(key))
                        } else {
                            val newJsonArray = JSONArray()
                            newJsonArray.put(tempJson.get(key))
                            json.put(key, newJsonArray)
                        }
                    }
                }
            }
        }
    }
}