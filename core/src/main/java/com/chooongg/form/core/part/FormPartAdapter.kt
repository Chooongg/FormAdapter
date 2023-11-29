package com.chooongg.form.core.part

import android.util.Log
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormDataVerificationException
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.VariantForm
import com.chooongg.form.core.style.BaseStyle
import org.json.JSONObject

class FormPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    private var data = FormPartData()

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
        return false
    }

    @Throws(FormDataVerificationException::class)
    override fun executeDataVerification() {
        data.getItems().forEach { item ->
            item.executeDataVerification(formAdapter)
        }
    }

    override fun executeOutput(json: JSONObject) {
        data.getItems().forEach { item ->
            Log.d("Form", "${item.javaClass.simpleName}   ${item.content}")
            item.executeOutput(formAdapter, json)
        }
    }
}