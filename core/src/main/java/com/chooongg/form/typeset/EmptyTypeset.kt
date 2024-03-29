package com.chooongg.form.typeset

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.boundary.FormInsideInfo
import com.chooongg.form.enum.FormEmsMode
import com.chooongg.form.item.BaseForm
import com.chooongg.form.style.BaseStyle

object EmptyTypeset : BaseTypeset() {

    override var emsMode = FormEmsMode.NONE
    override var multiColumnEmsMode: FormEmsMode = FormEmsMode.NONE

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup,
        item: BaseForm,
        enabled: Boolean
    ) = Unit

    override fun addView(style: BaseStyle, parentView: ViewGroup, child: View) = Unit

    override fun setTypesetLayoutPadding(
        holder: FormViewHolder,
        layout: ViewGroup?,
        insideInfo: FormInsideInfo,
        item: BaseForm
    ) {
        if (item.preventAdjustmentBoundaries) return
        (layout ?: holder.itemView as? ViewGroup)?.setPaddingRelative(
            when (item.insideBoundary.start) {
                Boundary.GLOBAL -> insideInfo.start - insideInfo.middleStart
                else -> 0
            }, when (item.insideBoundary.top) {
                Boundary.GLOBAL -> insideInfo.top - insideInfo.middleTop
                else -> 0
            }, when (item.insideBoundary.end) {
                Boundary.GLOBAL -> insideInfo.end - insideInfo.middleEnd
                else -> 0
            }, when (item.insideBoundary.bottom) {
                Boundary.GLOBAL -> insideInfo.bottom - insideInfo.middleBottom
                else -> 0
            }
        )
    }
}