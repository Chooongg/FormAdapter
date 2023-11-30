package com.chooongg.form.core.groupNameProvider

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView

class DefaultGroupNameProvider : BaseGroupNameProvider() {
    override fun onCreateGroupName(style: BaseStyle, parent: ViewGroup): View {
        return LinearLayoutCompat(parent.context).also {
            it.isBaselineAligned = false
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalContentChildView
                setTextIsSelectable(true)
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceLabel))
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
            }, LinearLayoutCompat.LayoutParams(0, -2).apply {
                gravity = Gravity.CENTER_VERTICAL
                weight = 1f
            })
            it.addView(
                MaterialButton(
                    it.context,
                    null,
                    com.google.android.material.R.attr.materialIconButtonStyle
                ).apply {
                    id = R.id.formInternalContentChildSecondView
                    insetTop = 0
                    insetBottom = 0
                    minWidth = 0
                    minHeight = 0
                    minimumWidth = 0
                    minimumHeight = 0
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
                    setPaddingRelative(
                        style.insideInfo.start, style.insideInfo.middleTop / 2,
                        style.insideInfo.end, style.insideInfo.middleBottom / 2
                    )
                    setTextColor(color)
                    iconTint = ColorStateList.valueOf(color)
                    rippleColor = ColorStateList.valueOf(color)
                    setIconResource(R.drawable.ic_form_remove)
                }, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                    gravity = Gravity.CENTER_VERTICAL
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
            setText(R.string.formDefaultGroupDelete)
            visibility = if (item.isShowDynamicDelete()) View.VISIBLE else View.GONE
            setOnClickListener {
                if (item.isHasDeleteConfirm) {
                    MaterialAlertDialogBuilder(context).setTitle(R.string.formDefaultGroupDelete)
                        .setMessage(R.string.formDefaultGroupDeleteConfirm)
                        .setPositiveButton(R.string.formDefaultGroupDelete) { dialog, _ ->
                            item.deleteDynamicGroup()
                            dialog.dismiss()
                        }.show()
                } else item.deleteDynamicGroup()
            }
        }
    }
}