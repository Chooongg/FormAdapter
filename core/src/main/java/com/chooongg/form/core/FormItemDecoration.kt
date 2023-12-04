package com.chooongg.form.core

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlin.math.max

class FormItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter as? FormAdapter ?: return
        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                val style =
                    adapter.getStyle4ItemViewType(parent.getChildViewHolder(view).itemViewType)
                outRect.set(
                    0,
                    max(style.marginInfo.top - style.marginInfo.middleTop, 0),
                    0,
                    0
                )
            }

            adapter.itemCount - 1 -> {
                val style =
                    adapter.getStyle4ItemViewType(parent.getChildViewHolder(view).itemViewType)
                outRect.set(
                    0,
                    0,
                    0,
                    max(style.marginInfo.bottom - style.marginInfo.middleBottom, 0)
                )
            }

            else -> super.getItemOffsets(outRect, view, parent, state)
        }
    }
}