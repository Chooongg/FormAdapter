package com.chooongg.form

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.part.BaseFormPartAdapter
import kotlin.math.max

open class BaseFormLayoutManager(context: Context) : GridLayoutManager(context, 27720) {

    protected var adapter: RecyclerView.Adapter<*>? = null

    internal var formMarginStart: Int = -1
    internal var formMarginEnd: Int = -1

    init {
        configSpanSizeLookup()
    }

    protected open fun configSpanSizeLookup() {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val partAdapter = adapter as? BaseFormPartAdapter ?: return spanCount
                return partAdapter.getItem(position).spanSize
            }

            override fun getSpanIndex(position: Int, spanCount: Int): Int {
                val partAdapter = adapter as? BaseFormPartAdapter ?: return spanCount
                return partAdapter.getItem(position).spanIndex
            }
        }
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        adapter = view.adapter
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        adapter = null
    }

    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) {
        adapter = newAdapter
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

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        startSmoothScroll(CenterSmoothScroller(recyclerView.context).apply {
            targetPosition = position
        })
    }

    private class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
        override fun calculateDtToFit(
            viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
        ) = (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }
}