package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class FormSpaceProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        View(parent.context)

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) = Unit
}