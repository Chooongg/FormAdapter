package com.chooongg.form.core.format

import com.chooongg.form.core.abstractItem.BaseFormItem

interface LabelFormat {
    fun format(item: BaseFormItem?): CharSequence?
}