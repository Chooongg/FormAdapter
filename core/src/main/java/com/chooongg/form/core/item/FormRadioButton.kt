package com.chooongg.form.core.item

import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.Option
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormRadioButtonProvider
import org.json.JSONObject
import kotlin.reflect.KClass

class FormRadioButton : BaseOptionForm<IOption> {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    override fun hasOpenOperation() = false

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormRadioButtonProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is IOption) return
        content = Option(null, value.toString())
    }

    override fun outputData(json: JSONObject) {
        if (field != null && content != null) {
            if (content is IOption) {
                json.put(field!!, (content as IOption).getValue())
            } else json.put(field!!, content)
        }
    }
}