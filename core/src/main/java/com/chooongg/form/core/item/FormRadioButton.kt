package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.Option
import com.chooongg.form.core.provider.FormRadioButtonProvider

class FormRadioButton : BaseOptionForm<IOption> {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    override fun hasOpenOperation() = false

    override fun getProvider(adapter: FormAdapter) = FormRadioButtonProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is IOption) return
        content = Option(null, value.toString())
    }
}