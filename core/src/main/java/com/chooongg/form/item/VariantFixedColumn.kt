package com.chooongg.form.item

import androidx.annotation.IntRange

class VariantFixedColumn(@IntRange(from = 1, to = 12) column: Int) : VariantBaseForm(null, null) {

    /**
     * 列数
     */
    @IntRange(from = 1, to = 12)
    var column: Int = column

    override var autoFill: Boolean = false

    override var loneLine: Boolean = true

    override fun getColumn(count: Int, layoutColumn: Int): Int = column
}