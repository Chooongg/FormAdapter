package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.textview.MaterialTextView

class FormTextProvider : BaseFormProvider() {
    override fun onCreateViewHolder(parent: ViewGroup): View {
        return MaterialTextView(parent.context)
    }

    override fun onBindViewHolder(holder: FormViewHolder, view: View, item: BaseForm) {
        with(view as MaterialTextView) {
            text = item.content?.toString()
        }
    }
}