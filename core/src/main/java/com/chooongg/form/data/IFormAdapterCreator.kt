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
        partName: Any? = null,
        partField: String? = null,
        style: BaseStyle = NoneStyle(),
        block: FormPartData.() -> Unit
    ): FormPartAdapter {
        val part = FormPartAdapter(getFormAdapter(), style)
        val data = FormPartData()
        data.partName = partName
        data.partField = partField
        block.invoke(data)
        part.create(data)
        initPart(part)
        return part
    }

    /**
     * 初始化动态片段
     */
    fun initDynamicPart(
        partName: Any? = null,
        partField: String? = null,
        style: BaseStyle = NoneStyle(),
        block: FormDynamicPartData.() -> Unit
    ): FormDynamicPartAdapter {
        val part = FormDynamicPartAdapter(getFormAdapter(), style)
        val data = FormDynamicPartData()
        data.partName = partName
        data.partField = partField
        block.invoke(data)
        part.create(data)
        initPart(part)
        return part
    }
}