package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.enum.FormSelectorOpenMode
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.Option
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormSelectorProvider
import org.json.JSONObject
import kotlin.reflect.KClass

class FormSelector : BaseOptionForm<IOption> {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    /**
     * 打开模式
     */
    var openMode: FormSelectorOpenMode = FormSelectorOpenMode.AUTO

    override fun hasOpenOperation() = true

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormSelectorProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is IOption) return
        content = Option(null, value.toString())
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return if (content is IOption) (content as IOption).getSpannableString(context)
        else FormUtils.getText(context, content)
    }

    override fun outputData(json: JSONObject) {
        if (field != null && content != null) {
            if (content is IOption) {
                json.put(field!!, (content as IOption).getValue())
            } else json.put(field!!, content)
        }
    }
}