package com.chooongg.form.part

import com.chooongg.form.FormAdapter
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.InternalFormPlaceholder
import com.chooongg.form.style.EmptyStyle
import org.json.JSONObject

class PlaceholderPartAdapter(formAdapter: FormAdapter, val isTop: Boolean) :
    BaseFormPartAdapter(formAdapter, EmptyStyle()) {
    override fun getOriginalItemList() = listOf(listOf(InternalFormPlaceholder()))
    override fun findOfField(field: String): BaseForm? = null
    override fun findOfId(id: String): BaseForm? = null
    override fun findOfItem(target: BaseForm): BaseForm? = null
    override fun executeDataVerification() = Unit
    override fun getDataVerificationError() = emptyList<FormDataVerificationException>()
    override fun executeOutput(json: JSONObject) = Unit
}