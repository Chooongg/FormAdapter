package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.data.IFormCreator
import com.chooongg.form.core.provider.BaseFormProvider
import kotlin.reflect.KClass

abstract class VariantForm : BaseForm(null), IFormCreator {

    private var _items = mutableListOf<BaseForm>()

    override fun getItems() = _items

    abstract fun getColumn(count: Int, layoutColumn: Int): Int

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> {
        throw IllegalAccessException("Under normal circumstances, this output will not be called!")
    }
}