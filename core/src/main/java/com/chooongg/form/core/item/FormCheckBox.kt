package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.Option
import com.chooongg.form.core.provider.FormCheckBoxProvider

class FormCheckBox : BaseOptionForm<IOption> {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    override fun hasOpenOperation() = false

    override fun getProvider(adapter: FormAdapter) = FormCheckBoxProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) return
        content = when (value) {
            is List<*> -> {
                if (value.isNotEmpty() && value[0] is IOption) return
                val temp = ArrayList<IOption>()
                value.forEach { temp.add(Option(null, it?.toString())) }
                value
            }

            is IOption -> listOf(value)
            is String -> listOf(Option(null, value))
            else -> null
        }
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return super.getContentText(context, enabled)
    }
}