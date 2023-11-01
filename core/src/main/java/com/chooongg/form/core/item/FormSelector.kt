package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.enum.FormSelectorOpenMode
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.provider.FormSelectorProvider

class FormSelector : BaseOptionForm<IOption> {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    /**
     * 打开模式
     */
    var openMode: FormSelectorOpenMode = FormSelectorOpenMode.AUTO

    override fun hasOpenOperation() = true

    override fun getProvider(adapter: FormAdapter) = FormSelectorProvider::class

}