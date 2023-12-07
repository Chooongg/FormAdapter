package com.chooongg.form.part

import com.chooongg.form.FormAdapter
import com.chooongg.form.data.FormPartData
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantForm
import com.chooongg.form.style.BaseStyle
import org.json.JSONObject

class FormPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    internal var data = FormPartData()

    fun create(data: FormPartData.() -> Unit) {
        create(FormPartData().apply(data))
    }

    fun create(data: FormPartData) {
        this.data = data
    }

    override fun getOriginalItemList(): List<List<BaseForm>> {
        return if (data.isEnablePart) {
            val group = ArrayList<BaseForm>()
            if (data.partName != null) {
                group.add(data.getGroupNameItem { item ->
                    item.name = data.partName
                    item.menu = data.menu
                    item.menuVisibilityMode = data.menuVisibilityMode
                    item.menuEnableMode = data.menuEnableMode
                    item.menuShowTitle = data.menuShowTitle
                    item.menuCreateOptionCallback(data.getMenuCreateOptionCallback())
                })
            } else data.clearGroupNameItem()
            group.addAll(data.getItems())
            listOf(group)
        } else emptyList()
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
                if (update) {
                    if (hasPayload) notifyChangeItem(item, true) else update()
                }
                return true
            }
            if (item is VariantForm) {
                item.getItems().forEach {
                    if (it.field == field) {
                        block(it)
                        if (update) {
                            if (hasPayload) notifyChangeItem(item, true) else update()
                        }
                        return true
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
        data.getItems().forEach { item ->
            if (item.id == id) {
                block(item)
                if (update) {
                    if (hasPayload) notifyChangeItem(item, true) else update()
                }
                return true
            }
            if (item is VariantForm) {
                item.getItems().forEach {
                    if (it.id == id) {
                        block(it)
                        if (update) {
                            if (hasPayload) notifyChangeItem(item, true) else update()
                        }
                        return true
                    }
                }
            }
        }
        return false
    }

    @Throws(FormDataVerificationException::class)
    override fun executeDataVerification() {
        data.getItems().forEach { item -> item.executeDataVerification(formAdapter) }
    }

    override fun executeOutput(json: JSONObject) {
        if (data.partField != null) {
            val childJson = JSONObject()
            data.getItems().forEach { item -> item.executeOutput(formAdapter, childJson) }
            json.put(data.partField!!, childJson)
        } else data.getItems().forEach { item -> item.executeOutput(formAdapter, json) }
    }
}