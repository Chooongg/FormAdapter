package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormLabel
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormLabelProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return MaterialTextView(parent.context).apply {
            setTextIsSelectable(true)
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceLabel))
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
        val itemLabel = item as? FormLabel
        with(view as MaterialTextView) {
            text = FormUtils.getText(context, item.name)
            if (itemLabel?.color != null) {
                setTextColor(itemLabel.color!!.invoke(context))
            } else {
                setTextColor(context.obtainStyledAttributes(
                    formTextAppearance(this, R.attr.formTextAppearanceLabel),
                    intArrayOf(android.R.attr.textColor)
                ).use { it.getColorStateList(0) })
            }
        }
    }
}