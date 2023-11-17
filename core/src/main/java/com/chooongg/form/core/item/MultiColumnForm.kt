package com.chooongg.form.core.item

import androidx.annotation.FloatRange
import kotlin.math.max

class MultiColumnForm : VariantForm() {

    @FloatRange(from = 1.0)
    var column: Float = 2f

    override var autoFill: Boolean = false

    override fun getColumn(count: Int, layoutColumn: Int): Int =
        if (layoutColumn * column > 12) 12 else max((layoutColumn * column).toInt(), 1)
}