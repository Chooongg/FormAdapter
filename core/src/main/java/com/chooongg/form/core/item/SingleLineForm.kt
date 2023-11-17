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
                do {
                    column = if (column % i == 0) {
                        column / i
                    } else column / i + 1
                } while (column > 12)
                columnList.add(column)
            }
            columnList.max()
        } else 12
    }
}