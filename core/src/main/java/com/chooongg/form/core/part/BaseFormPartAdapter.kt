package com.chooongg.form.core.part

import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.abstractItem.BaseFormItem
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseFormPartAdapter(val style: BaseStyle) : RecyclerView.Adapter<FormViewHolder>() {

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    protected var data: List<List<BaseFormItem>>? = null

    abstract fun update()


}