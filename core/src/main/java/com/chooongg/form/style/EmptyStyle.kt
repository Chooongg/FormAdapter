package com.chooongg.form.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm

class EmptyStyle : BaseStyle() {

    override fun isDecorateNoneItem() = false

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
    }

    override fun addView(parentView: ViewGroup, child: View) = Unit

}