package com.chooongg.form.core.nameProvider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.InternalFormGroupName
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textview.MaterialTextView

class DefaultGroupNameProvider : BaseGroupNameProvider() {
    override fun onCreateGroupName(style: BaseStyle, parent: ViewGroup): View {
        return MaterialTextView(parent.context).apply {
            setTextIsSelectable(true)
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceLabel))
            setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
        }
    }

    override fun onBindGroupName(
        holder: FormViewHolder,
        view: View,
        item: InternalFormGroupName,
        enabled: Boolean
    ) {
        with(view as MaterialTextView) {
            isEnabled = enabled
            text = item.getPartName(context)
        }
    }
}