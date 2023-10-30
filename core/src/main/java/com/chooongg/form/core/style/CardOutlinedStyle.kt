package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.InternalFormPartName

class CardOutlinedStyle:BaseCardStyle {

    constructor() : super()
    constructor(block: CardOutlinedStyle.() -> Unit) : super() {
        block(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? {
        TODO("Not yet implemented")
    }

    override fun onCreatePartName(parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun onBindPartName(holder: FormViewHolder, view: View, item: InternalFormPartName) {
        TODO("Not yet implemented")
    }

    override fun addView(parentView: ViewGroup, child: View) {
        TODO("Not yet implemented")
    }


}