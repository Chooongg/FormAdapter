package com.chooongg.form.core.part

import android.annotation.SuppressLint
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
import com.chooongg.form.core.item.InternalFormNone
import com.chooongg.form.core.item.VariantForm
import com.chooongg.form.core.provider.InternalFormNoneProvider
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseFormPartAdapter(val formAdapter: FormAdapter, val style: BaseStyle) :
    RecyclerView.Adapter<FormViewHolder>() {

    private val spanCount = 27720

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

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) = true
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            oldItem.id == newItem.id && oldItem.typeset == newItem.typeset
    }).build())

    private val itemList: List<BaseForm> get() = asyncDiffer.currentList

    fun update() {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        executeUpdate(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun executeUpdate(isNotifyChanged: Boolean) {
        val groups = getOriginalItemList()
        val tempList = ArrayList<ArrayList<BaseForm>>()
        groups.forEach { group ->
            val tempGroup = ArrayList<BaseForm>()
            var variantIndex = -1
            group.forEach { item ->
                item.resetInternalValues()
                if (item.isRealVisible(formAdapter.isEnabled)) {
                    if (item is VariantForm) {
                        val tempChildList = ArrayList<BaseForm>()
                        item.getItems().forEach { child ->
                            child.resetInternalValues()
                            if (child.isRealVisible(formAdapter.isEnabled)) {
                                tempChildList.add(child)
                            }
                        }
                        if (tempChildList.isNotEmpty()) {
                            variantIndex++
                            tempChildList.forEachIndexed { i, child ->
                                child.loneLine = false
                                child.showAtEdge = item.showAtEdge
                                child.autoFill = item.autoFill
                                child.parentItem = item
                                child.variantIndexInGroup = variantIndex
                                child.countInCurrentVariant = tempChildList.size
                                child.indexInCurrentVariant = i
                            }
                            tempGroup.addAll(tempChildList)
                        }
                    } else {
                        tempGroup.add(item)
                    }
                }
            }
            while (tempGroup.size > 0 && !tempGroup[0].showAtEdge) {
                tempGroup.removeAt(0)
            }
            while (tempGroup.size > 1 && !tempGroup[tempGroup.lastIndex].showAtEdge) {
                tempGroup.removeAt(tempGroup.lastIndex)
            }
            tempList.add(tempGroup)
        }
        val tempList2 = ArrayList<List<BaseForm>>()
        tempList.forEach { group ->
            val tempGroup = ArrayList<BaseForm>()
            var spanIndex = 0
            group.forEachIndexed { position, item ->
                item.spanIndex = spanIndex
                item.spanSize = when {
                    item.loneLine -> {
                        item.spanIndex = 0
                        spanIndex = 0
                        spanCount
                    }

                    item.parentItem != null -> {
                        if (item.indexInCurrentVariant == 0) {
                            item.spanIndex = 0
                            spanIndex = 0
                        }
                        spanCount / item.parentItem!!.getColumn(
                            item.countInCurrentVariant, formAdapter.columnCount
                        )
                    }

                    else -> spanCount / formAdapter.columnCount
                }
                if (position > 0) {
                    if (item.spanIndex == 0) {
                        val lastItem = group[position - 1]
                        if (lastItem.spanIndex + lastItem.spanSize < spanCount) {
                            if (lastItem.autoFill) {
                                lastItem.spanSize = spanCount - lastItem.spanIndex
                            } else {
                                val noneIndex = lastItem.spanIndex + lastItem.spanSize
                                tempGroup.add(InternalFormNone(noneIndex, spanCount - noneIndex))
                            }
                        }
                    }
                }
                spanIndex = if (item.countInCurrentVariant - 1 - item.indexInCurrentVariant == 0) {
                    0
                } else if (spanIndex + item.spanSize < spanCount) {
                    spanIndex + item.spanSize
                } else 0
                tempGroup.add(item)
                if (position == group.lastIndex) {
                    if (item.spanIndex + item.spanSize < spanCount) {
                        if (item.autoFill) {
                            item.spanSize = spanCount - item.spanIndex
                        } else {
                            val noneIndex = item.spanIndex + item.spanSize
                            tempGroup.add(InternalFormNone(noneIndex, spanCount - noneIndex))
                        }
                    }
                }
            }
            tempList2.add(tempGroup)
        }
        tempList2.forEachIndexed { index, group ->
            group.forEachIndexed { position, item ->
                item.groupCount = tempList.size
                item.groupIndex = index
                item.countInGroup = group.size
                item.positionInGroup = position
            }
        }
        asyncDiffer.submitList(ArrayList<BaseForm>().apply { tempList2.forEach { addAll(it) } }) {
            calculateBoundary()
            if (isNotifyChanged) {
                notifyItemRangeChanged(0, itemCount)
            } else {
                notifyItemRangeChanged(0, itemCount, Boundary.NOTIFY_BOUNDARY_FLAG)
            }
        }
    }

    private fun calculateBoundary() {
        val partAdapters = formAdapter.partAdapters
        val partIndex = partAdapters.indexOf(this)
        itemList.forEachIndexed { index, item ->
            // Start
            if (item.spanIndex == 0) {
                item.marginBoundary.start = Boundary.GLOBAL
                item.insideBoundary.start = Boundary.GLOBAL
            } else {
                item.marginBoundary.start = Boundary.NONE
                item.insideBoundary.start = FormManager.Default.horizontalMiddleBoundary
            }
            // End
            if (item.spanIndex + item.spanSize >= spanCount) {
                item.marginBoundary.end = Boundary.GLOBAL
                item.insideBoundary.end = Boundary.GLOBAL
            } else {
                item.marginBoundary.end = Boundary.NONE
                item.insideBoundary.end = style.horizontalMiddleBoundary
            }
            // Top
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
                var beginItem = getItem(beginIndex)
                while (beginItem.spanIndex != 0) {
                    beginIndex--
                    beginItem = getItem(beginIndex)
                }
                item.marginBoundary.top = beginItem.marginBoundary.top
                item.insideBoundary.top = beginItem.insideBoundary.top
            }
        }
        for (index in itemList.lastIndex downTo 0) {
            val item = getItem(index)
            // Bottom
            if (item.countInGroup - 1 - item.positionInGroup == 0) {
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
                var lastItem = getItem(lastIndex)
                while (lastIndex > 0 && lastItem.countInGroup - 1 - lastItem.positionInGroup != 0
                    && (lastIndex + 1 < itemList.size && getItem(lastIndex + 1).spanIndex != 0)
                ) {
                    lastIndex++
                    lastItem = getItem(lastIndex)
                }
                item.marginBoundary.bottom = getItem(lastIndex).marginBoundary.bottom
                item.insideBoundary.bottom = getItem(lastIndex).insideBoundary.bottom
            }
        }
    }

    abstract fun getOriginalItemList(): List<List<BaseForm>>

    abstract fun findOfField(
        field: String,
        update: Boolean = true,
        hasPayload: Boolean = false,
        block: BaseForm.() -> Unit
    ): Boolean

    fun indexOf(item: BaseForm) = itemList.indexOf(item)

    override fun getItemCount() = itemList.size

    fun getItem(position: Int) = itemList[position]

    override fun getItemViewType(position: Int): Int {
        return formAdapter.getItemViewType4Pool(style, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val style = formAdapter.getStyle4ItemViewType(viewType)
        val typeset = formAdapter.getTypeset4ItemViewType(viewType)
        val provider = formAdapter.getProvider4ItemViewType(viewType)
        style.createSizeInfo(this)
        val styleLayout = if (provider !is InternalFormNoneProvider || style.isDecorateNoneItem()) {
            style.onCreateViewHolder(parent)?.apply {
                clipChildren = false
                clipToPadding = false
            }
        } else null
        val typesetLayout = typeset.onCreateViewHolder(style, styleLayout ?: parent).apply {
            clipChildren = false
            clipToPadding = false
        }
        style.executeAddView(styleLayout, typesetLayout)
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
        val provider = formAdapter.getProvider4ItemViewType(holder.itemViewType)
        if (provider !is InternalFormNoneProvider || style.isDecorateNoneItem()) {
            style.onBindViewHolder(holder, holder.styleLayout, item)
        }
        formAdapter.getTypeset4ItemViewType(holder.itemViewType).apply {
            setTypesetLayoutPadding(holder, holder.typesetLayout, style.insideInfo, item)
            onBindViewHolder(holder, holder.typesetLayout, item)
        }
        provider.apply {
            onBindViewHolder(
                adapterScope, holder, holder.view, item, item.isRealEnable(formAdapter.isEnabled)
            )
            errorNotify(holder, item)
        }
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val isHasBind = payloads.isEmpty()
        val item = getItem(position)
        val style = formAdapter.getStyle4ItemViewType(holder.itemViewType)
        val typeset = formAdapter.getTypeset4ItemViewType(holder.itemViewType)
        val provider = formAdapter.getProvider4ItemViewType(holder.itemViewType)
        if (isHasBind) {
            if (provider !is InternalFormNoneProvider || style.isDecorateNoneItem()) {
                style.onBindViewHolder(holder, holder.styleLayout, item)
            }
            typeset.setTypesetLayoutPadding(holder, holder.typesetLayout, style.insideInfo, item)
            typeset.onBindViewHolder(holder, holder.typesetLayout, item)
        }
        if (isHasBind) {
            provider.onBindViewHolder(
                adapterScope, holder, holder.view, item, item.isRealEnable(formAdapter.isEnabled)
            )
            provider.errorNotify(holder, item)
        } else payloads.forEach {
            when (it) {
                Boundary.NOTIFY_BOUNDARY_FLAG -> {
                    if (provider !is InternalFormNoneProvider || style.isDecorateNoneItem()) {
                        style.onBindViewHolder(holder, holder.styleLayout, item)
                    }
                    typeset.setTypesetLayoutPadding(
                        holder, holder.typesetLayout, style.insideInfo, item
                    )
                }

                FormAdapter.UPDATE_PAYLOAD_FLAG -> {
                    provider.onBindViewHolderUpdate(
                        adapterScope,
                        holder,
                        holder.view,
                        item,
                        item.isRealEnable(formAdapter.isEnabled)
                    )
                }

                FormAdapter.ERROR_NOTIFY_FLAG -> {
                    provider.onBindViewHolderErrorNotify(
                        adapterScope,
                        holder,
                        holder.view,
                        item,
                        item.isRealEnable(formAdapter.isEnabled)
                    )
                }

                else -> provider.onBindViewHolderOtherPayload(
                    adapterScope,
                    holder,
                    holder.view,
                    item,
                    item.isRealEnable(formAdapter.isEnabled),
                    it
                )
            }
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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    fun notifyChangeItem(
        item: BaseForm,
        hasPayload: Boolean,
    ) {
        val position = itemList.indexOf(item)
        if (position != -1) {
            val tempEmpty = itemList.isEmpty()
            if (hasPayload) {
                notifyItemChanged(position, FormAdapter.UPDATE_PAYLOAD_FLAG)
            } else {
                update()
                if (tempEmpty != itemList.isEmpty()) {
                    val partIndex = formAdapter.partAdapters.indexOf(this)
                    if (partIndex > 0) {
                        formAdapter.partAdapters[partIndex - 1].executeUpdate(false)
                    }
                    if (partIndex < formAdapter.partAdapters.size - 1) {
                        formAdapter.partAdapters[partIndex + 1].executeUpdate(false)
                    }
                }
            }
        }
    }
}