package com.chooongg.form.core.data

import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormGroupName

open class FormGroupData : IFormCreator {

    private var _groupNameItem: InternalFormGroupName? = null

    private val _items = mutableListOf<BaseForm>()

    override fun getItems(): MutableList<BaseForm> = _items

    fun getGroupNameItem(block: (InternalFormGroupName) -> Unit): InternalFormGroupName {
        if (_groupNameItem == null) _groupNameItem = InternalFormGroupName()
        block.invoke(_groupNameItem!!)
        return _groupNameItem!!
    }
}