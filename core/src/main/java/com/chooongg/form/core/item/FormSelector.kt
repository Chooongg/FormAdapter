package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.enum.FormSelectorOpenMode
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.Option
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

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is IOption) return
        content = Option(null, value.toString())
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return if (content is IOption) (content as IOption).getSpannableString(context)
        else FormUtils.getText(context, content)
    }
}