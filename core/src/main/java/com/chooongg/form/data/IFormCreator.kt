package com.chooongg.form.data

import com.chooongg.form.item.BaseForm

interface IFormCreator {

    fun getItems(): MutableList<BaseForm>

    fun addItem(item: BaseForm): Boolean {
        item.initValue(item.content)
        return getItems().add(item)
    }
}