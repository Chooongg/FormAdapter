package com.chooongg.form.core.typeset

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.enum.FormEmsMode
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.menu.FormMenuView
import com.chooongg.form.core.part.BaseFormPartAdapter
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textview.MaterialTextView

class HorizontalTypeset : BaseTypeset {

    constructor() : super()
    constructor(block: HorizontalTypeset.() -> Unit) : super() {
        block(this)
    }

    override var emsMode = FormEmsMode.FIXED
    override var multiColumnEmsMode: FormEmsMode = FormEmsMode.FIXED

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup {
        return LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.HORIZONTAL
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalNameView
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceName))
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    0, style.insideInfo.middleBottom
                )
            }, LinearLayoutCompat.LayoutParams(-2, -2))
            it.addView(FormMenuView(it.context, style).apply {
                id = R.id.formInternalMenuView
            }, LinearLayoutCompat.LayoutParams(-2, -2))
        }
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup,
        item: BaseForm,
        enabled: Boolean
    ) {
        layout.findViewById<MaterialTextView>(R.id.formInternalNameView)?.apply {
            setNameViewEms(holder, this)
            text = obtainNameFormatter().format(context, item)
        }
        layout.findViewById<FormMenuView>(R.id.formInternalMenuView).apply {
            if (item.isRealMenuVisible(enabled) && item.menu != null) {
                visibility = View.VISIBLE
                inflateMenu(
                    item.menu!!,
                    item.isRealMenuEnable(enabled),
                    item.getMenuCreateOptionCallback(),
                    {
                        val isIntercept = item.getMenuClickListener()?.invoke(context, it, item)
                        if (isIntercept != true) {
                            val adapter = holder.bindingAdapter as? BaseFormPartAdapter
                            adapter?.formAdapter?.getMenuClickListener()?.invoke(context, it, item)
                        }
                        true
                    },
                    item.menuShowTitle
                )
            } else {
                visibility = View.GONE
                clearMenu()
            }
        }
    }

    override fun addView(style: BaseStyle, parentView: ViewGroup, child: View) {
        parentView.addView(child, 1, LinearLayoutCompat.LayoutParams(0, -2).apply {
            weight = 1f
        })
    }
}