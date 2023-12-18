package com.chooongg.form.item

import androidx.annotation.FloatRange
import com.chooongg.form.data.IFormGroupCreator
import kotlin.math.max

class VariantDynamicColumn : VariantBaseForm(null, null), IFormGroupCreator {

    /**
     * 当前列容纳的列数
     */
    @FloatRange(from = 1.0)
    var column: Float = 2f

    override var autoFill: Boolean = false

    override var loneLine: Boolean = true

    override fun getColumn(count: Int, layoutColumn: Int): Int =
        if (layoutColumn * column > 12) 12 else max((layoutColumn * column).toInt(), 1)
}