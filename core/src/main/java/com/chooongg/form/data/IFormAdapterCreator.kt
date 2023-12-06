package com.chooongg.form.data

import com.chooongg.form.FormAdapter
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormDynamicPartAdapter
import com.chooongg.form.part.FormPartAdapter
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NoneStyle

interface IFormAdapterCreator {

    fun getFormAdapter(): FormAdapter

    fun getParts(): MutableList<BaseFormPartAdapter>

    /**
     * 初始化片段
     */
    fun initPart(part: BaseFormPartAdapter): Boolean {
        return getParts().add(part)
    }

    /**
     * 初始化片段
     */
    fun initPart(
        style: BaseStyle = NoneStyle(),
        block: FormPartData.() -> Unit
    ): FormPartAdapter {
        val part = FormPartAdapter(getFormAdapter(), style)
        part.create(block)
        initPart(part)
        return part
    }

    /**
     * 初始化动态片段
     */
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