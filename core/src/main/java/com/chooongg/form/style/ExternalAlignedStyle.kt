package com.chooongg.form.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.item.BaseForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormChildDynamicPartAdapter
import com.chooongg.form.part.FormChildPartAdapter

class ExternalAlignedStyle : BaseStyle() {

    override fun isDecorateNoneItem() = false

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        holder.itemView.updateLayoutParams<GridLayoutManager.LayoutParams> {
            topMargin = when (item.marginBoundary.top) {
                Boundary.MIDDLE -> {
                    val adapter = holder.bindingAdapter as? BaseFormPartAdapter
                    if (adapter == null || adapter is FormChildPartAdapter || adapter is FormChildDynamicPartAdapter) {
                        0
                    } else -holder.style.marginInfo.middleTop
                }

                else -> 0
            }
            bottomMargin = when (item.marginBoundary.bottom) {
                Boundary.MIDDLE -> {
                    val adapter = holder.bindingAdapter as? BaseFormPartAdapter
                    if (adapter == null || adapter is FormChildPartAdapter || adapter is FormChildDynamicPartAdapter) {
                        0
                    } else -holder.style.marginInfo.middleBottom
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