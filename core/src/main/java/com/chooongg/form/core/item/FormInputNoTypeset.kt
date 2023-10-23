package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormInputNoTypesetProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset

class FormInputNoTypeset : FormInput {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    override var typeset: BaseTypeset? = NoneTypeset()

    override fun getProvider(adapter: FormAdapter) = FormInputNoTypesetProvider::class

}