package com.chooongg.form.core.provider

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormDivider
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.divider.MaterialDivider
import kotlinx.coroutines.CoroutineScope

class FormDividerProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        MaterialDivider(parent.context).apply {
            id = R.id.formInternalContentView
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemDivider = item as? FormDivider
        with(view as MaterialDivider) {
            dividerColor = if (itemDivider?.color != null) {
                itemDivider.color!!.invoke(context)
            } else {
                with(TypedValue()) {
                    context.theme.resolveAttribute(
                        com.google.android.material.R.attr.materialDividerStyle, this, true
                    )
                    context.obtainStyledAttributes(
                        resourceId, intArrayOf(com.google.android.material.R.attr.dividerColor)
                    ).use { it.getColor(0, Color.GRAY) }
                }
            }
            updateLayoutParams<MarginLayoutParams> {
                topMargin = holder.style.insideInfo.middleTop
                bottomMargin = holder.style.insideInfo.middleBottom
                if ((itemDivider?.matchParentStartEdge ?: itemDivider?.matchParentEdge) == true) {
                    dividerInsetStart = 0
                    marginStart =
                        -(holder.style.insideInfo.start - holder.style.insideInfo.middleStart)
                } else {
                    dividerInsetStart = holder.style.insideInfo.middleStart
                    marginStart = 0
                }
                if ((itemDivider?.matchParentEndEdge ?: itemDivider?.matchParentEdge) == true) {
                    dividerInsetEnd = 0
                    marginEnd =
                        -(holder.style.insideInfo.end - holder.style.insideInfo.middleEnd)
                } else {
                    dividerInsetEnd = holder.style.insideInfo.middleEnd
                    marginEnd = 0
                }
            }
        }

    }
}