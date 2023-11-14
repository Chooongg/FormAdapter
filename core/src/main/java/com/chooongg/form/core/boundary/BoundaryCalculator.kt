package com.chooongg.form.core.boundary

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.part.BaseFormPartAdapter

class BoundaryCalculator(private val formAdapter: FormAdapter) {

    fun calculate(adapter: BaseFormPartAdapter) {
        val partAdapters = formAdapter.partAdapters
        val partIndex = partAdapters.indexOf(adapter)
        var spanIndex = 0
        val spanCount = 2520
        val maxColumn = 10
        adapter.itemList.forEachIndexed { index, item ->
            val span = spanCount / formAdapter.columnCount
            item.spanSize = when {
                item.variantIndexInGroup >= 0 -> {
                    span
                }

                item.loneLine -> spanCount
                else -> span
            }
            item.spanSize = if (item.loneLine) spanCount else {
                val span = spanCount / formAdapter.columnCount
                if (item.nextItemLoneLine || (index >= adapter.itemList.lastIndex && spanIndex + span < spanCount)) {
                    spanCount - spanIndex
                } else span
            }
            item.spanIndex = spanIndex
            if (spanIndex == 0) {
                item.marginBoundary.start = Boundary.GLOBAL
                item.insideBoundary.start = Boundary.GLOBAL
            } else {
                item.marginBoundary.start = Boundary.NONE
                item.insideBoundary.start = FormManager.Default.horizontalMiddleBoundary
            }
            spanIndex += item.spanSize
            if (spanIndex >= spanCount) {
                item.marginBoundary.end = Boundary.GLOBAL
                item.insideBoundary.end = Boundary.GLOBAL
                spanIndex = 0
            } else {
                item.marginBoundary.end = Boundary.NONE
                item.insideBoundary.end = FormManager.Default.horizontalMiddleBoundary
            }
            if (item.positionInGroup == 0) {
                var isFirst = true
                var beginIndex = partIndex
                while (beginIndex - 1 >= 0) {
                    if (partAdapters[beginIndex - 1].itemList.isNotEmpty()) {
                        isFirst = false
                        break
                    } else beginIndex--
                }
                if (isFirst) {
                    item.marginBoundary.top = Boundary.GLOBAL
                    item.insideBoundary.top = Boundary.GLOBAL
                } else {
                    item.marginBoundary.top = Boundary.MIDDLE
                    item.insideBoundary.top = Boundary.GLOBAL
                }
            } else if (item.spanIndex == 0) {
                item.marginBoundary.top = Boundary.NONE
                item.insideBoundary.top = Boundary.MIDDLE
            } else {
                var beginIndex = index - 1
                while (adapter.getItem(beginIndex).spanIndex != 0) {
                    beginIndex--
                }
                item.marginBoundary.top = adapter.getItem(beginIndex).marginBoundary.top
                item.insideBoundary.top = adapter.getItem(beginIndex).insideBoundary.top
            }
        }
        for (index in adapter.itemList.lastIndex downTo 0) {
            val item = adapter.getItem(index)
            if (item.countInGroup - 1 - item.positionInGroup == 0) {
                var isLast = true
                var lastIndex = partIndex
                while (lastIndex + 1 < partAdapters.size) {
                    if (partAdapters[lastIndex + 1].itemList.isNotEmpty()) {
                        isLast = false
                        break
                    } else lastIndex++
                }
                if (isLast) {
                    item.marginBoundary.bottom = Boundary.GLOBAL
                    item.insideBoundary.bottom = Boundary.GLOBAL
                } else {
                    item.marginBoundary.bottom = Boundary.MIDDLE
                    item.insideBoundary.bottom = Boundary.GLOBAL
                }
            } else if (item.spanIndex + item.spanSize == spanCount) {
                item.marginBoundary.bottom = Boundary.NONE
                item.insideBoundary.bottom = Boundary.MIDDLE
            } else {
                var lastIndex = index + 1
                while (adapter.getItem(lastIndex).spanIndex + adapter.getItem(lastIndex).spanSize != spanCount) {
                    lastIndex++
                }
                item.marginBoundary.bottom = adapter.getItem(lastIndex).marginBoundary.bottom
                item.insideBoundary.bottom = adapter.getItem(lastIndex).insideBoundary.bottom
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