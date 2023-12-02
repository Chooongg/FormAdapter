package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.provider.FormSwitchProvider
import org.json.JSONObject

class FormSwitch : BaseForm {

    var customOutputTrue: Any? = null

    var customOutputFalse: Any? = null

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    override fun getProvider(adapter: FormAdapter) = FormSwitchProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) {
            content = false
            return
        }
        if (value is Boolean) return
        content = value.toString().toBooleanStrictOrNull() ?: false
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return content?.toString() ?: FormUtils.getText(context, content)
    }

    override fun outputData(json: JSONObject) {
        if (field != null && content != null) {
            if (content == true && customOutputTrue != null) {
                json.put(field!!, customOutputTrue!!)
            } else if (content == false && customOutputFalse != null) {
                json.put(field!!, customOutputFalse!!)
            } else json.put(field!!, content)
        }
    }
}