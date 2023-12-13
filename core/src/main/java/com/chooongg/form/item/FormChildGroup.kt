package com.chooongg.form.item

class FormChildGroup(name: Any?, field: String?) : VariantForm(name, field) {
    override fun getColumn(count: Int, layoutColumn: Int) = layoutColumn
}