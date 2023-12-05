package com.chooongg.form.core.provider

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
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
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlin.math.min

open class FormInputProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        TextInputLayout(
            parent.context, null,
            com.google.android.material.R.attr.textInputOutlinedExposedDropdownMenuStyle
        ).also {
            val editText = MaterialAutoCompleteTextView(
                ContextThemeWrapper(
                    it.context,
                    com.google.android.material.R.style.ThemeOverlay_Material3_AutoCompleteTextView
                )
            ).apply {
                onCreateInputEdit(this)
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
            }
            it.addView(editText)
            onCreateInputLayout(
                it, editText,
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
            it.isHintEnabled = false
            it.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
            it.boxStrokeWidth = 0
            it.boxStrokeWidthFocused = 0
            it.setPaddingRelative(0, 0, 0, 0)
            it.getChildAt(0).updateLayoutParams<LinearLayout.LayoutParams> {
                topMargin = 0
            }
        }

    protected fun onCreateInputLayout(
        layout: TextInputLayout,
        editText: MaterialAutoCompleteTextView,
        start: Int,
        top: Int,
        end: Int,
        bottom: Int
    ) {
        layout.id = R.id.formInternalContentView
        layout.setHintTextAppearance(formTextAppearance(layout, R.attr.formTextAppearanceHint))
        layout.setPrefixTextAppearance(formTextAppearance(layout, R.attr.formTextAppearancePrefix))
        layout.setSuffixTextAppearance(formTextAppearance(layout, R.attr.formTextAppearanceSuffix))
        layout.placeholderTextAppearance =
            formTextAppearance(layout, R.attr.formTextAppearancePlaceholder)
        layout.setCounterTextAppearance(
            formTextAppearance(layout, R.attr.formTextAppearanceCounter)
        )
        layout.setCounterOverflowTextAppearance(
            formTextAppearance(layout, R.attr.formTextAppearanceCounter)
        )
        layout.tag = editText.textColors
        // StartIcon
        layout.findViewById<CheckableImageButton>(
            com.google.android.material.R.id.text_input_start_icon
        ).apply {
            val iconPadding = resources.getDimensionPixelSize(R.dimen.formIconPadding)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            minimumHeight = 0
            minimumWidth = 0
            setPaddingRelative(start, top, iconPadding, bottom)
            val fontHeight = FormUtils.getFontHeight(editText)
            val realWidth = fontHeight + iconPadding + start
            updateLayoutParams<MarginLayoutParams> {
                marginEnd = 0
                width = realWidth
                height = fontHeight + top + bottom
            }
            val a =
                context.obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorControlHighlight))
            val color = a.getColorStateList(0) ?: ColorStateList.valueOf(Color.GRAY)
            a.recycle()
            background = RippleDrawable(color, null, LayerDrawable(arrayOf(ShapeDrawable().apply {
                paint.color = Color.GRAY
                shape = OvalShape()
            })).apply {
                setLayerGravity(0, Gravity.END or Gravity.CENTER_VERTICAL)
                val size = fontHeight + min(iconPadding, start) * 2
                setLayerWidth(0, size)
                setLayerHeight(0, size)
            })
        }
        // EndIcon
        layout.setEndIconTintList(editText.hintTextColors)
        layout.findViewById<CheckableImageButton>(
            com.google.android.material.R.id.text_input_end_icon
        ).apply {
            val iconPadding = resources.getDimensionPixelSize(R.dimen.formIconPadding)
            background = null
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            minimumHeight = 0
            minimumWidth = 0
            setPaddingRelative(iconPadding, top, end, bottom)
            val fontHeight = FormUtils.getFontHeight(editText)
            val realWidth = fontHeight + iconPadding + end
            updateLayoutParams<MarginLayoutParams> {
                marginStart = 0
                width = realWidth
                height = fontHeight + top + top
            }
            editText.setAdapter(
                FormArrayAdapter<CharSequence>(Boundary(start, top, realWidth, bottom))
            )
        }
    }

    protected fun onCreateInputEdit(edit: MaterialAutoCompleteTextView) {
        edit.id = R.id.formInternalContentChildView
        edit.isHorizontalFadingEdgeEnabled = true
        edit.isVerticalFadingEdgeEnabled = true
        edit.setFadingEdgeLength(edit.context.resources.getDimensionPixelSize(R.dimen.formFadingEdgeLength))
        edit.setTextAppearance(formTextAppearance(edit, R.attr.formTextAppearanceContent))
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
            onBindInputLayout(holder, this, item, enabled)
        }
        with(view.findViewById<MaterialAutoCompleteTextView>(R.id.formInternalContentChildView)) {
            onBindInputEdit(holder, this, item, enabled)
            hint = when (itemInput?.placeholder) {
                null -> FormUtils.getText(context, item.hint)
                    ?: if (item.isRealEnable(enabled)) {
                        context.getString(R.string.formDefaultHintInput)
                    } else resources.getString(R.string.fromDefaultHintNone)

                else -> null
            }
        }
        loadOption(holder, item)
    }

    protected fun onBindInputLayout(
        holder: FormViewHolder,
        layout: TextInputLayout,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemInput = item as? FormInput
        layout.isEnabled = enabled
        layout.prefixText = FormUtils.getText(layout.context, itemInput?.prefix)
        layout.suffixText = FormUtils.getText(layout.context, itemInput?.suffix)
        layout.placeholderText = FormUtils.getText(layout.context, itemInput?.placeholder)
        if (itemInput?.showCounter == true) {
            layout.isCounterEnabled = true
            layout.counterMaxLength = itemInput.counterMaxLength ?: itemInput.maxLength ?: -1
            layout.getChildAt(1)
                .updatePadding(top = 0, bottom = holder.style.insideInfo.middleBottom)
        } else layout.isCounterEnabled = false
        val startIcon = holder.style.iconProvider.getDrawable(layout.context, itemInput?.startIcon)
        if (startIcon != null) {
            layout.startIconDrawable = startIcon
            if (itemInput?.startIconTint != null) {
                layout.setStartIconTintList(itemInput.startIconTint!!.invoke(layout.context))
            } else {
                layout.setStartIconTintList(layout.tag as? ColorStateList)
            }
            if (itemInput?.getStartIconOnClickListener() != null) {
                layout.setStartIconOnClickListener {
                    itemInput.getStartIconOnClickListener()!!.invoke(item, layout)
                }
            } else layout.setStartIconOnClickListener(null)
        } else {
            layout.startIconDrawable = null
            layout.setStartIconOnClickListener(null)
        }
    }

    protected fun onBindInputEdit(
        holder: FormViewHolder,
        edit: MaterialAutoCompleteTextView,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemInput = item as? FormInput
        if (edit.tag is TextWatcher) edit.removeTextChangedListener(edit.tag as TextWatcher)
        edit.setText(item.getContentText(edit.context, enabled))
        edit.gravity = holder.typeset.obtainContentGravity(holder, item)
        if (itemInput != null && itemInput.maxLines <= 1) {
            edit.setSingleLine()
        } else {
            edit.minLines = itemInput?.minLines ?: 0
            edit.maxLines = itemInput?.maxLines ?: Int.MAX_VALUE
        }
        if (itemInput != null) {
            edit.inputType = if (itemInput.maxLines <= 1) {
                itemInput.inputMode.getInputType()
            } else itemInput.inputMode.getInputType() or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            edit.transformationMethod = itemInput.inputMode.getTransformationMethod()
            edit.filters = itemInput.inputMode.getFilters().apply {
                if (itemInput.maxLength != null) add(LengthFilter(itemInput.maxLength!!))
            }.toTypedArray()
        } else {
            edit.inputType = InputType.TYPE_CLASS_TEXT
            edit.transformationMethod = null
            edit.filters = arrayOf()
        }
        val watcher = edit.doAfterTextChanged { editable ->
            if (editable.isNullOrEmpty()) {
                changeContentAndNotifyLinkage(holder, item, null)
            } else changeContentAndNotifyLinkage(holder, item, editable)
        }
        edit.tag = watcher
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

    @Suppress("UNCHECKED_CAST")
    protected fun configOption(
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
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
                    if (itemInput?.inputMode?.isNeedPasswordToggle() == true) {
                        endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    } else if (itemInput?.showClearIcon != false) {
                        endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                        endIconDrawable = FormUtils.getIconChangeSize(
                            context, R.drawable.ic_form_close, fontHeight
                        )
                    } else {
                        endIconMode = TextInputLayout.END_ICON_NONE
                        endIconDrawable = null
                    }
                }

                is OptionLoadResult.Wait, is OptionLoadResult.Success -> {
                    TooltipCompat.setTooltipText(this, null)
                    if (enabled && item.options != null) {
                        endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
                        endIconDrawable = FormUtils.getIconChangeSize(
                            context, R.drawable.ic_form_arrow_dropdown, fontHeight
                        )
                    } else if (itemInput.inputMode.isNeedPasswordToggle()) {
                        endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    } else if (itemInput.showClearIcon) {
                        endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                        endIconDrawable = FormUtils.getIconChangeSize(
                            context, R.drawable.ic_form_close, fontHeight
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
                    if (itemInput.inputMode.isNeedPasswordToggle()) {
                        endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    } else if (itemInput.showClearIcon) {
                        endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                        endIconDrawable = FormUtils.getIconChangeSize(
                            context, R.drawable.ic_form_close, fontHeight
                        )
                    } else {
                        endIconMode = TextInputLayout.END_ICON_NONE
                        endIconDrawable = null
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
}