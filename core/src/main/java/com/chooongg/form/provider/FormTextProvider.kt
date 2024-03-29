package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormUtils
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.formTextAppearance
import com.chooongg.form.item.BaseForm
import com.chooongg.form.style.BaseStyle
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormTextProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return MaterialTextView(parent.context).apply {
            id = R.id.formInternalContentView
            setTextIsSelectable(true)
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
            setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
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
        with(view as MaterialTextView) {
            isEnabled = item.isRealEnable(enabled)
            text = item.getContentText(context, enabled)
            hint = FormUtils.getText(context, item.hint)
                ?: resources.getString(R.string.fromDefaultHintNone)
            gravity = holder.typeset.obtainContentGravity(holder, item)
        }
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
        val tempEnabled = view.isEnabled
        view.isEnabled = false
        if (tempEnabled) view.isEnabled = true
    }
}