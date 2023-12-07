package com.chooongg.form

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.chooongg.form.boundary.Boundary
import kotlin.math.max

class FormItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter as? FormAdapter ?: return
        val style = adapter.getStyle4ItemViewType(parent.getChildViewHolder(view).itemViewType)
        val position = parent.getChildAdapterPosition(view)
        val item = adapter.getItem(position)
        val top = if (position == 0) {
            max(style.marginInfo.top - style.marginInfo.middleTop, 0)
        } else 0
        val bottom = if (position == adapter.itemCount - 1) {
            max(style.marginInfo.bottom - style.marginInfo.middleBottom, 0)
        } else 0
        val start = if (style.getHorizontalIsSeparateItem()) {
            when (item.marginBoundary.start) {
                Boundary.GLOBAL -> 0
                else -> {
                    val columns = 27720 / item.spanSize
                    val index = item.spanIndex / item.spanSize
                    (style.marginInfo.middleStart + style.marginInfo.middleEnd) / columns * index
                }
            }
        } else 0
        val end = if (style.getHorizontalIsSeparateItem()) {
            when (item.marginBoundary.end) {
                Boundary.GLOBAL -> 0
                else -> {
                    val columns = 27720 / item.spanSize
                    val index = item.spanIndex / item.spanSize
                    (style.marginInfo.middleStart + style.marginInfo.middleEnd) / columns * (columns - 1 - index)
                }
            }
        } else 0
        if (view.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            outRect.set(end, top, start, bottom)
        } else {
            outRect.set(start, top, end, bottom)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        Log.e("Form", "parent: ${parent.width}, ${parent.measuredWidth}")
        super.onDraw(c, parent, state)
    }
}