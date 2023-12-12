package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.data.IFormGroupCreator
import com.chooongg.form.provider.BaseFormProvider
import kotlin.reflect.KClass

abstract class VariantForm(name: Any?, field: String?) : BaseForm(name, field), IFormGroupCreator {

    private var _items = mutableListOf<BaseForm>()

    override fun getItems() = _items

    abstract fun getColumn(count: Int, layoutColumn: Int): Int

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> {
        throw IllegalAccessException("Under normal circumstances, this output will not be called!")
    }
}