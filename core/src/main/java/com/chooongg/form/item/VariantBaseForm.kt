package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.VariantFormProvider
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.ExternalAlignedStyle
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.EmptyTypeset
import kotlin.reflect.KClass

abstract class VariantBaseForm(name: Any?, field: String?) : BaseForm(name, field) {

    internal var adapter: BaseFormPartAdapter? = null

    private var _groupNameItem: InternalFormGroupChildName? = null

    /**
     * 主题
     */
    open var style: BaseStyle = ExternalAlignedStyle()

    /**
     * 是否是独立的
     */
    var isIndependent: Boolean = false

    /**
     * 删除是否需要确认操作
     */
    var isHasDeleteConfirm: Boolean = false

    internal var _column = 1

    internal var dynamicGroupDeletingBlock: (() -> Unit)? = null

    override var typeset: BaseTypeset? = EmptyTypeset

    private var _items = mutableListOf<BaseForm>()

    fun getItems() = _items

    internal fun getGroupNameItem(block: (InternalFormGroupChildName) -> Unit): InternalFormGroupChildName {
        if (_groupNameItem == null) _groupNameItem = InternalFormGroupChildName()
        block.invoke(_groupNameItem!!)
        return _groupNameItem!!
    }

    internal fun clearGroupNameItem() {
        _groupNameItem = null
    }

    abstract fun getColumn(count: Int, layoutColumn: Int): Int

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        VariantFormProvider::class

    override fun resetInternalValues() {
        super.resetInternalValues()
        _column = 1
    }
}