package com.chooongg.form.core.style

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.res.use
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.view.OutlinedCutoutDrawable
import com.google.android.material.shape.MaterialShapeDrawable

class CardOutlinedStyle : BaseCardStyle {

    constructor() : super()
    constructor(block: CardOutlinedStyle.() -> Unit) : super() {
        block(this)
    }

    @ColorRes
    var strokeColorResId: Int? = null

    @DimenRes
    var strokeWidthResId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        super.onBindViewHolder(holder, layout, item)
        val context = holder.itemView.context
        val shape = getShapeAppearanceModel(holder.itemView, item)
        val isRtl = holder.itemView.layoutDirection == View.LAYOUT_DIRECTION_RTL

        val shapeDrawable = OutlinedCutoutDrawable(shape, item.marginBoundary, isRtl)
        val defaultStyle = TypedValue().also {
            context.theme.resolveAttribute(
                com.google.android.material.R.attr.materialCardViewOutlinedStyle, it, true
            )
        }
        val strokeWidth = if (strokeWidthResId != null) {
            context.resources.getDimension(strokeWidthResId!!)
        } else with(defaultStyle) {
            context.obtainStyledAttributes(
                resourceId, intArrayOf(com.google.android.material.R.attr.strokeWidth)
            ).use { it.getDimension(0, 3f) }
        }
        val strokeColor = if (strokeColorResId != null) {
            context.resources.getColor(strokeColorResId!!, context.theme)
        } else with(defaultStyle) {
            context.obtainStyledAttributes(
                resourceId, intArrayOf(com.google.android.material.R.attr.strokeColor)
            ).use { it.getColor(0, Color.GRAY) }
        }
        shapeDrawable.setStroke(strokeWidth, strokeColor)
        holder.itemView.background = shapeDrawable
//        holder.itemView.background = LayerDrawable(arrayOf(shapeDrawable)).apply {
//            val size = shapeDrawable.strokeWidth.toInt()
//            if (item.marginBoundary.top <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerHeight(numberOfLayers - 1, size)
//                setLayerInsetLeft(numberOfLayers - 1, size)
//                setLayerInsetRight(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.TOP)
//            }
//            if (item.marginBoundary.bottom <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerHeight(numberOfLayers - 1, size)
//                setLayerInsetLeft(numberOfLayers - 1, size)
//                setLayerInsetRight(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.BOTTOM)
//            }
//            if (item.marginBoundary.start <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerWidth(numberOfLayers - 1, size)
//                setLayerInsetTop(numberOfLayers - 1, size)
//                setLayerInsetBottom(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.START)
//            }
//            if (item.marginBoundary.end <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerWidth(numberOfLayers - 1, size)
//                setLayerInsetTop(numberOfLayers - 1, size)
//                setLayerInsetBottom(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.END)
//            }
//            if (item.marginBoundary.top <= 0 && item.marginBoundary.start <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerWidth(numberOfLayers - 1, size)
//                setLayerHeight(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.TOP or Gravity.START)
//            }
//            if (item.marginBoundary.top <= 0 && item.marginBoundary.end <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerWidth(numberOfLayers - 1, size)
//                setLayerHeight(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.TOP or Gravity.END)
//            }
//            if (item.marginBoundary.bottom <= 0 && item.marginBoundary.start <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerWidth(numberOfLayers - 1, size)
//                setLayerHeight(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.BOTTOM or Gravity.START)
//            }
//            if (item.marginBoundary.bottom <= 0 && item.marginBoundary.end <= 0) {
//                addLayer(createShapeDrawable(shapeDrawable))
//                setLayerWidth(numberOfLayers - 1, size)
//                setLayerHeight(numberOfLayers - 1, size)
//                setLayerGravity(numberOfLayers - 1, Gravity.BOTTOM or Gravity.END)
//            }
//        }
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }

    private fun createShapeDrawable(drawable: MaterialShapeDrawable) =
        ShapeDrawable(RectShape()).apply {
            paint.color = drawable.fillColor?.defaultColor ?: Color.GRAY
        }

    override fun equals(other: Any?): Boolean {
        if (other !is CardOutlinedStyle) return false
        if (!super.equals(other)) return false
        if (strokeColorResId != other.strokeColorResId) return false
        if (strokeWidthResId != other.strokeWidthResId) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}