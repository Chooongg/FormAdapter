package com.chooongg.form.provider

import android.view.View
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.BaseForm
import com.chooongg.form.part.FormDynamicPartAdapter

class InternalFormDynamicAddButtonProvider : FormButtonProvider() {

    override fun configButtonClick(holder: FormViewHolder, view: View, item: BaseForm) {
        view.setOnClickListener {
            val adapter = holder.bindingAdapter as? FormDynamicPartAdapter
            adapter?.onItemAddClick(item)
        }
    }
}