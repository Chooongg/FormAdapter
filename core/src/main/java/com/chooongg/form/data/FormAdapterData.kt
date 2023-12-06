package com.chooongg.form.data

import com.chooongg.form.FormAdapter
import com.chooongg.form.part.BaseFormPartAdapter

class FormAdapterData(private val adapter: FormAdapter) : IFormAdapterCreator {

    private val _parts = ArrayList<BaseFormPartAdapter>()

    override fun getFormAdapter(): FormAdapter = adapter

    override fun getParts(): MutableList<BaseFormPartAdapter> = _parts

}