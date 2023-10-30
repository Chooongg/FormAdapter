package com.chooongg.form.core.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textview.MaterialTextView

class CardFilledStyle : BaseCardStyle {

    constructor() : super()
    constructor(block: CardFilledStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        super.onBindViewHolder(holder, layout, item)
        val context = holder.itemView.context
        holder.itemView.clipToOutline = true
        val shape = getShapeAppearanceModel(holder, item)
        val shapeDrawable = MaterialShapeDrawable(shape)
        shapeDrawable.fillColor = ColorStateList.valueOf(
            context.obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorSurfaceContainerHighest))
                .use { it.getColor(0, Color.GRAY) }
        )
        holder.itemView.background = shapeDrawable
    }

    override fun onCreatePartName(parent: ViewGroup): View {
        return MaterialTextView(parent.context).apply {
            setTextIsSelectable(true)
            setTextAppearance(R.style.Form_TextAppearance_Label)
            setPaddingRelative(
                insideInfo.middleStart, insideInfo.middleTop,
                insideInfo.middleEnd, insideInfo.middleBottom
            )
        }
    }

    override fun onBindPartName(holder: FormViewHolder, view: View, item: InternalFormPartName) {
        with(view as MaterialTextView) {
            text = item.getPartName(context)
        }
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }
}