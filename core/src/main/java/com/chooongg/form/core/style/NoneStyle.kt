package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm

class NoneStyle : BaseStyle {

    constructor() : super()
    constructor(block: NoneStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) = Unit

    override fun addView(parentView: ViewGroup, child: View) = Unit

}