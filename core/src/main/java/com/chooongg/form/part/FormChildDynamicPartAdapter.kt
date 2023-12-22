package com.chooongg.form.part

import com.chooongg.form.FormAdapter
import com.chooongg.form.FormUtils
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.InternalFormDynamicAddButton
import com.chooongg.form.item.VariantBaseForm
import com.chooongg.form.item.VariantChildDynamicGroup
import com.chooongg.form.item.VariantChildGroup
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.InternalDynamicAddButtonStyle
import org.json.JSONArray
import org.json.JSONObject

class FormChildDynamicPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    override var needBlankFill: Boolean = false

    internal var data = VariantChildDynamicGroup(null, null)

    fun set(data: VariantChildDynamicGroup) {
        this.data = data
        update()
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item is InternalFormDynamicAddButton) {
            return formAdapter.getItemViewType4Pool(InternalDynamicAddButtonStyle(), item)
        }
        return formAdapter.getItemViewType4Pool(style, item)
    }

    override fun getOriginalItemList(): List<List<BaseForm>> {
        data.getGroups().forEach { if (it.getItems().isEmpty()) data.getGroups().remove(it) }
        if (data.dynamicGroupCreateBlock != null) {
            while (data.getGroups().size < data.minGroupCount) {
                val groupData = VariantChildGroup(null, null)
                data.dynamicGroupCreateBlock!!.invoke(groupData)
                data.getGroups().add(groupData)
            }
        }
        val context = formAdapter.recyclerView?.context
        val groups = ArrayList<List<BaseForm>>()
        val items = ArrayList<BaseForm>().apply {
            data.getGroups().forEachIndexed { index, it ->
                it.parentItem = data
                it.isIndependent = data.isIndependent
                it._column = it.getColumn(0, columnCount ?: formAdapter.columnCount)
                it.name = if (context != null) {
                    data.dynamicGroupNameFormatter.invoke(
                        context,
                        FormUtils.getText(context, data.name),
                        index,
                        data.getGroups().size
                    )
                } else data.name
                it.isHasDeleteConfirm = data.isHasDeleteConfirm
                if (data.minGroupCount < data.getGroups().size) {
                    it.dynamicGroupDeletingBlock = {
                        data.getGroups().remove(it)
                        var tempAdapter: BaseFormPartAdapter = this@FormChildDynamicPartAdapter
                        var isTopLevel = false
                        while (!isTopLevel) {
                            if (tempAdapter.parentAdapter != null) {
                                tempAdapter = tempAdapter.parentAdapter!!
                            } else {
                                isTopLevel = true
                            }
                        }
                        tempAdapter.update()
                    }
                } else {
                    it.dynamicGroupDeletingBlock = null
                }
                add(it)
            }
        }
        groups.add(items)
        if (formAdapter.isEnabled && data.dynamicGroupCreateBlock != null && data.maxGroupCount > data.getGroups().size) {
            val addItems = ArrayList<BaseForm>()
            addItems.add(InternalFormDynamicAddButton().apply {
                name = if (context != null) {
                    data.dynamicGroupNameFormatter.invoke(
                        context,
                        FormUtils.getText(context, data.name),
                        data.getGroups().size,
                        data.getGroups().size + 1
                    )
                } else data.name
                buttonStyle = data.addButtonStyle
                iconGravity = data.addIconGravity
                icon = data.addIcon
                addBlock = {
                    if (data.dynamicGroupCreateBlock != null) {
                        val tempAdd = VariantChildGroup(null, null)
                        data.dynamicGroupCreateBlock!!.invoke(tempAdd)
                        data.getGroups().add(tempAdd)
                        executeUpdate()
                    }
                }
            })
            groups.add(addItems)
        }
        return groups
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
        if (data.field != null) {
            val jsonArray = JSONArray()
            data.getGroups().forEach {
                val childJson = JSONObject()
                it.getItems().forEach { item -> item.executeOutput(formAdapter, childJson) }
                jsonArray.put(childJson)
            }
            json.put(data.field!!, jsonArray)
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