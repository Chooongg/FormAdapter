package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.BaseFormLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantChildDynamicGroup
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormChildDynamicPartAdapter
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class VariantChildDynamicGroupProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup) =
        RecyclerView(parent.context).apply { layoutManager = BaseFormLayoutManager(context) }

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
        if (recyclerView.adapter == null) {
            recyclerView.adapter =
                FormChildDynamicPartAdapter(bindingAdapter.formAdapter, variant.style)
        }
        val adapter = recyclerView.adapter as FormChildDynamicPartAdapter
        adapter.columnCount = variant._column
        adapter.set(variant)
        adapter.update()
    }
}