package com.chooongg.form.provider

import android.provider.SyncStateContract.Helpers.update
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.BaseFormLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantBaseForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormChildPartAdapter
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class VariantFormProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        RecyclerView(parent.context).apply {
            id = R.id.formInternalVariantView
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
                parentAdapter = bindingAdapter
                columnCount = variant._column
                set(variant)
            }, false
        )
    }
}