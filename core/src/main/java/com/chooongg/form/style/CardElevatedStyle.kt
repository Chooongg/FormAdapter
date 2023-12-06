package com.chooongg.form.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.core.content.res.use
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.google.android.material.shape.MaterialShapeDrawable

class CardElevatedStyle : BaseCardStyle {

    constructor() : super()
    constructor(block: CardElevatedStyle.() -> Unit) : super() {
        block(this)
    }

    @DimenRes
    var elevationResId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        super.onBindViewHolder(holder, layout, item)
        val context = holder.itemView.context
        val shape = getShapeAppearanceModel(holder.itemView, item)
        holder.itemView.elevation = if (elevationResId == null) {
            context.resources.getDimension(R.dimen.formCardElevation)
        } else context.resources.getDimension(elevationResId!!)
        val shapeDrawable = MaterialShapeDrawable(shape)
        shapeDrawable.fillColor = ColorStateList.valueOf(
            context.obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorSurfaceContainerLow))
                .use { it.getColor(0, Color.GRAY) }
        )
        holder.itemView.background = shapeDrawable
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is CardElevatedStyle) return false
        if (!super.equals(other)) return false
        if (elevationResId != other.elevationResId) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}