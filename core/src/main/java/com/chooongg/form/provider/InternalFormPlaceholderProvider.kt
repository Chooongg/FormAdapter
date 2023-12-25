package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.part.PlaceholderPartAdapter
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlin.math.max

class InternalFormPlaceholderProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        View(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -2)
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val adapter = holder.bindingAdapter as? PlaceholderPartAdapter
        view.updateLayoutParams<ViewGroup.LayoutParams> {
            height = if (adapter == null) {
                max(
                    holder.style.marginInfo.top - holder.style.marginInfo.middleTop,
                    holder.style.marginInfo.bottom - holder.style.marginInfo.middleBottom
                )
            } else if (adapter.isTop) {
                holder.style.marginInfo.top - holder.style.marginInfo.middleTop
            } else {
                holder.style.marginInfo.bottom - holder.style.marginInfo.middleBottom
            }
        }
    }
}