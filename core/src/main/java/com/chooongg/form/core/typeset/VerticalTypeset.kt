package com.chooongg.form.core.typeset

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.enum.FormEmsMode
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.menu.FormMenuView
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textview.MaterialTextView

class VerticalTypeset : BaseTypeset() {

    override var emsMode = FormEmsMode.NONE
    override var multiColumnEmsMode: FormEmsMode = FormEmsMode.NONE

    override var contentGravity: Int = Gravity.NO_GRAVITY
    override var multiColumnContentGravity: Int = Gravity.NO_GRAVITY

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup {
        return LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(LinearLayoutCompat(it.context).also { child ->
                child.orientation = LinearLayoutCompat.HORIZONTAL
                child.gravity = Gravity.CENTER_VERTICAL
                child.addView(MaterialTextView(child.context).apply {
                    id = R.id.formInternalNameView
                    setPaddingRelative(
                        style.insideInfo.middleStart, style.insideInfo.middleTop,
                        style.insideInfo.middleEnd, style.insideInfo.middleBottom
                    )
                }, LinearLayoutCompat.LayoutParams(0, -2).apply { weight = 1f })
                child.addView(FormMenuView(child.context).apply {
                    id = R.id.formInternalMenuView
                }, LinearLayoutCompat.LayoutParams(-2, -2))
            }, LinearLayoutCompat.LayoutParams(-1, -2))
        }
    }

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup, item: BaseForm) {
        layout.findViewById<MaterialTextView>(R.id.formInternalNameView).apply {
            setNameViewEms(holder, this)
            text = obtainNameFormatter().format(context, item)
        }
        layout.findViewById<FormMenuView>(R.id.formInternalMenuView).apply {
            if (item.menu != null) {
                inflateMenu(item.menu!!)
            } else clearMenu()
        }
    }

    override fun addView(style: BaseStyle, parentView: ViewGroup, child: View) {
        parentView.addView(child, LinearLayoutCompat.LayoutParams(-1, -2).apply {
            topMargin = -style.insideInfo.middleTop / 2
        })
    }

}