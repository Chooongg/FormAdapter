package com.chooongg.form.core.part

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseFormItem
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseFormPartAdapter(val formAdapter: FormAdapter, val style: BaseStyle) :
    RecyclerView.Adapter<FormViewHolder>() {

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeRemoved(position, count)

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseFormItem>() {
        override fun areItemsTheSame(oldItem: BaseFormItem, newItem: BaseFormItem) =
            oldItem.javaClass == newItem.javaClass &&
                    (oldItem.typeset ?: FormManager.Default.typeset) ==
                    (newItem.typeset ?: FormManager.Default.typeset)

        override fun areContentsTheSame(oldItem: BaseFormItem, newItem: BaseFormItem) =
            oldItem.id == newItem.id
    }).build())

    protected var data: List<List<BaseFormItem>>? = null

    abstract fun update()

    override fun getItemCount() = asyncDiffer.currentList.size

    fun getItem(position: Int) = asyncDiffer.currentList[position]

    override fun getItemViewType(position: Int) =
        formAdapter.getItemViewType4Pool(style, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }
}