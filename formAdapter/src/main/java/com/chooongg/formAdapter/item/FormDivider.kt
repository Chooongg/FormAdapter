package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormDividerProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

fun FormCreator.addDivider(
    field: String? = null, block: (FormDivider.() -> Unit)? = null
) = add(FormDivider(field).apply { block?.invoke(this) })

class FormDivider(field: String?) : BaseForm(null, null, field) {

    /**
     * 匹配父布局宽度
     */
    var matchParentWidth: Boolean = false

    override var isMustSingleColumn: Boolean = true

    override var isShowOnEdge: Boolean = false

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(adapter: FormAdapter) = FormDividerProvider
}