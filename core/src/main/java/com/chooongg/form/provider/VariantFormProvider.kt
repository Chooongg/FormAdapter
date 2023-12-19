package com.chooongg.form.provider

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.BaseFormLayoutManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.VariantBaseForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormPartAdapter
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

class VariantFormProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        RecyclerView(parent.context).apply {
            id = R.id.formInternalVariantView
            isNestedScrollingEnabled = false
            layoutManager = BaseFormLayoutManager(context)
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
            FormPartAdapter(bindingAdapter.formAdapter, item.style).apply {
                columnCount = item._column
                data.getItems().clear()
                data.isEnablePart = true
                data.partName = item.name
                data.partField = item.field
                data.menu = item.menu
                data.menuEnableMode = item.menuEnableMode
                data.menuVisibilityMode = item.menuVisibilityMode
                data.isHasDeleteConfirm = item.isHasDeleteConfirm
                data.dynamicGroupDeletingBlock = item.dynamicGroupDeletingBlock
                data.setOnMenuCreateOptionCallback(item.getMenuCreateOptionCallback())
                data.getItems().addAll(variant.getItems())
                create(data)
                update()
            },true
        )
//        val adapter = recyclerView.adapter as FormPartAdapter
//        adapter.columnCount = item._column
//        val data = adapter.data
//        data.getItems().clear()
//        data.isEnablePart = true
//        data.partName = item.name
//        data.partField = item.field
//        data.menu = item.menu
//        data.menuEnableMode = item.menuEnableMode
//        data.menuVisibilityMode = item.menuVisibilityMode
//        data.isHasDeleteConfirm = item.isHasDeleteConfirm
//        data.dynamicGroupDeletingBlock = item.dynamicGroupDeletingBlock
//        data.setOnMenuCreateOptionCallback(item.getMenuCreateOptionCallback())
//        data.getItems().addAll(variant.getItems())
//        adapter.create(data)
//        adapter.update()
    }
}