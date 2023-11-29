package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.provider.FormSwitchProvider

class FormSwitch : BaseForm {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    override fun getProvider(adapter: FormAdapter) = FormSwitchProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is Boolean) return
        content = value.toString().toBooleanStrictOrNull()
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return content?.toString() ?: FormUtils.getText(context, content)
    }
}