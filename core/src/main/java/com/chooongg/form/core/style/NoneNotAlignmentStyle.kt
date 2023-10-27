package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.item.BaseForm

class NoneNotAlignmentStyle : BaseStyle {

    constructor() : super()
    constructor(block: NoneNotAlignmentStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        holder.itemView.updateLayoutParams<GridLayoutManager.LayoutParams> {
            topMargin = when (item.marginBoundary.top) {
                Boundary.GLOBAL -> -holder.style.insideInfo.top
                Boundary.MIDDLE -> -holder.style.insideInfo.middleTop
                else -> 0
            }
            bottomMargin = when (item.marginBoundary.bottom) {
                Boundary.GLOBAL -> -holder.style.insideInfo.bottom
                Boundary.MIDDLE -> -holder.style.insideInfo.middleBottom
                else -> 0
            }
            marginStart = when (item.marginBoundary.start) {
                Boundary.GLOBAL -> -holder.style.insideInfo.start
                Boundary.MIDDLE -> -holder.style.insideInfo.middleStart
                else -> 0
            }
            marginEnd = when (item.marginBoundary.end) {
                Boundary.GLOBAL -> -holder.style.insideInfo.end
                Boundary.MIDDLE -> -holder.style.insideInfo.middleEnd
                else -> 0
            }
        }
    }

    override fun addView(parentView: ViewGroup, child: View) = Unit

}