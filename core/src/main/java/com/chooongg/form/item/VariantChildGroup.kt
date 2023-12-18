package com.chooongg.form.item

import androidx.annotation.IntRange
import com.chooongg.form.data.IFormGroupCreator
import kotlin.math.min

class VariantChildGroup(name: Any?, field: String?) : VariantBaseForm(name, field),
    IFormGroupCreator {

    @IntRange(from = 1)
    var maxColumn: Int = 1

    override var autoFill: Boolean = false

    override fun getColumn(count: Int, layoutColumn: Int) = min(maxColumn, layoutColumn)
}