package com.chooongg.form.data

import androidx.annotation.IntRange
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantChildDynamicGroup
import com.chooongg.form.item.VariantChildGroup
import com.chooongg.form.item.VariantDynamicColumn
import com.chooongg.form.item.VariantFixedColumn
import com.chooongg.form.item.VariantSingleLine

interface IFormGroupCreator {

    fun getItems(): MutableList<BaseForm>

    fun addItem(item: BaseForm): Boolean {
        return getItems().add(item)
    }

    fun singleLine(
        block: VariantSingleLine.() -> Unit
    ) = addItem(VariantSingleLine().apply(block))

    fun fixedColumn(
        @IntRange(from = 1, to = 12) column: Int,
        block: VariantFixedColumn.() -> Unit
    ) = addItem(VariantFixedColumn(column).apply(block))

    fun dynamicColumn(
        block: VariantDynamicColumn.() -> Unit
    ) = addItem(VariantDynamicColumn().apply(block))

    fun childGroup(
        name: Any? = null,
        field: String? = null,
        block: VariantChildGroup.() -> Unit
    ) = addItem(VariantChildGroup(name, field).apply(block))

    fun childDynamicGroup(
        name: Any? = null,
        field: String? = null,
        block: VariantChildDynamicGroup.() -> Unit
    ) = addItem(VariantChildDynamicGroup(name, field).apply(block))
}