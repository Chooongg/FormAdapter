package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.data.IFormGroupCreator
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.VariantFormProvider
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NoneStyle
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.EmptyTypeset
import kotlin.reflect.KClass

abstract class VariantBaseForm(name: Any?, field: String?) : BaseForm(name, field), IFormGroupCreator {

    /**
     * 是否是独立的
     */
    var isIndependent: Boolean = false

    var style: BaseStyle = NoneStyle()

    internal var _column = 1

    override var typeset: BaseTypeset? = EmptyTypeset()

    private var _items = mutableListOf<BaseForm>()

    override fun getItems() = _items

    abstract fun getColumn(count: Int, layoutColumn: Int): Int

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        VariantFormProvider::class

    override fun resetInternalValues() {
        super.resetInternalValues()
        _column = 1
    }
}