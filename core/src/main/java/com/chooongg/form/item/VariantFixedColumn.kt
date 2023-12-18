package com.chooongg.form.item

import androidx.annotation.IntRange
import com.chooongg.form.data.IFormGroupCreator

class VariantFixedColumn(
    /**
     * 列数
     */
    @IntRange(from = 1, to = 12) var column: Int
) : VariantBaseForm(null, null), IFormGroupCreator {

    override var autoFill: Boolean = false

    override var loneLine: Boolean = true

    override fun getColumn(count: Int, layoutColumn: Int): Int = column
}