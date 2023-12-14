package com.chooongg.form.part

import com.chooongg.form.FormAdapter
import com.chooongg.form.data.FormDynamicPartData
import com.chooongg.form.data.FormGroupData
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.InternalFormDynamicAddButton
import com.chooongg.form.item.VariantBaseForm
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NotAlignmentStyle
import org.json.JSONArray
import org.json.JSONObject

class FormDynamicPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    internal var data = FormDynamicPartData()

    private val addButton = InternalFormDynamicAddButton()

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
            while (data.getGroups().size < data.minGroupCount) {
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
                        if (data.minGroupCount <= index) {
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
                        item.menuCreateOptionCallback(data.getMenuCreateOptionCallback())
                    })
                    group.addAll(it.getItems())
                    add(group)
                }
                if (formAdapter.isEnabled && data.dynamicGroupCreateBlock != null && data.maxGroupCount > data.getGroups().size) {
                    val group = ArrayList<BaseForm>()
                    group.add(addButton.apply {
                        name = data.partName
                        dynamicGroupNameFormatter = data.dynamicGroupNameFormatter
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
        if (formAdapter.isEnabled && data.dynamicGroupCreateBlock != null && data.maxGroupCount > data.getGroups().size) {
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

    override fun findOfField(field: String): BaseForm? {
        data.getGroups().forEach { group ->
            group.getItems().forEach { item ->
                if (item.field == field) return item
                if (item is VariantBaseForm) {
                    item.getItems().forEach { if (it.field == field) return it }
                }
            }
        }
        return null
    }

    override fun findOfId(id: String): BaseForm? {
        data.getGroups().forEach { group ->
            group.getItems().forEach { item ->
                if (item.id == id) return item
                if (item is VariantBaseForm) {
                    item.getItems().forEach { if (it.id == id) return it }
                }
            }
        }
        return null
    }

    override fun findOfItem(target: BaseForm): BaseForm? {
        data.getGroups().forEach { group ->
            group.getItems().forEach { item ->
                if (item == target) return item
                if (item is VariantBaseForm) {
                    item.getItems().forEach { if (it == target) return it }
                }
            }
        }
        return null
    }

    @Throws(FormDataVerificationException::class)
    override fun executeDataVerification() {
        data.getGroups().forEach {
            it.getItems().forEach { item ->
                item.executeDataVerification(formAdapter)
            }
        }
    }

    override fun getDataVerificationError(): List<FormDataVerificationException> {
        val errors = ArrayList<FormDataVerificationException>()
        data.getGroups().forEach { group ->
            group.getItems().forEach {
                try {
                    it.executeDataVerification(formAdapter)
                } catch (e: FormDataVerificationException) {
                    errors.add(e)
                }
            }
        }
        return errors
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