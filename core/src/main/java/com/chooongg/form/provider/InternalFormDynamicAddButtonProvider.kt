package com.chooongg.form.provider

import android.view.View
import com.chooongg.form.FormUtils
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.InternalFormDynamicAddButton
import com.chooongg.form.part.FormDynamicPartAdapter
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope

class InternalFormDynamicAddButtonProvider : FormButtonProvider() {

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        super.onBindViewHolder(scope, holder, view, item, enabled)
        val itemButton = item as? InternalFormDynamicAddButton
        with(view.findViewById<MaterialButton>(R.id.formInternalContentView)) {
            val name = FormUtils.getText(context, item.name)
            text = itemButton?.dynamicGroupNameFormatter?.invoke(
                context, name, item.groupIndex, item.groupCount + 1
            ) ?: name
        }
    }

    override fun configButtonClick(holder: FormViewHolder, view: View, item: BaseForm) {
        view.setOnClickListener {
            val itemButton = item as? InternalFormDynamicAddButton
            itemButton?.addBlock?.invoke()
        }
    }
}