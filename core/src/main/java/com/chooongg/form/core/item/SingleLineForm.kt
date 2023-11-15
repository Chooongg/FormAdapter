package com.chooongg.form.core.item

class SingleLineForm : VariantForm() {

    var autoEquipartition: Boolean = true

    override fun getColumn(count: Int, layoutColumn: Int): Int {
        val size = getItems().size
        return if (size <= 12) size
        else if (autoEquipartition) {
            val columnList = ArrayList<Int>()
            for (i in 2..12) {
                var column = size
                while (column / i > 12) {
                    column /= i
                }
                columnList.add(column)
            }
            columnList.max()
        } else 12
    }
}