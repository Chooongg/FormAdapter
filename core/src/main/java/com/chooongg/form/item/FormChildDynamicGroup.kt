package com.chooongg.form.item

class FormChildDynamicGroup(name: Any?, field: String?) : VariantForm(name, field) {
    override fun getColumn(count: Int, layoutColumn: Int) = layoutColumn
}