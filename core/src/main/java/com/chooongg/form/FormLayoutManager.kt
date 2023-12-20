package com.chooongg.form

import android.content.Context
import android.view.View.MeasureSpec
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class FormLayoutManager(context: Context, maxItemWidth: Int = 0) : BaseFormLayoutManager(context) {

    var maxItemWidth: Int = maxItemWidth
        set(value) {
            if (field != value) {
                field = value
                if (width != 0) {
                    columnCount = max(
                        1,
                        min(12, (width - max(0, formMarginStart) - max(0, formMarginEnd)) / value)
                    )
                }
            }
        }

    var columnCount = 1
        private set(value) {
            if (field != value) {
                field = value
                (adapter as? FormAdapter)?.columnCount = value
            }
        }

    override fun configSpanSizeLookup() {
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
        val width = MeasureSpec.getSize(widthSpec)
        columnCount =
            max(
                1,
                min(12, (width - max(0, formMarginStart) - max(0, formMarginEnd)) / maxItemWidth)
            )
        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.didStructureChange()) {
            var position = 0
            (adapter as? FormAdapter)?.partAdapters?.forEach {
                for (i in 0 until it.itemCount) {
                    it.getItem(i).globalPosition = position
                    position++
                }
            }
        }
        super.onLayoutChildren(recycler, state)
    }
}