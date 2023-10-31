package com.chooongg.form.core.nameProvider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.InternalFormPartName
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textview.MaterialTextView

class DefaultPartNameProvider : BasePartNameProvider() {
    override fun onCreatePartName(style: BaseStyle, parent: ViewGroup): View {
        return MaterialTextView(parent.context).apply {
            setTextIsSelectable(true)
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceLabel))
            setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
        }
    }

    override fun onBindPartName(holder: FormViewHolder, view: View, item: InternalFormPartName) {
        with(view as MaterialTextView) {
            text = item.getPartName(context)
        }
    }
}