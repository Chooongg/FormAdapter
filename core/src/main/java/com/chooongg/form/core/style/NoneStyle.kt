package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName
import com.google.android.material.textview.MaterialTextView

class NoneStyle : BaseStyle {

    constructor() : super()
    constructor(block: NoneStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
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

    override fun addView(parentView: ViewGroup, child: View) = Unit

}