package com.chooongg.form.item

import android.content.Context
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormUtils
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormSwitchProvider
import org.json.JSONObject
import kotlin.reflect.KClass

class FormSwitch(name: Any?, field: String?) : BaseForm(name, field) {

    /**
     * 值为空时
     */
    var valueWhenEmpty: Boolean = false

    /**
     * 自定义输出(真)
     */
    var customTrueValue: Any? = null

    /**
     * 自定义输出(假)
     */
    var customFalseValue: Any? = null

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormSwitchProvider::class

    override fun initValue(value: Any?) {
        if (value == null) {
            content = valueWhenEmpty
            return
        }
        if (value is Boolean) return
        content = if (customTrueValue != null && value == customTrueValue) {
            true
        } else if (customFalseValue != null && value == customFalseValue) {
            false
        } else {
            value.toString().toBooleanStrictOrNull() ?: false
        }
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return content?.toString() ?: FormUtils.getText(context, content)
    }

    override fun outputData(json: JSONObject) {
        if (field != null && content != null) {
            if (customTrueValue != null && content == true) {
                json.put(field!!, customTrueValue!!)
            } else if (customFalseValue != null && content == false) {
                json.put(field!!, customFalseValue!!)
            } else json.put(field!!, content)
        }
    }
}