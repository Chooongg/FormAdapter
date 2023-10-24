package com.chooongg.form.core.part

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseFormPartAdapter(val formAdapter: FormAdapter, val style: BaseStyle) :
    RecyclerView.Adapter<FormViewHolder>() {

    protected var lastEnabled: Boolean = formAdapter.isEnabled

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    protected val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeRemoved(position, count)

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            oldItem.javaClass == newItem.javaClass && oldItem.typeset == newItem.typeset

        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            oldItem.id == newItem.id && lastEnabled == formAdapter.isEnabled
    }).build())

    abstract fun update()

    override fun getItemCount() = asyncDiffer.currentList.size

    fun getItem(position: Int) = asyncDiffer.currentList[position]

    override fun getItemViewType(position: Int) =
        formAdapter.getItemViewType4Pool(style, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val styleLayout = style.onCreateViewHolder(parent)
        val typeset = formAdapter.getTypeset4ItemViewType(viewType)
        val typesetLayout = typeset.onCreateViewHolder(styleLayout ?: parent)
        style.executeAddView(styleLayout, typesetLayout)
        val provider = formAdapter.getProvider4ItemViewType(viewType)
        val itemView = provider.onCreateViewHolder(typesetLayout ?: styleLayout ?: parent)
        typeset.executeAddView(typesetLayout ?: styleLayout, itemView)
        return FormViewHolder(style, typeset, styleLayout, typesetLayout, itemView)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = asyncDiffer.currentList[position]
        style.onBindViewHolder(holder, holder.styleLayout)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType)
            .onBindViewHolder(holder, holder.typesetLayout, item)
        formAdapter.getProvider4ItemViewType(holder.itemViewType)
            .onBindViewHolder(adapterScope, holder, holder.view, item)
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        style.onViewRecycled(holder, holder.styleLayout)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType)
            .onViewRecycled(holder, holder.typesetLayout)
        formAdapter.getProvider4ItemViewType(holder.itemViewType)
            .onViewRecycled(holder, holder.view)
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder) {
        style.onViewAttachedToWindow(holder, holder.styleLayout)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType)
            .onViewAttachedToWindow(holder, holder.typesetLayout)
        formAdapter.getProvider4ItemViewType(holder.itemViewType)
            .onViewAttachedToWindow(holder, holder.view)
    }

    override fun onViewDetachedFromWindow(holder: FormViewHolder) {
        style.onViewDetachedFromWindow(holder, holder.styleLayout)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType)
            .onViewDetachedFromWindow(holder, holder.typesetLayout)
        formAdapter.getProvider4ItemViewType(holder.itemViewType)
            .onViewDetachedFromWindow(holder, holder.view)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        style.createSizeInfo(recyclerView.context)
    }
}