package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.typeset.BaseTypeset

class CardStyle(typeset: BaseTypeset? = null) : BaseStyle(typeset) {

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?) {
        holder.itemView.clipToOutline = true
    }

    override fun addView(parentView: ViewGroup, child: View) {
    }
}