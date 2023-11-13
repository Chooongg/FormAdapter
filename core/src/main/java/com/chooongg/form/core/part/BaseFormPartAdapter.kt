package com.chooongg.form.core.part

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseFormPartAdapter(val formAdapter: FormAdapter, val style: BaseStyle) :
    RecyclerView.Adapter<FormViewHolder>() {

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
        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) = true
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            oldItem.id == newItem.id && oldItem.typeset == newItem.typeset
    }).build())

    val itemList get() = asyncDiffer.currentList

    fun update() {
        executeUpdate {
            calculateBoundary()
            notifyItemRangeChanged(0, itemCount)
        }
    }

    abstract fun executeUpdate(notifyBlock: () -> Unit)

    fun calculateBoundary() {
        val partAdapters = formAdapter.partAdapters
        val partIndex = partAdapters.indexOf(this)
        var spanIndex = 0
        val spanCount = 24
        itemList.forEachIndexed { index, item ->
            item.spanSize = if (item.loneLine) spanCount
            else {
                val span = spanCount / formAdapter.columnCount
                if (item.nextItemLoneLine || (index >= itemList.lastIndex && spanIndex + span < spanCount)) {
                    spanCount - spanIndex
                } else span
            }
            item.spanIndex = spanIndex
            if (spanIndex == 0) {
                item.marginBoundary.start = Boundary.GLOBAL
                item.insideBoundary.start = Boundary.GLOBAL
            } else {
                item.marginBoundary.start = Boundary.NONE
                item.insideBoundary.start = FormManager.Default.horizontalMiddleBoundary
            }
            spanIndex += item.spanSize
            if (spanIndex >= spanCount) {
                item.marginBoundary.end = Boundary.GLOBAL
                item.insideBoundary.end = Boundary.GLOBAL
                spanIndex = 0
            } else {
                item.marginBoundary.end = Boundary.NONE
                item.insideBoundary.end = FormManager.Default.horizontalMiddleBoundary
            }
            if (item.positionInGroup == 0) {
                var isFirst = true
                var beginIndex = partIndex
                while (beginIndex - 1 >= 0) {
                    if (partAdapters[beginIndex - 1].itemList.isNotEmpty()) {
                        isFirst = false
                        break
                    } else beginIndex--
                }
                if (isFirst) {
                    item.marginBoundary.top = Boundary.GLOBAL
                    item.insideBoundary.top = Boundary.GLOBAL
                } else {
                    item.marginBoundary.top = Boundary.MIDDLE
                    item.insideBoundary.top = Boundary.GLOBAL
                }
            } else if (item.spanIndex == 0) {
                item.marginBoundary.top = Boundary.NONE
                item.insideBoundary.top = Boundary.MIDDLE
            } else {
                var beginIndex = index - 1
                while (getItem(beginIndex).spanIndex != 0) {
                    beginIndex--
                }
                item.marginBoundary.top = getItem(beginIndex).marginBoundary.top
                item.insideBoundary.top = getItem(beginIndex).insideBoundary.top
            }
        }
        for (index in itemList.lastIndex downTo 0) {
            val item = getItem(index)
            if (item.itemCountInGroup - 1 - item.positionInGroup == 0) {
                var isLast = true
                var lastIndex = partIndex
                while (lastIndex + 1 < partAdapters.size) {
                    if (partAdapters[lastIndex + 1].itemList.isNotEmpty()) {
                        isLast = false
                        break
                    } else lastIndex++
                }
                if (isLast) {
                    item.marginBoundary.bottom = Boundary.GLOBAL
                    item.insideBoundary.bottom = Boundary.GLOBAL
                } else {
                    item.marginBoundary.bottom = Boundary.MIDDLE
                    item.insideBoundary.bottom = Boundary.GLOBAL
                }
            } else if (item.spanIndex + item.spanSize == spanCount) {
                item.marginBoundary.bottom = Boundary.NONE
                item.insideBoundary.bottom = Boundary.MIDDLE
            } else {
                var lastIndex = index + 1
                while (getItem(lastIndex).spanIndex + getItem(lastIndex).spanSize != spanCount) {
                    lastIndex++
                }
                item.marginBoundary.bottom = getItem(lastIndex).marginBoundary.bottom
                item.insideBoundary.bottom = getItem(lastIndex).insideBoundary.bottom
            }
        }
    }

    abstract fun findOfField(
        field: String,
        update: Boolean = true,
        hasPayload: Boolean = false,
        block: BaseForm.() -> Unit
    ): Boolean

    fun indexOf(item: BaseForm): Int? {
        itemList.forEachIndexed { index, it ->
            if (it.id == item.id) return index
        }
        return null
    }

    override fun getItemCount() = itemList.size

    fun getItem(position: Int) = itemList[position]

    override fun getItemViewType(position: Int): Int {
        return formAdapter.getItemViewType4Pool(style, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val style = formAdapter.getStyle4ItemViewType(viewType)
        style.createSizeInfo(this)
        val styleLayout = style.onCreateViewHolder(parent)?.apply {
            clipChildren = false
            clipToPadding = false
        }
        val typeset = formAdapter.getTypeset4ItemViewType(viewType)
        val typesetLayout = typeset.onCreateViewHolder(style, styleLayout ?: parent).apply {
            clipChildren = false
            clipToPadding = false
        }
        style.executeAddView(styleLayout, typesetLayout)
        val provider = formAdapter.getProvider4ItemViewType(viewType)
        val itemView = provider.onCreateViewHolder(style, typesetLayout)
        typeset.executeAddView(style, typesetLayout, itemView)
        val view = styleLayout ?: typesetLayout
        view.layoutParams =
            if (view.layoutParams != null) GridLayoutManager.LayoutParams(view.layoutParams!!)
            else GridLayoutManager.LayoutParams(-1, -2)
        return FormViewHolder(style, styleLayout, typeset, typesetLayout, itemView)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = getItem(position)
        val style = formAdapter.getStyle4ItemViewType(holder.itemViewType)
        formAdapter.getStyle4ItemViewType(holder.itemViewType).apply {
            onBindViewHolder(holder, holder.styleLayout, item)
        }
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
        val style = formAdapter.getStyle4ItemViewType(holder.itemViewType)
        style.onBindViewHolder(holder, holder.styleLayout, item)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType).apply {
            setTypesetLayoutPadding(holder, holder.typesetLayout, style.insideInfo, item)
            onBindViewHolder(holder, holder.typesetLayout, item)
        }
        formAdapter.getProvider4ItemViewType(holder.itemViewType).apply {
            if (payloads.isNotEmpty()) {
                payloads.forEach {
                    if (it == FormAdapter.UPDATE_PAYLOAD_FLAG) {
                        onBindViewHolderUpdate(
                            adapterScope, holder, holder.view, item, formAdapter.isEnabled
                        )
                    } else onBindViewHolderOtherPayload(
                        adapterScope, holder, holder.view, item, formAdapter.isEnabled, it
                    )
                }
            } else onBindViewHolder(adapterScope, holder, holder.view, item, formAdapter.isEnabled)
        }
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        formAdapter.getStyle4ItemViewType(holder.itemViewType)
            .onViewRecycled(holder, holder.styleLayout)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType)
            .onViewRecycled(holder, holder.typesetLayout)
        formAdapter.getProvider4ItemViewType(holder.itemViewType)
            .onViewRecycled(holder, holder.view)
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder) {
        formAdapter.getStyle4ItemViewType(holder.itemViewType)
            .onViewAttachedToWindow(holder, holder.styleLayout)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType)
            .onViewAttachedToWindow(holder, holder.typesetLayout)
        formAdapter.getProvider4ItemViewType(holder.itemViewType)
            .onViewAttachedToWindow(holder, holder.view)
    }

    override fun onViewDetachedFromWindow(holder: FormViewHolder) {
        formAdapter.getStyle4ItemViewType(holder.itemViewType)
            .onViewDetachedFromWindow(holder, holder.styleLayout)
        formAdapter.getTypeset4ItemViewType(holder.itemViewType)
            .onViewDetachedFromWindow(holder, holder.typesetLayout)
        formAdapter.getProvider4ItemViewType(holder.itemViewType)
            .onViewDetachedFromWindow(holder, holder.view)
    }
}