package com.chooongg.form.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.item.BaseForm

internal class InternalDynamicAddButtonStyle : BaseStyle() {

    override fun isDecorateNoneItem() = false

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        holder.itemView.updateLayoutParams<GridLayoutManager.LayoutParams> {
            marginStart = when (item.marginBoundary.start) {
                Boundary.GLOBAL -> -holder.style.marginInfo.middleStart
                Boundary.MIDDLE -> -holder.style.marginInfo.middleStart
                else -> -holder.style.marginInfo.middleStart
            }
            marginEnd = when (item.marginBoundary.end) {
                Boundary.GLOBAL -> -holder.style.marginInfo.middleEnd
                Boundary.MIDDLE -> -holder.style.marginInfo.middleEnd
                else -> -holder.style.marginInfo.middleEnd
            }
        }
    }

    override fun addView(parentView: ViewGroup, child: View) = Unit

}