package com.chooongg.form.core.data

import com.chooongg.form.core.item.BaseForm

open class FormGroupData : IFormCreator {

    private val _items = mutableListOf<BaseForm>()

    override fun getItems(): MutableList<BaseForm> = _items

}