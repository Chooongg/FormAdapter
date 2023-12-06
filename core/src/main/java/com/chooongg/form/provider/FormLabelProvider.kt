package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import com.chooongg.form.FormUtils
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.formTextAppearance
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.FormLabel
import com.chooongg.form.style.BaseStyle
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormLabelProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        MaterialTextView(parent.context).apply {
            id = R.id.formInternalContentView
            setTextIsSelectable(true)
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceLabel))
            setPaddingRelative(
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
        val itemLabel = item as? FormLabel
        with(view as MaterialTextView) {
            text = FormUtils.getText(context, item.name)
            setTextColor(
                itemLabel?.color?.invoke(context)
                    ?: context.obtainStyledAttributes(
                        formTextAppearance(this, R.attr.formTextAppearanceLabel),
                        intArrayOf(android.R.attr.textColor)
                    ).use { it.getColorStateList(0) }
            )
            gravity = holder.typeset.obtainContentGravity(holder, item)
        }
    }
}