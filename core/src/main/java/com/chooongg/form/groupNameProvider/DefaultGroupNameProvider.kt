package com.chooongg.form.groupNameProvider

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.formTextAppearance
import com.chooongg.form.getTextAppearance
import com.chooongg.form.item.InternalFormGroupName
import com.chooongg.form.menu.FormMenuView
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.style.BaseStyle
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
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceGroupName))
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
            }, LinearLayoutCompat.LayoutParams(0, -2).apply {
                gravity = Gravity.CENTER_VERTICAL
                weight = 1f
            })
            it.addView(FormMenuView(it.context, style).apply {
                id = R.id.formInternalMenuView
            }, LinearLayoutCompat.LayoutParams(-2, -2))
            it.addView(
                MaterialButton(
                    it.context,
                    null,
                    com.google.android.material.R.attr.borderlessButtonStyle
                ).apply {
                    id = R.id.formInternalContentChildSecondView
                    insetTop = 0
                    insetBottom = 0
                    minWidth = 0
                    minHeight = 0
                    minimumWidth = 0
                    minimumHeight = 0
                    setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                    rippleColor = ColorStateList.valueOf(
                        MaterialColors.getColor(
                            this, com.google.android.material.R.attr.colorErrorContainer, Color.GRAY
                        )
                    )
                    val color = MaterialColors.getColor(
                        this, com.google.android.material.R.attr.colorError, Color.WHITE
                    )
                    setTextColor(color)
                    iconTint = ColorStateList.valueOf(color)
                    setIconResource(R.drawable.ic_form_delete)
                    setPaddingRelative(
                        style.insideInfo.middleStart, style.insideInfo.middleTop,
                        style.insideInfo.middleEnd, style.insideInfo.middleBottom
                    )
                    val textView = TextView(context).apply {
                        setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                        measure(0, 0)
                    }
                    iconSize = textView.measuredHeight
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
        val title = view.findViewById<MaterialTextView>(R.id.formInternalContentChildView).apply {
            text = item.getPartName(context)
        }
        view.findViewById<FormMenuView>(R.id.formInternalMenuView).apply {
            if (item.isRealMenuVisible(enabled) && item.menu != null) {
                inflateMenu(
                    item.menu!!,
                    item.isRealMenuEnable(enabled),
                    item.getMenuCreateOptionCallback()
                ) {
                    val isIntercept = item.getMenuClickListener()?.invoke(context, it, item)
                    if (isIntercept != true) {
                        (holder.bindingAdapter as? BaseFormPartAdapter)?.formAdapter
                            ?.getOnMenuClickListener()?.invoke(context, it, item)
                    }
                    true
                }
            } else clearMenu()
        }
        view.findViewById<MaterialButton>(R.id.formInternalContentChildSecondView).apply {
            setText(R.string.formDefaultGroupDelete)
            visibility = if (enabled && item.isShowDynamicDelete()) {
                setOnClickListener {
                    if (item.isHasDeleteConfirm) {
                        val alertDialog =
                            MaterialAlertDialogBuilder(context).setTitle(R.string.formDefaultGroupDeleteConfirm)
                                .setMessage(
                                    context.getString(
                                        R.string.formDefaultGroupDeleteMessage,
                                        title.text
                                    )
                                ).setPositiveButton(R.string.formDefaultGroupDelete) { dialog, _ ->
                                    item.deleteDynamicGroup()
                                    dialog.dismiss()
                                }.setNegativeButton(R.string.formDefaultGroupCancel) { dialog, _ ->
                                    dialog.dismiss()
                                }.create()
                        alertDialog.show()
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(textColors)
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(hintTextColors)
                    } else item.deleteDynamicGroup()
                }
                View.VISIBLE
            } else View.GONE
        }
    }
}