package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.item.BaseForm

class NoneStyle : BaseStyle {

    constructor() : super()
    constructor(block: NoneStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        holder.itemView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = when (item.marginBoundary.top) {
                Boundary.GLOBAL -> marginInfo.top
                Boundary.MIDDLE -> marginInfo.middleTop
                else -> 0
            }
            bottomMargin = when (item.marginBoundary.bottom) {
                Boundary.GLOBAL -> marginInfo.bottom
                Boundary.MIDDLE -> marginInfo.middleBottom
                else -> 0
            }
        }
    }

    override fun addView(parentView: ViewGroup, child: View) = Unit

}