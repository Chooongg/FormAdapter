package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormTextProvider : BaseFormProvider() {
    override fun onCreateViewHolder(parent: ViewGroup): View {
        return MaterialTextView(parent.context).apply {
            id = R.id.formInternalContentView
            setTextIsSelectable(true)
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
            layoutParams = ViewGroup.MarginLayoutParams(-1, -2)
        }
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm
    ) {
        with(view as MaterialTextView) {
            text = item.content?.toString()
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        val tempEnabled = view.isEnabled
        view.isEnabled = false
        if (tempEnabled) view.isEnabled = true
    }
}