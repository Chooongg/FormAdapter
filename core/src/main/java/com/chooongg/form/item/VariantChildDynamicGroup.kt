package com.chooongg.form.item

class VariantChildDynamicGroup(name: Any?, field: String?) : VariantBaseForm(name, field) {
    override fun getColumn(count: Int, layoutColumn: Int) = layoutColumn
}