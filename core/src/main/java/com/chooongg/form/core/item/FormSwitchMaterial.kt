package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormSwitchMaterialProvider

class FormSwitchMaterial : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    override fun getProvider(adapter: FormAdapter) = FormSwitchMaterialProvider::class

}