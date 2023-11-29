package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.materialswitch.MaterialSwitch
import kotlinx.coroutines.CoroutineScope

class FormSwitchProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        FrameLayout(parent.context).also {
            it.id = R.id.formInternalContentView
            it.addView(MaterialSwitch(it.context).apply {
                id = R.id.formInternalContentChildView
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
                minimumHeight = 0
                minHeight = 0
            }, FrameLayout.LayoutParams(-2, -2))
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        with(view.findViewById<MaterialSwitch>(R.id.formInternalContentChildView)) {
            isEnabled = enabled
            setOnCheckedChangeListener(null)
            isChecked = item.content as? Boolean ?: false
            setOnCheckedChangeListener { _, isCheck ->
                changeContentAndNotifyLinkage(holder, item, isCheck)
            }
            updateLayoutParams<FrameLayout.LayoutParams> {
                gravity = holder.typeset.obtainContentGravity(holder, item)
            }
        }
    }
}