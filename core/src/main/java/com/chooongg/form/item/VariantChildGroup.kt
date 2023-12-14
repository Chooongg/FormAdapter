package com.chooongg.form.item

class VariantChildGroup(name: Any?, field: String?) : VariantBaseForm(name, field) {

    override var autoFill: Boolean = false

    override fun getColumn(count: Int, layoutColumn: Int) = 1
}