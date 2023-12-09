package com.chooongg.form.style

import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.res.use
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.view.OutlinedCutoutDrawable

class CardOutlinedStyle : BaseCardStyle() {

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
        if (item.field == "selector") {
            Log.e("Form", "修改了背景")
        }
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
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