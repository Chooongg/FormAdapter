package com.chooongg.form.core.item

import androidx.annotation.IntRange

class MultiColumnForm : VariantForm() {

    override var isTileToEnd: Boolean = false

    @IntRange(from = 1)
    var column: Int = 2

    override fun getColumn(count: Int, layoutColumn: Int): Int =
        if (layoutColumn * column > 12) 12 else layoutColumn * column
}