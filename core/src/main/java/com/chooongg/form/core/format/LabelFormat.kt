package com.chooongg.form.core.format

import com.chooongg.form.core.item.BaseForm

interface LabelFormat {
    fun format(item: BaseForm?): CharSequence?
}