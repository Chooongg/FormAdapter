package com.chooongg.form.data

import com.chooongg.form.item.MultiColumnForm
import com.chooongg.form.item.SingleLineForm

interface IFormPartCreator : IFormGroupCreator {

    fun singleLine(block: SingleLineForm.() -> Unit) =
        addItem(SingleLineForm().apply(block))

    fun multiColumn(block: MultiColumnForm.() -> Unit) =
        addItem(MultiColumnForm().apply(block))

}