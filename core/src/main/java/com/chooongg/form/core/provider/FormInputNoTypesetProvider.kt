package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormInputNoTypesetProvider : BaseFormProvider() {
    override fun onCreateViewHolder(parent: ViewGroup): View {
        return TextInputLayout(parent.context).also {
            it.id = R.id.formInternalContentView
//            it.isHintEnabled = false
//            it.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
            it.setHintTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceHint))
            it.setPrefixTextAppearance(formTextAppearance(it, R.attr.formTextAppearancePrefix))
            it.setSuffixTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceSuffix))
            it.placeholderTextAppearance =
                formTextAppearance(it, R.attr.formTextAppearancePlaceholder)
            it.layoutParams = ViewGroup.MarginLayoutParams(-1, -2)
            it.addView(TextInputEditText(it.context).apply {
                id = R.id.formInternalContentChildView
            })
        }
    }

    override fun onBindViewHolder(holder: FormViewHolder, view: View, item: BaseForm) {
        with(view.findViewById<TextInputLayout>(R.id.formInternalContentView)) {
            hint = item.getHintString(context)
        }
        with(view.findViewById<TextInputEditText>(R.id.formInternalContentChildView)) {
            setText(item.getContentString(context))
        }
    }
}