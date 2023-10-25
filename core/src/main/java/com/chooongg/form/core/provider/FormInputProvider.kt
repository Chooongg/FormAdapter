package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormInput
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope

class FormInputProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return TextInputLayout(parent.context).also {
            it.id = R.id.formInternalContentView
            it.isHintEnabled = false
            it.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
            it.setHintTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceHint))
            it.setPrefixTextAppearance(formTextAppearance(it, R.attr.formTextAppearancePrefix))
            it.setSuffixTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceSuffix))
            it.placeholderTextAppearance =
                formTextAppearance(it, R.attr.formTextAppearancePlaceholder)
            it.addView(TextInputEditText(it.context).apply {
                id = R.id.formInternalContentChildView
                imeOptions = EditorInfo.IME_ACTION_DONE
                isHorizontalFadingEdgeEnabled = true
                isVerticalFadingEdgeEnabled = true
                setFadingEdgeLength(context.resources.getDimensionPixelSize(R.dimen.formFadingEdgeLength))
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
        val itemInput = item as? FormInput
        with(view as TextInputLayout) {
            placeholderText = itemInput?.placeholder
            prefixText = "测试"
            suffixText = "测试"
        }
        with(view.findViewById<TextInputEditText>(R.id.formInternalContentChildView)) {
            hint = if (itemInput?.placeholder != null) null else item.getHintString(context)
            setText(item.getContentString(context))
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        val tempEnabled = view.isEnabled
        view.isEnabled = false
        if (tempEnabled) view.isEnabled = true
    }
}