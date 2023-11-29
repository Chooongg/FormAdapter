package com.chooongg.form.core.groupNameProvider

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.InternalFormGroupName
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.textview.MaterialTextView

class DefaultGroupNameProvider : BaseGroupNameProvider() {
    override fun onCreateGroupName(style: BaseStyle, parent: ViewGroup): View {
        return LinearLayoutCompat(parent.context).also {
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalContentChildView
                setTextIsSelectable(true)
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceLabel))
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
            }, LinearLayoutCompat.LayoutParams(0, -2).apply { weight = 1f })
            it.addView(MaterialButton(it.context).apply {
                id = R.id.formInternalContentChildSecondView
                insetTop = 0
                insetBottom = 0

                setBackgroundColor(
                    MaterialColors.getColor(
                        this, com.google.android.material.R.attr.colorErrorContainer,
                        Color.GRAY
                    )
                )
                val color = MaterialColors.getColor(
                    this,
                    com.google.android.material.R.attr.colorError,
                    Color.WHITE
                )
                setTextColor(color)
                iconTint = ColorStateList.valueOf(color)
                setIconResource(R.drawable.ic_form_remove)
            })
        }
    }

    override fun onBindGroupName(
        holder: FormViewHolder,
        view: View,
        item: InternalFormGroupName,
        enabled: Boolean
    ) {
        with(view.findViewById<MaterialTextView>(R.id.formInternalContentChildView)) {
            isEnabled = enabled
            text = item.getPartName(context)
        }
        with(view.findViewById<MaterialButton>(R.id.formInternalContentChildSecondView)) {
            visibility = if (item.isShowDynamicDelete()) View.VISIBLE else View.GONE
            setOnClickListener { item.deleteDynamicGroup() }
        }
    }
}