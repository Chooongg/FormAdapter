package com.chooongg.form.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.item.BaseForm

class NegativePaddingStyle : BaseStyle() {

    override fun isDecorateNoneItem() = false

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        if (holder.itemView is RecyclerView) {
            holder.itemView.setPaddingRelative(
                0, when (item.marginBoundary.top) {
                    Boundary.GLOBAL -> holder.style.marginInfo.top
                    Boundary.MIDDLE -> holder.style.marginInfo.top - holder.style.marginInfo.middleTop
                    else -> 0
                }, 0, when (item.marginBoundary.bottom) {
                    Boundary.GLOBAL -> holder.style.marginInfo.bottom
                    Boundary.MIDDLE -> holder.style.marginInfo.bottom - holder.style.marginInfo.middleBottom
                    else -> 0
                }
            )
        } else holder.itemView.updateLayoutParams<GridLayoutManager.LayoutParams> {
            topMargin = when (item.marginBoundary.top) {
                Boundary.GLOBAL -> holder.style.marginInfo.top
                Boundary.MIDDLE -> 0
                else -> 0
            }
            bottomMargin = when (item.marginBoundary.bottom) {
                Boundary.GLOBAL -> holder.style.marginInfo.bottom
                Boundary.MIDDLE -> 0
                else -> 0
            }
            marginStart = when (item.marginBoundary.start) {
                Boundary.GLOBAL -> -holder.style.marginInfo.start
                Boundary.MIDDLE -> -holder.style.marginInfo.middleStart
                else -> -holder.style.marginInfo.middleStart
            }
            marginEnd = when (item.marginBoundary.end) {
                Boundary.GLOBAL -> -holder.style.marginInfo.end
                Boundary.MIDDLE -> -holder.style.marginInfo.middleEnd
                else -> -holder.style.marginInfo.middleEnd
            }
        }
    }

    override fun addView(parentView: ViewGroup, child: View) = Unit

}