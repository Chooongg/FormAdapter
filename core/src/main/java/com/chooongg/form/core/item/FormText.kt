package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormTextProvider

class FormText(label: CharSequence?) : BaseFormItem(label) {

    override fun getProvider(adapter: FormAdapter) = FormTextProvider::class

}