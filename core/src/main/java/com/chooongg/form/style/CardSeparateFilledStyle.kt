package com.chooongg.form.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.res.use
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.google.android.material.shape.MaterialShapeDrawable

class CardSeparateFilledStyle : BaseSeparateCardStyle() {

    @ColorRes
    var colorResId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        super.onBindViewHolder(holder, layout, item)
        val context = holder.itemView.context
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        shapeDrawable.fillColor = ColorStateList.valueOf(
            if (colorResId != null) {
                context.getColor(colorResId!!)
            } else {
                context.obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorSurfaceContainerHigh))
                    .use { it.getColor(0, Color.GRAY) }
            }
        )
        holder.itemView.background = shapeDrawable
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is CardSeparateFilledStyle) return false
        if (!super.equals(other)) return false
        if (colorResId != other.colorResId) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}