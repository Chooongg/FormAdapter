package com.chooongg.form.core.data

import com.chooongg.form.core.item.BaseForm

interface IFormCreator {

    fun getItems(): MutableList<BaseForm>

    fun addItem(item: BaseForm): Boolean {
        item.initValue(item.content)
        return getItems().add(item)
    }
}