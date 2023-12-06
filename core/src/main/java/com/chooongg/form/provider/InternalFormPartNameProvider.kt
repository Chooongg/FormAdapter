package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.InternalFormGroupName
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class InternalFormPartNameProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return style.partNameProvider.onCreateGroupName(style, parent)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        holder.style.partNameProvider.onBindGroupName(
            holder, view, item as InternalFormGroupName, enabled
        )
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        view.isEnabled = false
        view.isEnabled = true
    }
}