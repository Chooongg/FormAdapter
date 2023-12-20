package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.InternalFormGroupName
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class InternalFormGroupNameProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return style.groupNameProvider.onCreateGroupName(style, parent)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        holder.style.groupNameProvider.onBindGroupName(
            holder, view, item as InternalFormGroupName, enabled
        )
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        view.isEnabled = false
        view.isEnabled = true
    }
}