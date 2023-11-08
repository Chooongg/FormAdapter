package com.chooongg.form.core.data

import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName

open class FormGroupData : IFormCreator {

    private var _partNameItem: InternalFormPartName? = null

    private val _items = mutableListOf<BaseForm>()

    override fun getItems(): MutableList<BaseForm> = _items

    fun getPartNameItem(block: (InternalFormPartName) -> Unit): InternalFormPartName {
        if (_partNameItem == null) _partNameItem = InternalFormPartName()
        block.invoke(_partNameItem!!)
        return _partNameItem!!
    }
}