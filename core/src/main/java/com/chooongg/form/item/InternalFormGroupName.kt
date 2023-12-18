package com.chooongg.form.item

import android.content.Context
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormGroupNameFormatter
import com.chooongg.form.FormUtils
import com.chooongg.form.provider.InternalFormGroupNameProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.NoneTypeset

class InternalFormGroupName internal constructor() : BaseForm(null, null) {

    override var loneLine = true

    override var typeset: BaseTypeset? = NoneTypeset()

    var isHasDeleteConfirm: Boolean = true

    internal var dynamicGroupDeletingBlock: (() -> Unit)? = null

    internal var dynamicGroupNameFormatBlock: FormGroupNameFormatter? = null

    override fun getProvider(adapter: FormAdapter) = InternalFormGroupNameProvider::class

    fun getPartName(context: Context): CharSequence? {
        val tempName = FormUtils.getText(context, name)
        return if (dynamicGroupNameFormatBlock != null) {
            dynamicGroupNameFormatBlock!!.invoke(context, tempName, groupIndex, groupCount)
        } else tempName
    }

    fun isShowDynamicDelete() = dynamicGroupDeletingBlock != null

    fun deleteDynamicGroup() {
        dynamicGroupDeletingBlock?.invoke()
    }
}