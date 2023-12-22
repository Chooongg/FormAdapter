package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.BaseFormLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantChildDynamicGroup
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormChildDynamicPartAdapter
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NegativePaddingStyle
import kotlinx.coroutines.CoroutineScope

class VariantChildDynamicGroupProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup) =
        RecyclerView(parent.context).apply {
            setPaddingRelative(style.marginInfo.start, 0, style.marginInfo.end, 0)
            id = R.id.formInternalChildDynamicGroupView
            clipChildren = false
            clipToPadding = false
            isNestedScrollingEnabled = false
            layoutManager = object : BaseFormLayoutManager(context) {
                override fun canScrollVertically() = false
                override fun canScrollHorizontally() = false
            }
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val variant = item as? VariantChildDynamicGroup
        val recyclerView = view as RecyclerView
        val bindingAdapter = holder.bindingAdapter as? BaseFormPartAdapter
        if (variant == null || bindingAdapter == null) {
            recyclerView.adapter = null
            return
        }
        if (recyclerView.recycledViewPool != bindingAdapter.formAdapter.recycledPool) {
            recyclerView.setRecycledViewPool(bindingAdapter.formAdapter.recycledPool)
        }
        recyclerView.swapAdapter(
            FormChildDynamicPartAdapter(bindingAdapter.formAdapter, variant.style).apply {
                columnCount = variant._column
                parentAdapter = bindingAdapter
                parentBoundary = Boundary(
                    if (item.positionInGroup == 0) Boundary.GLOBAL else item.marginBoundary.start,
                    if (item.style is NegativePaddingStyle) Boundary.NONE else Boundary.MIDDLE,
                    if (item.positionInGroup == item.countInGroup - 1) Boundary.GLOBAL else item.marginBoundary.end,
                    if (item.style is NegativePaddingStyle) Boundary.NONE else Boundary.MIDDLE
                )
                set(variant)
            }, false
        )
    }
}