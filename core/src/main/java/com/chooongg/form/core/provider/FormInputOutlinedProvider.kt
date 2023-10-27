package com.chooongg.form.core.provider

import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormInput
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope

class FormInputOutlinedProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return TextInputLayout(
            parent.context, null, com.google.android.material.R.attr.textInputOutlinedStyle
        ).also {
            it.clipChildren = false
            it.clipToPadding = false
            it.id = R.id.formInternalContentView
            it.setHintTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceHint))
            it.setPrefixTextAppearance(formTextAppearance(it, R.attr.formTextAppearancePrefix))
            it.setSuffixTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceSuffix))
            it.placeholderTextAppearance =
                formTextAppearance(it, R.attr.formTextAppearancePlaceholder)
            it.setPaddingRelative(
                style.insideInfo.start-style.insideInfo.middleStart,
                style.insideInfo.top-style.insideInfo.middleTop,
                style.insideInfo.end-style.insideInfo.middleEnd,
                style.insideInfo.bottom-style.insideInfo.middleBottom
            )
            val editText = TextInputEditText(it.context).apply {
                id = R.id.formInternalContentChildView
                imeOptions = EditorInfo.IME_ACTION_DONE
                isHorizontalFadingEdgeEnabled = true
                isVerticalFadingEdgeEnabled = true
                setFadingEdgeLength(context.resources.getDimensionPixelSize(R.dimen.formFadingEdgeLength))
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
            }
            it.addView(editText)
            it.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            it.setEndIconTintList(editText.hintTextColors)
            it.endIconMinSize =
                FormUtils.getFontHeight(editText) + style.insideInfo.middleTop + style.insideInfo.middleBottom
            it.setEndIconDrawable(R.drawable.ic_form_close)
            it.getChildAt(0).updateLayoutParams<LinearLayout.LayoutParams> {
                topMargin = 0
            }
        }
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemInput = item as? FormInput
        with(view as TextInputLayout) {
            isEnabled = item.isRealEnable(enabled)
            hint = FormUtils.getText(context, item.hint)
            prefixText = FormUtils.getText(context, itemInput?.prefix)
            suffixText = FormUtils.getText(context, itemInput?.suffix)
            placeholderText = FormUtils.getText(context, itemInput?.placeholder)
            if (itemInput?.counterMaxLength != null && itemInput.counterMaxLength != Int.MAX_VALUE) {
                isCounterEnabled = true
                counterMaxLength = itemInput.counterMaxLength
                getChildAt(1).updatePadding(top = 0, bottom = 0)
            } else isCounterEnabled = false
        }
        with(view.findViewById<TextInputEditText>(R.id.formInternalContentChildView)) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            setText(FormUtils.getText(context, item.content))
            gravity = holder.typeset.obtainContentGravity(item)
            if (itemInput != null && itemInput.maxLines <= 1) {
                setSingleLine()
            } else {
                minLines = itemInput?.minLines ?: 0
                maxLines = itemInput?.maxLines ?: Int.MAX_VALUE
            }
            val watcher = doAfterTextChanged { editable ->
                changeContentAndNotifyLinkage(holder, item, editable)
            }
            tag = watcher
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        val tempEnabled = view.isEnabled
        view.isEnabled = false
        if (tempEnabled) view.isEnabled = true
    }
}