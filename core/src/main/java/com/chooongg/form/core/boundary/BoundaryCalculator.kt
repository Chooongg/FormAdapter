package com.chooongg.form.core.boundary

import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.part.BaseFormPartAdapter

class BoundaryCalculator(private val adapter: BaseFormPartAdapter) {

    private val spanCount = 27720
    private val maxColumn = 12

    fun calculateBoundary(item: BaseForm, position: Int) {
        val partAdapters = adapter.formAdapter.partAdapters
        val partIndex = partAdapters.indexOf(adapter)
        // Start
        if (item.spanIndex == 0) {
            item.marginBoundary.start = Boundary.GLOBAL
            item.insideBoundary.start = Boundary.GLOBAL
        } else {
            item.marginBoundary.start = Boundary.NONE
            item.insideBoundary.start = adapter.style.horizontalMiddleBoundary
        }
        // End
        if (item.spanIndex + item.spanSize >= spanCount) {
            item.marginBoundary.end = Boundary.GLOBAL
            item.insideBoundary.end = Boundary.GLOBAL
        } else {
            item.marginBoundary.end = Boundary.NONE
            item.insideBoundary.end = adapter.style.horizontalMiddleBoundary
        }
        // Top
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
            var beginIndex = position - 1
            var beginItem = adapter.getItem(beginIndex)
            while (beginItem.spanIndex != 0) {
                beginIndex--
                beginItem = adapter.getItem(beginIndex)
            }
            item.marginBoundary.top = beginItem.marginBoundary.top
            item.insideBoundary.top = beginItem.insideBoundary.top
        }
        // Bottom
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
            var lastIndex = position + 1
            var lastItem = adapter.getItem(lastIndex)
            while (lastIndex > 0 && lastItem.countInGroup - 1 - lastItem.positionInGroup != 0
                && (lastIndex + 1 < adapter.itemList.size && adapter.getItem(lastIndex + 1).spanIndex != 0)
            ) {
                lastIndex++
                lastItem = adapter.getItem(lastIndex)
            }
            item.marginBoundary.bottom = adapter.getItem(lastIndex).marginBoundary.bottom
            item.insideBoundary.bottom = adapter.getItem(lastIndex).insideBoundary.bottom
        }
    }
}