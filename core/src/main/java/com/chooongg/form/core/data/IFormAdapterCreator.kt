package com.chooongg.form.core.data

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.part.BaseFormPartAdapter
import com.chooongg.form.core.part.FormDynamicPartAdapter
import com.chooongg.form.core.part.FormPartAdapter
import com.chooongg.form.core.style.BaseStyle
import com.chooongg.form.core.style.NoneStyle

interface IFormAdapterCreator {

    fun getFormAdapter(): FormAdapter

    fun getParts(): MutableList<BaseFormPartAdapter>

    fun initPart(part: BaseFormPartAdapter): Boolean {
        return getParts().add(part)
    }

    fun initPart(
        style: BaseStyle = NoneStyle(),
        block: FormPartData.() -> Unit
    ): FormPartAdapter {
        val part = FormPartAdapter(getFormAdapter(), style)
        part.create(block)
        initPart(part)
        return part
    }

    fun initDynamicPart(
        style: BaseStyle = NoneStyle(),
        block: FormDynamicPartData.() -> Unit
    ): FormDynamicPartAdapter {
        val part = FormDynamicPartAdapter(getFormAdapter(), style)
        part.create(block)
        initPart(part)
        return part
    }
}