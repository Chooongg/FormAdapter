package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.InternalFormPartName
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class InternalFormPartNameProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return style.onCreatePartName(parent)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        holder.style.onBindPartName(holder, view, item as InternalFormPartName)
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        view.isEnabled = false
        view.isEnabled = true
    }
}