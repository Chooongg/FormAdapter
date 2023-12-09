package com.chooongg.form.part

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.InternalFormNone
import com.chooongg.form.item.InternalFormOperationButton
import com.chooongg.form.item.VariantForm
import com.chooongg.form.provider.InternalFormNoneProvider
import com.chooongg.form.style.BaseStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.json.JSONObject

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

    protected val itemList: List<BaseForm> get() = asyncDiffer.currentList

    fun update() {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        executeUpdate(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun executeUpdate(isNotifyChanged: Boolean) {
        val groups = getOriginalItemList()
        val extraGroupsCount = getExtraGroupCount()
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
                    item.parentItem != null -> {
                        if (item.indexInCurrentVariant == 0) {
                            item.spanIndex = 0
                            spanIndex = 0
                        }
                        spanCount / item.parentItem!!.getColumn(
                            item.countInCurrentVariant, formAdapter.columnCount
                        )
                    }

                    item.loneLine -> {
                        item.spanIndex = 0
                        spanIndex = 0
                        spanCount
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
        var localPosition = 0
        tempList2.forEachIndexed { index, group ->
            group.forEachIndexed { position, item ->
                item.groupCount = tempList.size - extraGroupsCount
                item.groupIndex = index
                item.countInGroup = group.size
                item.positionInGroup = position
                item.localPosition = localPosition
                localPosition++
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
        itemList.forEachIndexed { index, item ->
            // Start
            if (item.spanIndex == 0) {
                item.marginBoundary.start = Boundary.GLOBAL
                item.insideBoundary.start = Boundary.GLOBAL
            } else {
                item.marginBoundary.start = Boundary.NONE
                item.insideBoundary.start = style.horizontalMiddleBoundary
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
//                // 使用ItemDecoration控制GLOBAL类型
                item.marginBoundary.top = Boundary.MIDDLE
                item.insideBoundary.top = Boundary.GLOBAL
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
                item.marginBoundary.bottom = Boundary.MIDDLE
                item.insideBoundary.bottom = Boundary.GLOBAL
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

    open fun getExtraGroupCount() = 0

    abstract fun findOfField(field: String): BaseForm?

    abstract fun findOfId(id: String): BaseForm?

    abstract fun findOfItem(target: BaseForm): BaseForm?

    /**
     * 执行数据验证
     */
    @Throws(FormDataVerificationException::class)
    abstract fun executeDataVerification()

    abstract fun getDataVerificationError(): List<FormDataVerificationException>

    /**
     * 执行输出
     */
    abstract fun executeOutput(json: JSONObject)

    open fun onItemClick(item: BaseForm, view: View) {
        if (item is InternalFormOperationButton) {
            formAdapter.getOnOperationButtonClickListener()?.invoke(view)
            return
        }
        formAdapter.getOnItemClickListener()?.invoke(item, view)
    }

    fun indexOfShow(item: BaseForm) = itemList.indexOf(item)

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
        view.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
        view.textDirection = TextView.TEXT_DIRECTION_LOCALE
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
            formAdapter.getTypeset4ItemViewType(holder.itemViewType).apply {
                setTypesetLayoutPadding(holder, holder.typesetLayout, style.insideInfo, item)
                onBindViewHolder(holder, holder.typesetLayout, item, formAdapter.isEnabled)
            }
            provider.onBindViewHolder(
                adapterScope, holder, holder.view, item, item.isRealEnable(formAdapter.isEnabled)
            )
            provider.errorNotify(holder, item)
        }
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val payloadEmpty = payloads.isEmpty()
        val item = getItem(position)
        val style = formAdapter.getStyle4ItemViewType(holder.itemViewType)
        val typeset = formAdapter.getTypeset4ItemViewType(holder.itemViewType)
        val provider = formAdapter.getProvider4ItemViewType(holder.itemViewType)
        if (payloadEmpty && (provider !is InternalFormNoneProvider || style.isDecorateNoneItem())) {
            style.onBindViewHolder(holder, holder.styleLayout, item)
            typeset.setTypesetLayoutPadding(holder, holder.typesetLayout, style.insideInfo, item)
            typeset.onBindViewHolder(holder, holder.typesetLayout, item, formAdapter.isEnabled)
            provider.onBindViewHolder(
                adapterScope, holder, holder.view, item, item.isRealEnable(formAdapter.isEnabled)
            )
            provider.errorNotify(holder, item)
        }
        if (!payloadEmpty) payloads.forEach {
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
            if (hasPayload) {
                notifyItemChanged(position, FormAdapter.UPDATE_PAYLOAD_FLAG)
            } else {
                update()
            }
        }
    }
}