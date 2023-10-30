package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName
import com.google.android.material.textview.MaterialTextView

class NoneNotAlignmentStyle : BaseStyle {

    constructor() : super()
    constructor(block: NoneNotAlignmentStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        holder.itemView.updateLayoutParams<GridLayoutManager.LayoutParams> {
            topMargin = when (item.marginBoundary.top) {
                Boundary.MIDDLE -> -holder.style.marginInfo.middleTop
                else -> 0
            }
            bottomMargin = when (item.marginBoundary.bottom) {
                Boundary.MIDDLE -> -holder.style.marginInfo.middleBottom
                else -> 0
            }
            marginStart = when (item.marginBoundary.start) {
                Boundary.GLOBAL -> -holder.style.marginInfo.start
                Boundary.MIDDLE -> -holder.style.marginInfo.middleStart
                else -> 0
            }
            marginEnd = when (item.marginBoundary.end) {
                Boundary.GLOBAL -> -holder.style.marginInfo.end
                Boundary.MIDDLE -> -holder.style.marginInfo.middleEnd
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