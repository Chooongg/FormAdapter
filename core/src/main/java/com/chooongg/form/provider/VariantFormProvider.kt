package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.BaseFormLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.data.FormPartData
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormPartAdapter
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class VariantFormProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        RecyclerView(parent.context).apply {
            layoutManager = BaseFormLayoutManager(context)
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemVariant = item as? VariantForm
        val recyclerView = view as RecyclerView
        val bindingAdapter = holder.bindingAdapter as? BaseFormPartAdapter
        if (itemVariant == null || bindingAdapter == null) {
            recyclerView.adapter = null
            return
        }
        if (recyclerView.adapter == null) {
            recyclerView.adapter = FormPartAdapter(bindingAdapter.formAdapter, itemVariant.style)
        }
        val adapter = recyclerView.adapter as FormPartAdapter
        adapter.columnCount = itemVariant._column
        val data = FormPartData()
        data.isEnablePart = item.isRealEnable(enabled)
        data.partName = item.name
        data.partField = item.field
        data.menu = item.menu
        data.menuEnableMode = item.menuEnableMode
        data.menuVisibilityMode = item.menuVisibilityMode
        data.setOnMenuCreateOptionCallback(item.getMenuCreateOptionCallback())
        data.getItems().addAll(itemVariant.getItems())
        adapter.create(data)
        adapter.update()
    }
}