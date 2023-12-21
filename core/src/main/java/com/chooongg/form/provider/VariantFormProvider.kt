package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.BaseFormLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantBaseForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormChildPartAdapter
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NegativePaddingStyle
import kotlinx.coroutines.CoroutineScope

class VariantFormProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        RecyclerView(parent.context).apply {
            setPaddingRelative(style.marginInfo.start, 0, style.marginInfo.end, 0)
            id = R.id.formInternalVariantView
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
        val variant = item as? VariantBaseForm
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
            FormChildPartAdapter(bindingAdapter.formAdapter, item.style).apply {
                columnCount = variant._column
                parentAdapter = bindingAdapter
                val boundary = Boundary()
                boundary.start = if (item.positionInGroup == 0) {
                    Boundary.GLOBAL
                } else item.marginBoundary.start
                boundary.end = if (item.positionInGroup == item.countInGroup - 1) {
                    Boundary.GLOBAL
                } else item.marginBoundary.end
                boundary.top = if (item.style !is NegativePaddingStyle) {
                    0
                } else bindingAdapter.parentBoundary.top
                boundary.bottom = if (item.style !is NegativePaddingStyle) {
                    0
                } else bindingAdapter.parentBoundary.bottom
                parentBoundary = boundary
                set(variant)
            }, false
        )
    }
}