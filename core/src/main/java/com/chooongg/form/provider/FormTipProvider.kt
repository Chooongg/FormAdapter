package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import androidx.core.view.updatePaddingRelative
import com.chooongg.form.FormUtils
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.formTextAppearance
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.FormTip
import com.chooongg.form.style.BaseStyle
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormTipProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        MaterialTextView(parent.context).apply {
            id = R.id.formInternalContentView
            setTextIsSelectable(true)
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceTip))
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
        val itemTip = item as? FormTip
        with(view as MaterialTextView) {
            text = FormUtils.getText(context, item.name)
            //  (如果不想使用默认色调, 请实现Block返回null)
            setTextColor(
                itemTip?.color?.invoke(context) ?: context.obtainStyledAttributes(
                    formTextAppearance(this, R.attr.formTextAppearanceTip),
                    intArrayOf(android.R.attr.textColor)
                ).use { it.getColorStateList(0) }
            )
            gravity = holder.typeset.obtainContentGravity(holder, item)
            updatePaddingRelative(
                top = if (itemTip?.enableTopPadding == true) holder.style.insideInfo.middleTop else 0,
                bottom = if (itemTip?.enableBottomPadding == true) holder.style.insideInfo.middleBottom else 0
            )
        }
    }
}