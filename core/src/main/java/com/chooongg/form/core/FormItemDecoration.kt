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
        when (val position = parent.getChildAdapterPosition(view)) {
            0 -> {
                val part = adapter.getPartAdapter(position)
                outRect.set(
                    0,
                    max(part.style.marginInfo.top - part.style.marginInfo.middleTop, 0),
                    0,
                    0
                )
            }

            adapter.itemCount - 1 -> {
                val part = adapter.getPartAdapter(position)
                outRect.set(
                    0,
                    0,
                    0,
                    max(part.style.marginInfo.bottom - part.style.marginInfo.middleBottom, 0)
                )
            }

            else -> super.getItemOffsets(outRect, view, parent, state)
        }
    }
}