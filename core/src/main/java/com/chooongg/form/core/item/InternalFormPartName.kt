package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormPartNameFormatter
import com.chooongg.form.core.provider.InternalFormPartNameProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset

class InternalFormPartName internal constructor(
    name: Any?
) : BaseForm(name) {

    override var loneLine = true

    override var typeset: BaseTypeset? = NoneTypeset()

    internal var dynamicPartNameFormatBlock: FormPartNameFormatter? = null

    override fun getProvider(adapter: FormAdapter) = InternalFormPartNameProvider::class

}