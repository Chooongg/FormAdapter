package com.chooongg.form.item

import android.content.Context
import com.chooongg.form.FormGroupNameFormatter
import com.chooongg.form.FormUtils

class VariantChildGroup(name: Any?, field: String?) : VariantBaseForm(name, field) {

    internal var isHasDeleteConfirm: Boolean = true

    internal var dynamicGroupDeletingBlock: (() -> Unit)? = null

    internal var dynamicGroupNameFormatBlock: FormGroupNameFormatter? = null

    override var autoFill: Boolean = false

    override fun getColumn(count: Int, layoutColumn: Int) = 1

    fun getPartName(context: Context): CharSequence? {
        val tempName = FormUtils.getText(context, name)
        return if (dynamicGroupNameFormatBlock != null) {
            dynamicGroupNameFormatBlock!!.invoke(context, tempName, groupIndex, groupCount)
        } else tempName
    }
}