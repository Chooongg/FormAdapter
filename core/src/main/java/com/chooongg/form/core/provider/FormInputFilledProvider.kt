package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope

class FormInputFilledProvider : BaseFormProvider() {
    override fun onCreateViewHolder(parent: ViewGroup): View {
        return TextInputLayout(
            parent.context, null, com.google.android.material.R.attr.textInputFilledStyle
        ).also {
            it.id = R.id.formInternalContentView
            it.setHintTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceHint))
            it.setPrefixTextAppearance(formTextAppearance(it, R.attr.formTextAppearancePrefix))
            it.setSuffixTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceSuffix))
            it.placeholderTextAppearance =
                formTextAppearance(it, R.attr.formTextAppearancePlaceholder)
            it.layoutParams = ViewGroup.MarginLayoutParams(-1, -2)
            it.addView(TextInputEditText(it.context).apply {
                id = R.id.formInternalContentChildView
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
            })
        }
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm
    ) {
        with(view.findViewById<TextInputLayout>(R.id.formInternalContentView)) {
            hint = item.getHintString(context)
        }
        with(view.findViewById<TextInputEditText>(R.id.formInternalContentChildView)) {
            setText(item.getContentString(context))
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        val tempEnabled = view.isEnabled
        view.isEnabled = false
        if (tempEnabled) view.isEnabled = true
    }
}