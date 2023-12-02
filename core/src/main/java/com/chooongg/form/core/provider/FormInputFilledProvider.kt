package com.chooongg.form.core.provider

import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.BaseOptionForm
import com.chooongg.form.core.item.FormInput
import com.chooongg.form.core.option.FormArrayAdapter
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope

class FormInputFilledProvider : FormInputProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        TextInputLayout(
            parent.context,
            null,
            com.google.android.material.R.attr.textInputFilledExposedDropdownMenuStyle
        ).also {
            it.id = R.id.formInternalContentView
            it.setHintTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceHint))
            it.setPrefixTextAppearance(formTextAppearance(it, R.attr.formTextAppearancePrefix))
            it.setSuffixTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceSuffix))
            it.placeholderTextAppearance =
                formTextAppearance(it, R.attr.formTextAppearancePlaceholder)
            it.setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
            val editText = MaterialAutoCompleteTextView(
                ContextThemeWrapper(
                    it.context,
                    com.google.android.material.R.style.ThemeOverlay_Material3_AutoCompleteTextView_FilledBox
                )
            ).apply {
                id = R.id.formInternalContentChildView
                imeOptions = EditorInfo.IME_ACTION_DONE
                isHorizontalFadingEdgeEnabled = true
                isVerticalFadingEdgeEnabled = true
                setFadingEdgeLength(context.resources.getDimensionPixelSize(R.dimen.formFadingEdgeLength))
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
            }
            it.addView(editText)
            it.setEndIconTintList(editText.hintTextColors)
            it.findViewById<CheckableImageButton>(
                com.google.android.material.R.id.text_input_end_icon
            ).apply {
                val iconPadding = context.resources.getDimensionPixelSize(R.dimen.formIconPadding)
                background = null
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                minimumHeight = 0
                minimumWidth = 0
                setPaddingRelative(
                    iconPadding, style.insideInfo.top,
                    style.insideInfo.end, style.insideInfo.bottom
                )
                val fontHeight = FormUtils.getFontHeight(editText)
                val realHeight = fontHeight + iconPadding + style.insideInfo.end
                updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    marginStart = 0
                    width = fontHeight + iconPadding + style.insideInfo.end
                    height = fontHeight + style.insideInfo.top + style.insideInfo.bottom
                }
                editText.setAdapter(
                    FormArrayAdapter<CharSequence>(
                        Boundary(
                            style.insideInfo.start, style.insideInfo.middleTop,
                            realHeight, style.insideInfo.middleBottom
                        )
                    )
                )
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
        configOption(holder, view, item, enabled)
        with(view as TextInputLayout) {
            isEnabled = enabled
            prefixText = FormUtils.getText(context, itemInput?.prefix)
            suffixText = FormUtils.getText(context, itemInput?.suffix)
            placeholderText = FormUtils.getText(context, itemInput?.placeholder)
            hint = holder.typeset.obtainNameFormatter().format(context, item)
            if (itemInput?.counterMaxLength != null && itemInput.counterMaxLength != Int.MAX_VALUE) {
                isCounterEnabled = true
                counterMaxLength = itemInput.counterMaxLength
                getChildAt(1).updatePadding(top = 0, bottom = holder.style.insideInfo.middleBottom)
            } else isCounterEnabled = false
        }
        with(view.findViewById<MaterialAutoCompleteTextView>(R.id.formInternalContentChildView)) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            setText(item.getContentText(context, enabled))
            gravity = holder.typeset.obtainContentGravity(holder, item)
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
        if (enabled) loadOption(holder, item)
    }

    override fun onBindViewHolderOtherPayload(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean,
        payload: Any
    ) {
        if (payload == BaseOptionForm.CHANGE_OPTION_PAYLOAD_FLAG) {
            configOption(holder, view, item, enabled)
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        val tempEnabled = view.isEnabled
        view.isEnabled = false
        if (tempEnabled) view.isEnabled = true
    }
}