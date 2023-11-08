package com.chooongg.form.core.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.shape.MaterialShapeDrawable

class CardOutlinedStyle : BaseCardStyle {

    constructor() : super()
    constructor(block: CardOutlinedStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        super.onBindViewHolder(holder, layout, item)
        val context = holder.itemView.context
        val shape = getShapeAppearanceModel(holder.itemView, item)
        val shapeDrawable = MaterialShapeDrawable(shape)
        shapeDrawable.setStroke(3f, Color.BLACK)
        shapeDrawable.fillColor = ColorStateList.valueOf(
            context.obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorSurface))
                .use { it.getColor(0, Color.GRAY) }
        )
        holder.itemView.background = LayerDrawable(arrayOf(shapeDrawable)).apply {
            val size = shapeDrawable.strokeWidth.toInt()
            if (item.marginBoundary.top <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerHeight(numberOfLayers - 1, size)
                setLayerInsetLeft(numberOfLayers - 1, size)
                setLayerInsetRight(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.TOP)
            }
            if (item.marginBoundary.bottom <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerHeight(numberOfLayers - 1, size)
                setLayerInsetLeft(numberOfLayers - 1, size)
                setLayerInsetRight(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.BOTTOM)
            }
            if (item.marginBoundary.start <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerWidth(numberOfLayers - 1, size)
                setLayerInsetTop(numberOfLayers - 1, size)
                setLayerInsetBottom(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.START)
            }
            if (item.marginBoundary.end <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerWidth(numberOfLayers - 1, size)
                setLayerInsetTop(numberOfLayers - 1, size)
                setLayerInsetBottom(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.END)
            }
            if (item.marginBoundary.top <= 0 && item.marginBoundary.start <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerWidth(numberOfLayers - 1, size)
                setLayerHeight(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.TOP or Gravity.START)
            }
            if (item.marginBoundary.top <= 0 && item.marginBoundary.end <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerWidth(numberOfLayers - 1, size)
                setLayerHeight(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.TOP or Gravity.END)
            }
            if (item.marginBoundary.bottom <= 0 && item.marginBoundary.start <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerWidth(numberOfLayers - 1, size)
                setLayerHeight(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.BOTTOM or Gravity.START)
            }
            if (item.marginBoundary.bottom <= 0 && item.marginBoundary.end <= 0) {
                addLayer(createShapeDrawable(shapeDrawable))
                setLayerWidth(numberOfLayers - 1, size)
                setLayerHeight(numberOfLayers - 1, size)
                setLayerGravity(numberOfLayers - 1, Gravity.BOTTOM or Gravity.END)
            }
        }
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }

    private fun createShapeDrawable(drawable: MaterialShapeDrawable) =
        ShapeDrawable(RectShape()).apply {
            paint.color = drawable.fillColor?.defaultColor ?: Color.GRAY
        }
}