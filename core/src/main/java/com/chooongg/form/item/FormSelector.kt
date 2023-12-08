package com.chooongg.form.item

import android.content.Context
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormUtils
import com.chooongg.form.enum.FormSelectorOpenMode
import com.chooongg.form.option.IOption
import com.chooongg.form.option.Option
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormSelectorProvider
import org.json.JSONObject
import kotlin.reflect.KClass

class FormSelector(name: Any?, field: String?) : BaseOptionForm<IOption>(name, field) {

    /**
     * 打开模式
     */
    var openMode: FormSelectorOpenMode = FormSelectorOpenMode.AUTO

    override fun hasOpenOperation() = true

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormSelectorProvider::class

    override fun initValue(value: Any?) {
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