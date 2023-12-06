package com.chooongg.form.style

import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.google.android.material.shape.ShapeAppearanceModel

abstract class BaseSeparateCardStyle : BaseStyle() {

    override var horizontalIsSeparateItem: Boolean = true

    override var horizontalMiddleBoundary: Int = Boundary.GLOBAL

    @StyleRes
    var shapeAppearanceResId: Int? = null

    protected lateinit var shapeAppearanceModel: ShapeAppearanceModel

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        holder.itemView.clipToOutline = true
        if (!this::shapeAppearanceModel.isInitialized) {
            val resId = if (shapeAppearanceResId == null) {
                holder.itemView.context.obtainStyledAttributes(intArrayOf(R.attr.formShapeAppearanceCorner))
                    .use {
                        it.getResourceId(
                            0,
                            com.google.android.material.R.style.ShapeAppearance_Material3_Corner_Medium
                        )
                    }
            } else shapeAppearanceResId!!
            shapeAppearanceModel =
                ShapeAppearanceModel.builder(holder.itemView.context, resId, 0).build()
        }
        item.insideBoundary.start = Boundary.GLOBAL
        item.insideBoundary.top = Boundary.GLOBAL
        item.insideBoundary.end = Boundary.GLOBAL
        item.insideBoundary.bottom = Boundary.GLOBAL
        holder.itemView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = when (item.marginBoundary.top) {
                Boundary.GLOBAL -> marginInfo.top
                else -> marginInfo.middleTop
            }
            bottomMargin = when (item.marginBoundary.bottom) {
                Boundary.GLOBAL -> marginInfo.bottom
                else -> marginInfo.middleBottom
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BaseSeparateCardStyle) return false
        if (!super.equals(other)) return false
        if (shapeAppearanceResId != other.shapeAppearanceResId) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}