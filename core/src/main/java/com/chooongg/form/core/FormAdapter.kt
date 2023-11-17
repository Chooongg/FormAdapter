package com.chooongg.form.core

import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chooongg.form.core.data.FormDynamicPartData
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.part.BaseFormPartAdapter
import com.chooongg.form.core.part.FormDynamicPartAdapter
import com.chooongg.form.core.part.FormPartAdapter
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.style.BaseStyle
import com.chooongg.form.core.style.NoneStyle
import com.chooongg.form.core.typeset.BaseTypeset

open class FormAdapter(isEnabled: Boolean) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val UPDATE_PAYLOAD_FLAG = "UPDATE"
    }

    internal var recyclerView: RecyclerView? = null

    private val concatAdapter = ConcatAdapter(
        ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
    )

    val partAdapters get() = concatAdapter.adapters.map { it as BaseFormPartAdapter }

    var isEnabled: Boolean = isEnabled
        set(value) {
            field = value
            updateForm()
        }

    var columnCount: Int = 1
        internal set

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

    fun setNewInstance(block: FormAdapter.() -> Unit) {
        clear()
        block(this)
        updateForm()
    }

    fun updateForm() {
        recyclerView?.focusedChild?.clearFocus()
        partAdapters.forEach { it.update() }
    }

    fun findOfField(
        field: String,
        update: Boolean = true,
        hasPayload: Boolean = false,
        block: BaseForm.() -> Unit
    ): Boolean {
        partAdapters.forEach {
            if (it.findOfField(field, update, hasPayload, block)) return true
        }
        return false
    }

    //<editor-fold desc="ConcatAdapter 覆写">

    fun addPart(
        style: BaseStyle = NoneStyle(),
        updateAdjacentAdapter: Boolean = false,
        block: FormPartData.() -> Unit
    ) {
        val adapter = FormPartAdapter(this, style)
        adapter.create { block(this) }
        addPart(adapter, updateAdjacentAdapter)
    }

    fun addPart(adapter: FormPartAdapter?, updateAdjacentAdapter: Boolean = false) {
        if (adapter != null) concatAdapter.addAdapter(adapter)
        if (!updateAdjacentAdapter || recyclerView == null || concatAdapter.adapters.size - 2 < 0) return
        partAdapters[concatAdapter.adapters.size - 2].update()
    }

    fun addDynamicPart(
        style: BaseStyle = NoneStyle(),
        updateAdjacentAdapter: Boolean = false,
        block: FormDynamicPartData.() -> Unit
    ) {
        val adapter = FormDynamicPartAdapter(this, style)
        adapter.create { block(this) }
        addDynamicPart(adapter, updateAdjacentAdapter)
    }

    fun addDynamicPart(adapter: FormDynamicPartAdapter?, updateAdjacentAdapter: Boolean = false) {
        if (adapter != null) concatAdapter.addAdapter(adapter)
        if (!updateAdjacentAdapter || recyclerView == null || concatAdapter.adapters.size - 2 < 0) return
        partAdapters[concatAdapter.adapters.size - 2].update()
    }

    fun removeAdapter(adapter: RecyclerView.Adapter<ViewHolder>) {
        concatAdapter.removeAdapter(adapter)
    }

    override fun findRelativeAdapterPositionIn(
        adapter: RecyclerView.Adapter<*>, viewHolder: ViewHolder, localPosition: Int
    ) = concatAdapter.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition)

    fun getWrappedAdapterAndPosition(globalPosition: Int): Pair<BaseFormPartAdapter, Int> =
        concatAdapter.getWrappedAdapterAndPosition(globalPosition).let {
            return Pair(it.first as BaseFormPartAdapter, it.second)
        }

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
            if (recyclerView is FormView) {
                layoutManager.setFormMargin(
                    recyclerView.formMarginStart, recyclerView.formMarginEnd
                )
            }
            recyclerView.layoutManager = layoutManager
            columnCount = layoutManager.columnCount
        }
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        concatAdapter.onDetachedFromRecyclerView(recyclerView)
        clearPool()
        this.recyclerView = null
    }

    fun scrollToPosition(globalPosition: Int) {
        recyclerView?.smoothScrollToPosition(globalPosition)
    }

    //</editor-fold>

    //<editor-fold desc="RecyclerView.Adapter 类型匹配池">

    private val stylePool = ArrayList<BaseStyle>()
    private val typesetPool = ArrayList<BaseTypeset>()
    private val providerPool = ArrayList<BaseFormProvider>()
    private val itemTypePool = ArrayList<Triple<Int, Int, Int>>()

    @Suppress("DEPRECATION")
    internal fun getItemViewType4Pool(
        style: BaseStyle,
        item: BaseForm
    ): Int {
        val styleIndex = stylePool.indexOf(style).let {
            if (it < 0) {
                stylePool.add(style)
                stylePool.lastIndex
            } else it
        }
        val typeset = item.typeset ?: style.typeset ?: FormManager.Default.typeset
        val typesetIndex = typesetPool.indexOf(typeset).let {
            if (it < 0) {
                typesetPool.add(typeset)
                typesetPool.lastIndex
            } else it
        }
        val providerClass = item.getProvider(this)
        val providerIndex = providerPool.indexOfFirst { it::class == providerClass }.let {
            if (it < 0) {
                providerPool.add(providerClass.java.newInstance())
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

    internal fun getStyle4ItemViewType(viewType: Int): BaseStyle {
        return stylePool[itemTypePool[viewType].first]
    }

    internal fun getTypeset4ItemViewType(viewType: Int): BaseTypeset {
        return typesetPool[itemTypePool[viewType].second]
    }

    internal fun getProvider4ItemViewType(viewType: Int): BaseFormProvider {
        return providerPool[itemTypePool[viewType].third]
    }

    fun clearPool() {
        recyclerView?.recycledViewPool?.clear()
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