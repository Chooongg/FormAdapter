package com.chooongg.form.typeset

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.FormInsideInfo
import com.chooongg.form.enum.FormEmsMode
import com.chooongg.form.item.BaseForm
import com.chooongg.form.style.BaseStyle

class EmptyTypeset : BaseTypeset() {

    override var emsMode = FormEmsMode.NONE
    override var multiColumnEmsMode: FormEmsMode = FormEmsMode.NONE

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup? = null

    override fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup,
        item: BaseForm,
        enabled: Boolean
    ) = Unit

    override fun addView(style: BaseStyle, parentView: ViewGroup, child: View) = Unit

    override fun setTypesetLayoutPadding(
        holder: FormViewHolder,
        layout: ViewGroup?,
        insideInfo: FormInsideInfo,
        item: BaseForm
    ) = Unit
}