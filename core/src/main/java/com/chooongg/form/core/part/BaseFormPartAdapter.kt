package com.chooongg.form.core.part

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
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

    val itemList get() = asyncDiffer.currentList

    abstract fun update()

    abstract fun findOfField(
        field: String,
        update: Boolean = true,
        block: (BaseForm) -> Unit
    ): Boolean

    override fun getItemCount() = itemList.size

    fun getItem(position: Int) = itemList[position]

    override fun getItemViewType(position: Int): Int {
        return formAdapter.getItemViewType4Pool(style, getItem(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val styleLayout = style.onCreateViewHolder(parent)?.apply {
            clipChildren = false
            clipToPadding = false
        }
        val typeset = formAdapter.getTypeset4ItemViewType(viewType)
        val typesetLayout = typeset.onCreateViewHolder(style, styleLayout ?: parent)?.apply {
            clipChildren = false
            clipToPadding = false
        }
        style.executeAddView(styleLayout, typesetLayout)
        val provider = formAdapter.getProvider4ItemViewType(viewType)
        val itemView = provider.onCreateViewHolder(style, typesetLayout ?: styleLayout ?: parent)
        typeset.executeAddView(style, typesetLayout ?: styleLayout, itemView)
        val view = styleLayout ?: typesetLayout ?: itemView
        view.layoutParams =
            if (view.layoutParams != null) GridLayoutManager.LayoutParams(view.layoutParams!!)
            else GridLayoutManager.LayoutParams(-1, -2)
        return FormViewHolder(style, typeset, styleLayout, typesetLayout, itemView)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = getItem(position)
        style.onBindViewHolder(holder, holder.styleLayout, item)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType).apply {
            setTypesetLayoutPadding(holder, holder.typesetLayout, style.insideInfo, item)
            onBindViewHolder(holder, holder.typesetLayout, item)
        }
        formAdapter.getProvider4ItemViewType(holder.itemViewType).apply {
            onBindViewHolder(adapterScope, holder, holder.view, item, formAdapter.isEnabled)
        }
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position)
        style.onBindViewHolder(holder, holder.styleLayout, item)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType).apply {
            setTypesetLayoutPadding(holder, holder.typesetLayout, style.insideInfo, item)
            onBindViewHolder(holder, holder.typesetLayout, item)
        }
        formAdapter.getProvider4ItemViewType(holder.itemViewType).apply {
            onBindViewHolder(
                adapterScope, holder, holder.view, item, formAdapter.isEnabled, payloads
            )
        }
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
        style.createSizeInfo(recyclerView)
    }
}