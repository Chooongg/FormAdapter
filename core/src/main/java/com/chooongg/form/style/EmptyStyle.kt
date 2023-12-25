package com.chooongg.form.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.EmptyTypeset

class EmptyStyle : BaseStyle() {

    override var typeset: BaseTypeset? = EmptyTypeset

    override fun isDecorateNoneItem() = false

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
    }

    override fun addView(parentView: ViewGroup, child: View) = Unit

}