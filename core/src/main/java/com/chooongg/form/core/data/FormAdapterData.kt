package com.chooongg.form.core.data

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.part.BaseFormPartAdapter

class FormAdapterData(private val adapter: FormAdapter) : IFormAdapterCreator {

    private val _parts = ArrayList<BaseFormPartAdapter>()

    override fun getFormAdapter(): FormAdapter = adapter

    override fun getParts(): MutableList<BaseFormPartAdapter> = _parts

}