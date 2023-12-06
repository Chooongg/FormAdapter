package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.updateLayoutParams
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.BaseOptionForm
import com.chooongg.form.style.BaseStyle
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope

class FormInputOutlinedProvider : FormInputProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        TextInputLayout(
            parent.context, null,
            com.google.android.material.R.attr.textInputOutlinedExposedDropdownMenuStyle
        ).also {
            val editText = MaterialAutoCompleteTextView(
                ContextThemeWrapper(
                    it.context,
                    com.google.android.material.R.style.ThemeOverlay_Material3_AutoCompleteTextView_OutlinedBox
                )
            ).apply { onCreateInputEdit(this) }
            it.addView(editText)
            onCreateInputLayout(
                it, editText,
                style.insideInfo.start, style.insideInfo.top,
                style.insideInfo.end, style.insideInfo.bottom
            )
            it.getChildAt(0).updateLayoutParams<LinearLayout.LayoutParams> { topMargin = 0 }
            it.setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        configOption(holder, view, item, enabled)
        with(view as TextInputLayout) {
            onBindInputLayout(holder, this, item, enabled)
            hint = holder.typeset.obtainNameFormatter().format(context, item)
        }
        with(view.findViewById<MaterialAutoCompleteTextView>(R.id.formInternalContentChildView)) {
            onBindInputEdit(holder, this, item, enabled)
        }
        loadOption(holder, item)
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