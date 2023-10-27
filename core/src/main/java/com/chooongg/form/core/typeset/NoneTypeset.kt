package com.chooongg.form.core.typeset

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.boundary.FormInsideInfo
import com.chooongg.form.core.enum.FormEmsMode
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.style.BaseStyle

class NoneTypeset : BaseTypeset() {

    override var emsMode = FormEmsMode.NONE

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup {
        return FrameLayout(parent.context).apply {
            clipChildren = false
            clipToPadding = false
        }
    }

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) = Unit

    override fun addView(style: BaseStyle, parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }

    override fun setTypesetLayoutPadding(
        holder: FormViewHolder,
        layout: ViewGroup?,
        insideInfo: FormInsideInfo,
        item: BaseForm
    ) {
        super.setTypesetLayoutPadding(holder, layout, insideInfo, item)
    }
}