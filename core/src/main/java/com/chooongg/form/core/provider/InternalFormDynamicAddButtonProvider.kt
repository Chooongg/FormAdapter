package com.chooongg.form.core.provider

import android.view.View
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.part.FormDynamicPartAdapter

class InternalFormDynamicAddButtonProvider : FormButtonProvider() {

    override fun configButtonClick(holder: FormViewHolder, view: View, item: BaseForm) {
        view.setOnClickListener {
            val adapter = holder.bindingAdapter as? FormDynamicPartAdapter
            adapter?.onItemAddClick(item)
        }
    }
}