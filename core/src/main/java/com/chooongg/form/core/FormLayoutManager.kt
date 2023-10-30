package com.chooongg.form.core

import android.content.Context
import android.view.View.MeasureSpec
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.boundary.Boundary
import kotlin.math.max
import kotlin.math.min

class FormLayoutManager(context: Context) : GridLayoutManager(context, 24) {

    private var recyclerView: RecyclerView? = null

    private var adapter: RecyclerView.Adapter<*>? = null

    private var maxItemWidth: Int = FormManager.Default.maxWidth
        set(value) {
            field = value
            if (width != 0) {
                columnCount = max(
                    1,
                    min(4, (width - max(0, formMarginStart) - max(0, formMarginEnd)) / value)
                )
            }
        }

    private var columnCount = 1

    internal var formMarginStart: Int = -1
    internal var formMarginEnd: Int = -1

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val formAdapter = adapter as? FormAdapter ?: return spanCount
                val pair = formAdapter.getWrappedAdapterAndPosition(position)
                return pair.first.getItem(pair.second).spanSize
            }

            override fun getSpanIndex(position: Int, spanCount: Int): Int {
                val formAdapter = adapter as? FormAdapter ?: return 0
                val pair = formAdapter.getWrappedAdapterAndPosition(position)
                return pair.first.getItem(pair.second).spanIndex
            }
        }
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        val width = MeasureSpec.getSize(widthSpec)
        columnCount =
            max(1, min(4, (width - max(0, formMarginStart) - max(0, formMarginEnd)) / maxItemWidth))
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.didStructureChange()) calculateBoundary()
        super.onLayoutChildren(recycler, state)
    }

    private fun calculateBoundary() {
        val formAdapter = adapter as? FormAdapter ?: return
        var spanIndex = 0
        var globalPosition = 0
        formAdapter.partAdapters.forEach { adapter ->
            adapter.itemList.forEachIndexed { index, item ->
                item.globalPosition = globalPosition
                item.spanSize = if (item.loneLine) {
                    spanCount
                } else {
                    val span = spanCount / columnCount
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
                if (spanIndex == spanCount) {
                    item.marginBoundary.end = Boundary.GLOBAL
                    item.insideBoundary.end = Boundary.GLOBAL
                    spanIndex = 0
                } else {
                    item.marginBoundary.end = Boundary.NONE
                    item.insideBoundary.end = FormManager.Default.horizontalMiddleBoundary
                }
                if (item.positionInGroup == 0) {
                    if (item.globalPosition == 0) {
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
                    var lastIndex = index - 1
                    while (adapter.getItem(lastIndex).spanIndex != 0) {
                        lastIndex--
                    }
                    item.marginBoundary.top = adapter.getItem(lastIndex).marginBoundary.top
                    item.insideBoundary.top = adapter.getItem(lastIndex).insideBoundary.top
                }
                globalPosition++
            }
            for (index in adapter.itemList.lastIndex downTo 0) {
                val item = adapter.getItem(index)
                if (item.itemCountInGroup - item.positionInGroup == 1) {
                    if (item.globalPosition == formAdapter.itemCount - 1) {
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
                    var beginIndex = index + 1
                    while (adapter.getItem(beginIndex).spanIndex + adapter.getItem(beginIndex).spanSize != spanCount) {
                        beginIndex++
                    }
                    item.marginBoundary.bottom = adapter.getItem(beginIndex).marginBoundary.bottom
                    item.insideBoundary.bottom = adapter.getItem(beginIndex).insideBoundary.bottom
                }
            }
        }
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        recyclerView = view
        adapter = view.adapter
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        adapter = null
        recyclerView = null
    }

    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) {
        adapter = newAdapter
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        startSmoothScroll(
            CenterSmoothScroller(recyclerView.context).apply {
                targetPosition = position
            }
        )
    }

    fun setFormMargin(start: Int, end: Int) {
        formMarginStart = start
        formMarginEnd = end
    }

    override fun getPaddingStart() = super.getPaddingStart() + max(0, formMarginStart)
    override fun getPaddingEnd() = super.getPaddingEnd() + max(0, formMarginEnd)

    override fun getPaddingLeft() =
        super.getPaddingLeft() + if (isLayoutRTL) max(0, formMarginEnd) else max(0, formMarginStart)

    override fun getPaddingRight() =
        super.getPaddingRight() + if (isLayoutRTL) max(0, formMarginStart) else max(
            0,
            formMarginEnd
        )

    private class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
        override fun calculateDtToFit(
            viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
        ) = (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }
}