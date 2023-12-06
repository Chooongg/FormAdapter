package com.chooongg.form.style

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.google.android.material.shape.AbsoluteCornerSize
import com.google.android.material.shape.ShapeAppearanceModel

abstract class BaseCardStyle : BaseStyle() {

    @StyleRes
    var shapeAppearanceResId: Int? = null

    protected lateinit var shapeAppearanceModel: ShapeAppearanceModel

    @CallSuper
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

    protected fun getShapeAppearanceModel(view: View, item: BaseForm) =
        ShapeAppearanceModel.builder().apply {
            if (view.layoutDirection != View.LAYOUT_DIRECTION_RTL) {
                setTopLeftCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.topLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setTopRightCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.topRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomLeftCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.bottomLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomRightCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.bottomRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
            } else {
                setTopLeftCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.topRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setTopRightCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.topLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomLeftCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.bottomRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomRightCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.bottomLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
            }
        }.build()

    override fun equals(other: Any?): Boolean {
        if (other !is BaseCardStyle) return false
        if (!super.equals(other)) return false
        if (shapeAppearanceResId != other.shapeAppearanceResId) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}