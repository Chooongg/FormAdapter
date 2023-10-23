package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.utils.ext.resDimensionPixelSize
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormInputProvider : BaseFormProvider() {
    override fun onCreateViewHolder(parent: ViewGroup): View {
        return TextInputLayout(parent.context).also {
            it.id = R.id.formInternalContentView
            it.isExpandedHintEnabled = false
            it.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
            it.setHintTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceHint))
            it.setPrefixTextAppearance(formTextAppearance(it, R.attr.formTextAppearancePrefix))
            it.setSuffixTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceSuffix))
            it.placeholderTextAppearance =
                formTextAppearance(it, R.attr.formTextAppearancePlaceholder)
            it.layoutParams = ViewGroup.MarginLayoutParams(-1, -2)
            it.addView(TextInputEditText(it.context).apply {
                id = R.id.formInternalContentChildView
                imeOptions = EditorInfo.IME_ACTION_DONE
                isHorizontalFadingEdgeEnabled = true
                isVerticalFadingEdgeEnabled = true
                setFadingEdgeLength(resDimensionPixelSize(R.dimen.formFadingEdgeLength))
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
            })
        }
    }

    override fun onBindViewHolder(holder: FormViewHolder, view: View, item: BaseForm) {
        with(view as TextInputLayout) {
//            placeholderText = "测试"
        }
        with(view.findViewById<TextInputEditText>(R.id.formInternalContentChildView)) {
            hint = item.getHintString(context)
            setText(item.getContentString(context))
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        with(view as TextInputLayout) {
            val tempEnabled = isEnabled
            isEnabled = false
            if (tempEnabled) isEnabled = true
        }
    }
}