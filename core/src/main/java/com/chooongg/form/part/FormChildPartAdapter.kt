package com.chooongg.form.part

import com.chooongg.form.FormAdapter
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantBaseForm
import com.chooongg.form.style.BaseStyle
import org.json.JSONObject

class FormChildPartAdapter(formAdapter: FormAdapter, style: BaseStyle) :
    BaseFormPartAdapter(formAdapter, style) {

    override var needBlankFill: Boolean = false

    internal lateinit var data: VariantBaseForm

    fun set(data: VariantBaseForm) {
        this.data = data
        update()
    }

    override fun getOriginalItemList(): List<List<BaseForm>> {
        return if (this::data.isInitialized) {
            val group = ArrayList<BaseForm>()
            if (data.name != null || data.dynamicGroupDeletingBlock != null) {
                group.add(data.getGroupNameItem { item ->
                    item.name = data.name
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
    override fun executeDataVerification() = Unit

    override fun getDataVerificationError(): List<FormDataVerificationException> {
        return ArrayList()
    }

    override fun executeOutput(json: JSONObject) = Unit
}