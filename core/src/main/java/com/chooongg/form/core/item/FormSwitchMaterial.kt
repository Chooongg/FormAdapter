package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.provider.FormSwitchMaterialProvider

class FormSwitchMaterial : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    override fun getProvider(adapter: FormAdapter) = FormSwitchMaterialProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is Boolean) return
        content = value.toString().toBooleanStrictOrNull()
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return content?.toString() ?: FormUtils.getText(context,content)
    }
}