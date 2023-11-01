package com.chooongg.form.core.data

import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName

class FormPartData : BaseFormPartData(), IFormCreator {

    private val _items = mutableListOf<BaseForm>()

    override fun getItems(): MutableList<BaseForm> = _items

}