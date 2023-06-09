package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormDivider
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.divider.MaterialDivider

object FormDividerProvider : BaseFormProvider() {
    override fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialDivider(parent.context).apply {
        id = R.id.formInternalContent
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = partAdapter.style.paddingInfo.verticalLocal
            bottomMargin = partAdapter.style.paddingInfo.verticalLocal
        }
    }

    override fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemDivider = item as? FormDivider
        with(holder.getView<MaterialDivider>(R.id.formInternalContent)) {
            if (itemDivider?.matchParentWidth == true) {
                dividerInsetStart = 0
                dividerInsetEnd = 0
                updateLayoutParams<MarginLayoutParams> {
                    marginStart =
                        -(partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal)
                    marginEnd =
                        -(partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal)
                }
            } else {
                dividerInsetStart = partAdapter.style.paddingInfo.horizontalLocal
                dividerInsetEnd = partAdapter.style.paddingInfo.horizontalLocal
                updateLayoutParams<MarginLayoutParams> {
                    marginStart = 0
                    marginEnd = 0
                }
            }
        }
    }

    override fun onBindItemViewForeground(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = null

    override fun onBindItemViewLongClick(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = Unit
}