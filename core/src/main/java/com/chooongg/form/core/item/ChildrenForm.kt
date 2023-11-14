package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.BaseFormProvider
import kotlin.reflect.KClass

open class ChildrenForm: BaseForm(null) {

    private var _items = mutableListOf<BaseForm>()

    internal fun setItems(list: MutableList<BaseForm>) {
        _items = list
    }

    fun getItems() = _items

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> {
        throw IllegalAccessException("Under normal circumstances, this output will not be called!")
    }
}