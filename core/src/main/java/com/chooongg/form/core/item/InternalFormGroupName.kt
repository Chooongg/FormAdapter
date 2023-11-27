package com.chooongg.form.core.item

import android.content.Context
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormGroupCreateBlock
import com.chooongg.form.core.FormGroupNameFormatter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.data.FormGroupData
import com.chooongg.form.core.provider.InternalFormPartNameProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset

class InternalFormGroupName internal constructor() : BaseForm(null) {

    override var loneLine = true

    override var typeset: BaseTypeset? = NoneTypeset()

    internal var dynamicGroupNameFormatBlock: FormGroupNameFormatter? = null

    internal var dynamicGroupCreateBlock: FormGroupCreateBlock? = null

    override fun getProvider(adapter: FormAdapter) = InternalFormPartNameProvider::class

    fun getPartName(context: Context): CharSequence? {
        val tempName = FormUtils.getText(context, name)
        return if (dynamicGroupNameFormatBlock != null) {
            dynamicGroupNameFormatBlock!!.invoke(context, tempName, groupIndex, groupCount)
        } else tempName
    }

    fun isHasDynamicCreateBlock() = dynamicGroupCreateBlock != null

    fun createDynamicGroup(){
        if (dynamicGroupCreateBlock != null) {
            val groupData = FormGroupData()
            dynamicGroupCreateBlock!!.invoke(groupData)
            groupData
        }
    }
}