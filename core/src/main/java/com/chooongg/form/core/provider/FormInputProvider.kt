package com.chooongg.form.core.provider

import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.TooltipCompat
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
import com.chooongg.form.core.option.OptionLoadResult
import com.chooongg.form.core.part.BaseFormPartAdapter
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope

class FormInputProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        TextInputLayout(
            parent.context,
            null,
            com.google.android.material.R.attr.textInputOutlinedExposedDropdownMenuStyle
        ).also {
            it.id = R.id.formInternalContentView
            it.isHintEnabled = false
            it.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
            it.boxStrokeWidth = 0
            it.boxStrokeWidthFocused = 0
            it.setHintTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceHint))
            it.setPrefixTextAppearance(formTextAppearance(it, R.attr.formTextAppearancePrefix))
            it.setSuffixTextAppearance(formTextAppearance(it, R.attr.formTextAppearanceSuffix))
            it.placeholderTextAppearance =
                formTextAppearance(it, R.attr.formTextAppearancePlaceholder)
            it.setPaddingRelative(0, 0, 0, 0)
            val editText = MaterialAutoCompleteTextView(
                ContextThemeWrapper(
                    it.context,
                    com.google.android.material.R.style.ThemeOverlay_Material3_AutoCompleteTextView
                )
            ).apply {
                id = R.id.formInternalContentChildView
                imeOptions = EditorInfo.IME_ACTION_DONE
                isHorizontalFadingEdgeEnabled = true
                isVerticalFadingEdgeEnabled = true
                setFadingEdgeLength(context.resources.getDimensionPixelSize(R.dimen.formFadingEdgeLength))
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
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
                    iconPadding, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
                val fontHeight = FormUtils.getFontHeight(editText)
                val realHeight = fontHeight + iconPadding + style.insideInfo.middleEnd
                updateLayoutParams<MarginLayoutParams> {
                    marginStart = 0
                    width = realHeight
                    height = fontHeight + style.insideInfo.middleTop + style.insideInfo.middleBottom
                }
                editText.setAdapter(
                    FormArrayAdapter<CharSequence>(
                        Boundary(
                            style.insideInfo.middleStart, style.insideInfo.middleTop,
                            realHeight, style.insideInfo.middleBottom
                        )
                    )
                )
            }
            it.getChildAt(0).updateLayoutParams<LinearLayout.LayoutParams> {
                topMargin = 0
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
            isEnabled = item.isRealEnable(enabled)
            prefixText = FormUtils.getText(context, itemInput?.prefix)
            suffixText = FormUtils.getText(context, itemInput?.suffix)
            placeholderText = FormUtils.getText(context, itemInput?.placeholder)
            if (itemInput?.counterMaxLength != null && itemInput.counterMaxLength != Int.MAX_VALUE) {
                isCounterEnabled = true
                counterMaxLength = itemInput.counterMaxLength
                getChildAt(1).updatePadding(top = 0, bottom = holder.style.insideInfo.middleBottom)
            } else isCounterEnabled = false
        }
        with(view.findViewById<MaterialAutoCompleteTextView>(R.id.formInternalContentChildView)) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            hint = when (itemInput?.placeholder) {
                null -> FormUtils.getText(context, item.hint)
                    ?: context.getString(R.string.formDefaultHintInput)

                else -> null
            }
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
        loadOption(holder, item)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(scope, holder, view, item, enabled, payloads)
            return
        }
        if (payloads.contains("changeOption")) {
            configOption(holder, view, item, enabled)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun configOption(holder: FormViewHolder, view: View, item: BaseForm, enabled: Boolean) {
        val editText =
            view.findViewById<MaterialAutoCompleteTextView>(R.id.formInternalContentChildView)
        val itemInput = item as? FormInput
        try {
            val adapter = editText.adapter as FormArrayAdapter<CharSequence>
            adapter.setNewData(itemInput?.options, editText.gravity)
        } catch (_: Exception) {
        }
        val fontHeight = FormUtils.getFontHeight(editText)
        with(view as TextInputLayout) {
            when (val result = itemInput?.optionLoadResult) {
                null -> {
                    TooltipCompat.setTooltipText(this, null)
                    endIconMode = TextInputLayout.END_ICON_NONE
                    endIconDrawable = null
                }

                is OptionLoadResult.Wait, is OptionLoadResult.Success -> {
                    TooltipCompat.setTooltipText(this, null)
                    if (enabled && item.options != null) {
                        endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
                        endIconDrawable = FormUtils.getIconChangeSize(
                            context, R.drawable.ic_form_arrow_down, fontHeight
                        )
                    } else {
                        endIconMode = TextInputLayout.END_ICON_NONE
                        endIconDrawable = null
                    }
                }

                is OptionLoadResult.Loading -> {
                    TooltipCompat.setTooltipText(this, null)
                    val drawable = IndeterminateDrawable.createCircularDrawable(context,
                        CircularProgressIndicatorSpec(context, null).apply {
                            trackThickness = fontHeight / 10
                            indicatorSize = fontHeight / 2
                        })
                    endIconMode = TextInputLayout.END_ICON_CUSTOM
                    endIconDrawable = drawable
                    drawable.start()
                }

                is OptionLoadResult.Error -> {
                    TooltipCompat.setTooltipText(this, result.e.message)
                    endIconMode = TextInputLayout.END_ICON_CUSTOM
                    endIconDrawable = FormUtils.getIconChangeSize(
                        context, R.drawable.ic_form_error, fontHeight
                    )
                    setEndIconOnClickListener { loadOption(holder, item) }
                }

                is OptionLoadResult.Empty -> {
                    TooltipCompat.setTooltipText(this, null)
                    endIconMode = TextInputLayout.END_ICON_NONE
                    endIconDrawable = null
                }
            }
        }
    }

    private fun loadOption(holder: FormViewHolder, item: BaseForm?) {
        val itemOption = item as? BaseOptionForm<*>
        if (itemOption?.isNeedToLoadOption(holder) == true) {
            val adapter = holder.bindingAdapter as? BaseFormPartAdapter ?: return
            item.loadOption(holder) {
                holder.itemView.post {
                    val position = adapter.indexOf(item)
                    if (position != null) {
                        adapter.notifyItemChanged(position, "changeOption")
                    }
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        val tempEnabled = view.isEnabled
        view.isEnabled = false
        if (tempEnabled) view.isEnabled = true
    }

    override fun onViewRecycled(holder: FormViewHolder, view: View) {
        super.onViewRecycled(holder, view)
    }
}