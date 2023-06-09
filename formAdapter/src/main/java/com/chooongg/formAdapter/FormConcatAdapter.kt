package com.chooongg.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chooongg.formAdapter.format.FormNameFormat
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.provider.BaseFormProvider
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrResourcesId
import com.google.android.material.shape.ShapeAppearanceModel
import java.lang.ref.WeakReference

open class FormConcatAdapter constructor(isEditable: Boolean) : RecyclerView.Adapter<ViewHolder>() {

    private val concatAdapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    var shapeAppearanceResId: Int? = null
        set(value) {
            field = value
            concatAdapter.notifyItemRangeChanged(0, itemCount)
        }

    var shapeAppearanceModel: ShapeAppearanceModel? = null
        private set

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            updateForm()
        }

    var normalColumnCount = 1
        internal set

    var nameFormat: FormNameFormat = FormManager.nameFormat

    val adapters get() = concatAdapter.adapters

    val partAdapters get() = adapters.map { it as FormPartAdapter }

    internal var _recyclerView: WeakReference<RecyclerView> = WeakReference(null)

    private val dataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            this@FormConcatAdapter.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            this@FormConcatAdapter.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            this@FormConcatAdapter.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            this@FormConcatAdapter.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            this@FormConcatAdapter.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onStateRestorationPolicyChanged() {
            this@FormConcatAdapter.stateRestorationPolicy = concatAdapter.stateRestorationPolicy
        }
    }

    init {
        concatAdapter.registerAdapterDataObserver(dataObserver)
    }

    fun plusPart(adapter: FormPartAdapter) {
        concatAdapter.addAdapter(adapter)
        if (_recyclerView.get() == null || adapters.size - 2 < 0) return
        partAdapters[adapters.size - 2].update()
    }

    fun plusPart(index: Int, adapter: FormPartAdapter) {
        concatAdapter.addAdapter(index, adapter)
        if (_recyclerView.get() == null) return
        if (index - 1 >= 0) partAdapters[index - 1].update()
        if (index + 1 < adapters.size) partAdapters[index + 1].update()
    }

    fun removePart(adapter: FormPartAdapter) {
        concatAdapter.removeAdapter(adapter)
    }

    fun partIndexOf(part: FormPartAdapter) = adapters.indexOf(part)

    fun partSize() = concatAdapter.adapters.size

    fun updateForm() {
        partAdapters.forEach { it.update() }
    }

    fun scrollToPosition(globalPosition: Int) {
        _recyclerView.get()?.smoothScrollToPosition(globalPosition)
    }

    fun scrollToItem(item: BaseForm) {
        if (item.globalPosition > 0) scrollToPosition(item.globalPosition)
    }

    fun findOfField(
        field: String,
        update: Boolean = true,
        block: (BaseForm) -> Unit
    ): Boolean {
        partAdapters.forEach {
            val isExist = it.findOfField(field, update, block)
            if (isExist) return true
        }
        return false
    }

    fun findRelativeAdapterPositionIn(
        adapter: FormPartAdapter,
        viewHolder: ViewHolder,
        localPosition: Int
    ): Int {
        return concatAdapter.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition)
    }

    fun getWrappedAdapterAndPosition(globalPosition: Int): Pair<FormPartAdapter, Int> {
        concatAdapter.getWrappedAdapterAndPosition(globalPosition).let {
            return Pair(it.first as FormPartAdapter, it.second)
        }
    }

    fun getItem(globalPosition: Int): BaseForm {
        getWrappedAdapterAndPosition(globalPosition).let {
            return it.first.getItem(it.second)
        }
    }

    //<editor-fold desc="ConcatAdapter 覆写">

    override fun getItemViewType(position: Int): Int {
        return concatAdapter.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return concatAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        concatAdapter.onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        concatAdapter.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemId(position: Int): Long {
        return concatAdapter.getItemId(position)
    }

    override fun getItemCount(): Int {
        return concatAdapter.itemCount
    }

    override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
        return concatAdapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        concatAdapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        concatAdapter.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        concatAdapter.onViewRecycled(holder)
    }

    private val onScrollListener = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING && recyclerView.focusedChild != null) {
                recyclerView.focusedChild.clearFocus()
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
        if (recyclerView.layoutManager !is FormLayoutManager) {
            val layoutManager = FormLayoutManager(recyclerView.context)
            recyclerView.layoutManager = layoutManager
            normalColumnCount = layoutManager.normalColumnCount
        }
        shapeAppearanceModel = ShapeAppearanceModel.builder(
            recyclerView.context,
            shapeAppearanceResId
                ?: recyclerView.attrResourcesId(
                    com.google.android.material.R.attr.shapeAppearanceCornerMedium,
                    com.google.android.material.R.style.ShapeAppearance_Material3_Corner_Medium
                ), 0
        ).build()
        concatAdapter.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(onScrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(null)
        concatAdapter.onDetachedFromRecyclerView(recyclerView)
        shapeAppearanceModel = null
        recyclerView.removeOnScrollListener(onScrollListener)
    }

    //</editor-fold>

    //<editor-fold desc="ItemViewType 缓存池">

    private val typePool = ArrayList<FormTypeInfo>()

    internal fun getItemViewType(typeset: Typeset, provider: BaseFormProvider): Int {
        val info = FormTypeInfo(typeset, provider)
        if (!typePool.contains(info)) {
            typePool.add(info)
        }
        return typePool.indexOf(info)
    }

    internal fun getTypesetForItemViewType(viewType: Int): Typeset {
        return typePool[viewType].typeset
    }

    internal fun getItemProviderForItemViewType(viewType: Int): BaseFormProvider {
        return typePool[viewType].itemProvider
    }

    //</editor-fold>

    fun clear() {
        concatAdapter.adapters.forEach { concatAdapter.removeAdapter(it) }
        _recyclerView.get()?.also {
            it.recycledViewPool.clear()
            typePool.clear()
        }
    }
}