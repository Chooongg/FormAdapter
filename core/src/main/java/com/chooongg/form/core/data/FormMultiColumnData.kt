package com.chooongg.form.core.data

import com.chooongg.form.core.enum.FormEnableMode
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.core.item.BaseForm

class FormMultiColumnData(val field: String?) : IFormCreator {

    private val _items = mutableListOf<BaseForm>()

    /**
     * 可见模式
     */
    var visibilityMode: FormVisibilityMode? = null

    override fun getItems() = _items
}