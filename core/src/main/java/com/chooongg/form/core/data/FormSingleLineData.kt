package com.chooongg.form.core.data

import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.core.item.BaseForm

class FormSingleLineData : IFormCreator {

    private val _items = mutableListOf<BaseForm>()

    var field: String? = null

    /**
     * 可见模式
     */
    var visibilityMode: FormVisibilityMode? = null

    override fun getItems() = _items
}