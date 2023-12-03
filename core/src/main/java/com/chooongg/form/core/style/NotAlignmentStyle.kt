package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.part.BaseFormPartAdapter

class NotAlignmentStyle : BaseStyle {

    constructor() : super()
    constructor(block: NotAlignmentStyle.() -> Unit) : super() {
        block(this)
    }

    override fun isDecorateNoneItem() = false

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        holder.itemView.updateLayoutParams<GridLayoutManager.LayoutParams> {
            topMargin = when (item.marginBoundary.top) {
                Boundary.MIDDLE -> {
                    if (holder.absoluteAdapterPosition == 0) 0 else -holder.style.marginInfo.middleTop
                }

                else -> 0
            }
            bottomMargin = when (item.marginBoundary.bottom) {
                Boundary.MIDDLE -> {
                    val adapter = holder.bindingAdapter as? BaseFormPartAdapter
                    val itemCount = adapter?.formAdapter?.itemCount ?: 0
                    if (holder.absoluteAdapterPosition == itemCount - 1) 0
                    else -holder.style.marginInfo.middleBottom
                }

                else -> 0
            }
            marginStart = when (item.marginBoundary.start) {
                Boundary.GLOBAL -> -holder.style.marginInfo.start
                Boundary.MIDDLE -> -holder.style.marginInfo.middleStart
                else -> 0
            }
            marginEnd = when (item.marginBoundary.end) {
                Boundary.GLOBAL -> -holder.style.marginInfo.end
                Boundary.MIDDLE -> -holder.style.marginInfo.middleEnd
                else -> 0
            }
        }
    }

    override fun addView(parentView: ViewGroup, child: View) = Unit

}