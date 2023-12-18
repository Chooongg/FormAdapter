package com.chooongg.form.part

import com.chooongg.form.FormAdapter
import com.chooongg.form.data.FormPartData
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantBaseForm
import com.chooongg.form.style.BaseStyle
import org.json.JSONObject

class FormPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    internal var data = FormPartData().apply { isEnablePart = false }

    fun create(data: FormPartData.() -> Unit) {
        create(FormPartData().apply(data))
    }

    fun create(data: FormPartData) {
        this.data = data
    }

    override fun getOriginalItemList(): List<List<BaseForm>> {
        return if (data.isEnablePart) {
            val group = ArrayList<BaseForm>()
            if (data.partName != null || data.dynamicGroupDeletingBlock != null) {
                group.add(data.getGroupNameItem { item ->
                    item.name = data.partName
                    item.menu = data.menu
                    item.menuEnableMode = data.menuEnableMode
                    item.menuVisibilityMode = data.menuVisibilityMode
                    item.isHasDeleteConfirm = data.isHasDeleteConfirm
                    item.dynamicGroupDeletingBlock = data.dynamicGroupDeletingBlock
                    item.setMenuCreateOptionCallback(data.getMenuCreateOptionCallback())
                })
            } else data.clearGroupNameItem()
            group.addAll(data.getItems())
            listOf(group)
        } else emptyList()
    }

    override fun findOfField(field: String): BaseForm? {
        data.getItems().forEach { item ->
            if (item.field == field) return item
            if (item is VariantBaseForm) {
                item.getItems().forEach { if (it.field == field) return item }
            }
        }
        return null
    }

    override fun findOfId(id: String): BaseForm? {
        data.getItems().forEach { item ->
            if (item.id == id) return item
            if (item is VariantBaseForm) {
                item.getItems().forEach { if (it.id == id) return item }
            }
        }
        return null
    }

    override fun findOfItem(target: BaseForm): BaseForm? {
        data.getItems().forEach { item ->
            if (item == target) return item
            if (item is VariantBaseForm) {
                item.getItems().forEach { if (it == target) return item }
            }
        }
        return null
    }

    @Throws(FormDataVerificationException::class)
    override fun executeDataVerification() {
        data.getItems().forEach { item -> item.executeDataVerification(formAdapter) }
    }

    override fun getDataVerificationError(): List<FormDataVerificationException> {
        val errors = ArrayList<FormDataVerificationException>()
        data.getItems().forEach {
            try {
                it.executeDataVerification(formAdapter)
            } catch (e: FormDataVerificationException) {
                errors.add(e)
            }
        }
        return errors
    }

    override fun executeOutput(json: JSONObject) {
        if (data.partField != null) {
            val childJson = JSONObject()
            data.getItems().forEach { item -> item.executeOutput(formAdapter, childJson) }
            json.put(data.partField!!, childJson)
        } else data.getItems().forEach { item -> item.executeOutput(formAdapter, json) }
    }
}