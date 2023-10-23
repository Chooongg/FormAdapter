package com.chooongg.form.core

import android.content.Context
import android.view.View.MeasureSpec
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class FormLayoutManager(private val context: Context) : GridLayoutManager(context, 24) {

    private var recyclerView: RecyclerView? = null

    private var adapter: RecyclerView.Adapter<*>? = null

    private var maxItemWidth: Int = FormManager.Default.maxWidth
        set(value) {
            field = value
            if (width != 0) {
                columnCount = max(1, min(10, (width - formMarginStart - formMarginEnd) / value))
            }
        }

    private var columnCount = 1

    private var formMarginStart: Int = 0
    private var formMarginTop: Int = 0
    private var formMarginEnd: Int = 0
    private var formMarginBottom: Int = 0

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val formAdapter = adapter as? FormAdapter ?: return spanCount
                val pair = formAdapter.getWrappedAdapterAndPosition(position)
                val item = pair.first.getItem(pair.second)
//                item.globalPosition = position
                return 24 / columnCount
            }

//            override fun getSpanIndex(position: Int, spanCount: Int): Int {
//                val adapter = recyclerView?.adapter as? FormAdapter ?: return 0
//                val pair = adapter.getWrappedAdapterAndPosition(position)
//                return pair.first.getItem(pair.second).spanIndex
//            }
        }
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        columnCount = max(
            1,
            min(
                4,
                (MeasureSpec.getSize(widthSpec) - formMarginStart - formMarginEnd) / maxItemWidth
            )
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
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

    fun setFormMargin(start: Int, top: Int, end: Int, bottom: Int) {
        formMarginStart = start
        formMarginTop = top
        formMarginEnd = end
        formMarginBottom = bottom
    }

    override fun getPaddingTop() = super.getPaddingTop() + formMarginTop
    override fun getPaddingBottom() = super.getPaddingBottom() + formMarginBottom

    override fun getPaddingStart() = super.getPaddingStart() + formMarginEnd
    override fun getPaddingEnd() = super.getPaddingEnd() + formMarginEnd

    override fun getPaddingLeft() =
        super.getPaddingLeft() + if (isLayoutRTL) formMarginEnd else formMarginStart

    override fun getPaddingRight() =
        super.getPaddingRight() + if (isLayoutRTL) formMarginStart else formMarginEnd

    private class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
        override fun calculateDtToFit(
            viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
        ) = (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }
}