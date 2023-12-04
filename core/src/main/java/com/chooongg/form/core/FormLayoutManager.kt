package com.chooongg.form.core

import android.content.Context
import android.util.DisplayMetrics
import android.view.View.MeasureSpec
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class FormLayoutManager(context: Context) : GridLayoutManager(context, 27720) {

    private var recyclerView: RecyclerView? = null

    private var adapter: RecyclerView.Adapter<*>? = null

    private var maxItemWidth: Int = FormManager.Default.maxWidth
        set(value) {
            field = value
            if (width != 0) {
                columnCount = max(
                    1,
                    min(12, (width - max(0, formMarginStart) - max(0, formMarginEnd)) / value)
                )
            }
        }

    var columnCount = 1
        private set(value) {
            if (field != value) {
                field = value
                (adapter as? FormAdapter)?.columnCount = value
            }
        }

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
            max(
                1,
                min(12, (width - max(0, formMarginStart) - max(0, formMarginEnd)) / maxItemWidth)
            )
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