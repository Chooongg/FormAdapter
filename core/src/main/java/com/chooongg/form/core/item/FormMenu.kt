package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormMenuProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset

open class FormMenu : BaseForm {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    override var typeset: BaseTypeset? = NoneTypeset()

    override fun getProvider(adapter: FormAdapter) = FormMenuProvider::class

}