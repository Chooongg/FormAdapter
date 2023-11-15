package com.chooongg.form.core.boundary

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.part.BaseFormPartAdapter

class BoundaryCalculator(private val formAdapter: FormAdapter) {

    private val spanCount = 27720
    private val maxColumn = 12

    fun calculate(adapter: BaseFormPartAdapter) {
        val partAdapters = formAdapter.partAdapters
        val partIndex = partAdapters.indexOf(adapter)
        var spanIndex = 0
        adapter.itemList.forEachIndexed { index, item ->
            val span = spanCount / formAdapter.columnCount
            item.spanIndex = spanIndex
            item.spanSize = when {
                item.loneLine -> spanCount
                item.nextIsLoneLine -> spanCount - spanIndex
                item.nextIsVariant -> spanCount - spanIndex
                index >= adapter.itemList.lastIndex && spanIndex + span < spanCount -> spanCount - spanIndex
                else -> span
            }
            spanIndex += item.spanSize
            if (spanIndex >= spanCount) {
                spanIndex = 0
            }
        }
    }

    private fun calculateStart() {

    }

    private fun calculateEnd() {

    }

    private fun calculateTop() {

    }

    private fun calculateBottom() {

    }
}