package com.chooongg.form.core.typeset

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.enum.FormEmsMode
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textview.MaterialTextView

class VerticalTypeset : BaseTypeset() {

    override var emsMode = FormEmsMode.NONE

    override var contentGravity: Int = Gravity.NO_GRAVITY
    override var multiColumnContentGravity: Int = Gravity.NO_GRAVITY

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup {
        return LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalNameView
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
            }, LinearLayoutCompat.LayoutParams(-2, -2))
        }
    }

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup, item: BaseForm) {
        layout?.findViewById<MaterialTextView>(R.id.formInternalNameView)?.apply {
            text = obtainNameFormatter().format(context, item)
        }
    }

    override fun addView(style: BaseStyle, parentView: ViewGroup, child: View) {
        parentView.addView(child, LinearLayoutCompat.LayoutParams(-1, -2))
    }

}