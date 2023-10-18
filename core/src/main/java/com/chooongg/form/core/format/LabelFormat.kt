package com.chooongg.form.core.format

import com.chooongg.form.core.item.BaseFormItem

interface LabelFormat {
    fun format(item: BaseFormItem?): CharSequence?
}