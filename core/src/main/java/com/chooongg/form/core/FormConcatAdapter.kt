package com.chooongg.form.core

import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chooongg.form.core.provider.AbsFormProvider
import com.chooongg.form.core.style.AbsStyle
import com.chooongg.form.core.typeset.AbsTypeset

open class FormConcatAdapter internal constructor() : RecyclerView.Adapter<ViewHolder>() {

    private var _recyclerView: RecyclerView? = null

    private val concatAdapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING && recyclerView.focusedChild != null) {
                recyclerView.focusedChild.clearFocus()
            }
        }
    }

    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onStateRestorationPolicyChanged() {
            stateRestorationPolicy = concatAdapter.stateRestorationPolicy
        }
    }

    init {
        concatAdapter.registerAdapterDataObserver(dataObserver)
    }

    //<editor-fold desc="ConcatAdapter 覆写">

    val adapters get() = concatAdapter.adapters

    fun addAdapter(adapter: RecyclerView.Adapter<ViewHolder>) {
        concatAdapter.addAdapter(adapter)
    }

    fun addAdapter(index: Int, adapter: RecyclerView.Adapter<ViewHolder>) {
        concatAdapter.addAdapter(index, adapter)
    }

    fun removeAdapter(adapter: RecyclerView.Adapter<ViewHolder>) {
        concatAdapter.removeAdapter(adapter)
    }

    override fun findRelativeAdapterPositionIn(
        adapter: RecyclerView.Adapter<*>, viewHolder: ViewHolder, localPosition: Int
    ) = concatAdapter.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition)

    fun getWrappedAdapterAndPosition(globalPosition: Int) =
        concatAdapter.getWrappedAdapterAndPosition(globalPosition)

    //</editor-fold>

    //<editor-fold desc="RecyclerView.Adapter 覆写">

    override fun getItemViewType(position: Int) =
        concatAdapter.getItemViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        concatAdapter.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        concatAdapter.onBindViewHolder(holder, position)

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) =
        concatAdapter.onBindViewHolder(holder, position, payloads)

    override fun getItemId(position: Int) =
        concatAdapter.getItemId(position)

    override fun getItemCount() =
        concatAdapter.itemCount

    override fun onFailedToRecycleView(holder: ViewHolder) =
        concatAdapter.onFailedToRecycleView(holder)

    override fun onViewAttachedToWindow(holder: ViewHolder) =
        concatAdapter.onViewAttachedToWindow(holder)

    override fun onViewDetachedFromWindow(holder: ViewHolder) =
        concatAdapter.onViewDetachedFromWindow(holder)

    override fun onViewRecycled(holder: ViewHolder) =
        concatAdapter.onViewRecycled(holder)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        concatAdapter.onAttachedToRecyclerView(recyclerView)
        if (recyclerView.layoutManager !is FormLayoutManager) {
            val layoutManager = FormLayoutManager(recyclerView.context)
            recyclerView.layoutManager = layoutManager
//            normalColumnCount = layoutManager.normalColumnCount
        }
        recyclerView.addOnScrollListener(onScrollListener)
        _recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        concatAdapter.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(onScrollListener)
        clearPool()
        _recyclerView = null
    }

    fun scrollToPosition(globalPosition: Int) {
        _recyclerView?.smoothScrollToPosition(globalPosition)
    }

    //</editor-fold>

    //<editor-fold desc="RecyclerView.Adapter 类型匹配池">

    private val stylePool = ArrayList<AbsStyle>()
    private val typesetPool = ArrayList<AbsTypeset>()
    private val providerPool = ArrayList<AbsFormProvider>()
    private val itemTypePool = ArrayList<Triple<Int, Int, Int>>()

    internal fun getItemViewType(
        style: AbsStyle,
        typeset: AbsTypeset,
        provider: AbsFormProvider
    ): Int {
        val styleIndex = stylePool.indexOf(style).let {
            if (it < 0) {
                stylePool.add(style)
                stylePool.lastIndex
            } else it
        }
        val typesetIndex = typesetPool.indexOf(typeset).let {
            if (it < 0) {
                typesetPool.add(typeset)
                typesetPool.lastIndex
            } else it
        }
        val providerIndex = providerPool.indexOf(provider).let {
            if (it < 0) {
                providerPool.add(provider)
                providerPool.lastIndex
            } else it
        }
        val typeInfo = Triple(styleIndex, typesetIndex, providerIndex)
        return itemTypePool.indexOf(typeInfo).let {
            if (it < 0) {
                itemTypePool.add(typeInfo)
                itemTypePool.lastIndex
            } else it
        }
    }

    internal fun getStyle4ItemViewType(viewType: Int): AbsStyle {
        return stylePool[itemTypePool[viewType].first]
    }

    internal fun getTypeset4ItemViewType(viewType: Int): AbsTypeset {
        return typesetPool[itemTypePool[viewType].second]
    }

    internal fun getProvider4ItemViewType(viewType: Int): AbsFormProvider {
        return providerPool[itemTypePool[viewType].third]
    }

    fun clearPool() {
        _recyclerView?.recycledViewPool?.clear()
        stylePool.clear()
        typesetPool.clear()
        providerPool.clear()
        itemTypePool.clear()
    }

    //</editor-fold>

    fun clear() {
        concatAdapter.adapters.forEach { concatAdapter.removeAdapter(it) }
        clearPool()
    }
}