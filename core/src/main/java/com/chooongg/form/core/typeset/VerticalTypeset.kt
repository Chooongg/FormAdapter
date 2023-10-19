package com.chooongg.form.core.typeset

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.textview.MaterialTextView

class VerticalTypeset : BaseTypeset() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup {
        return LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalLabelView
            })
        }
    }

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup, item: BaseForm) {
        with(layout.findViewById<MaterialTextView>(R.id.formInternalLabelView)) {
            text = item.getNameString(context)
        }
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }

}