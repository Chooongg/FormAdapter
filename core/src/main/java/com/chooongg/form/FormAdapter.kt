package com.chooongg.form

import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chooongg.form.data.FormAdapterData
import com.chooongg.form.data.FormDynamicPartData
import com.chooongg.form.data.FormPartData
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.LinkageForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormDynamicPartAdapter
import com.chooongg.form.part.FormPartAdapter
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NoneStyle
import com.chooongg.form.typeset.BaseTypeset
import org.json.JSONObject

open class FormAdapter(isEnabled: Boolean) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val UPDATE_PAYLOAD_FLAG = "UPDATE"
        const val ERROR_NOTIFY_FLAG = "ERROR_NOTIFY"
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
        internal set(value) {
            field = value
            partAdapters.forEach { it.executeUpdate(false) }
        }

    var refreshLinkageWhileCreate: Boolean = true

    private var menuClickListener: FormMenuClickGlobalBlock? = null

    private var itemClickListener: FormItemClickBlock? = null

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

    fun setNewInstance(block: FormAdapterData.() -> Unit) {
        clear()
        val data = FormAdapterData(this)
        block(data)
        data.getParts().forEach {
            when (it) {
                is FormPartAdapter -> addPart(it)
                is FormDynamicPartAdapter -> addDynamicPart(it)
            }
        }
        if (refreshLinkageWhileCreate) refreshLinkage()
    }

    fun getPartAdapter(position: Int): BaseFormPartAdapter {
        return concatAdapter.getWrappedAdapterAndPosition(position).first as BaseFormPartAdapter
    }

    fun getItem(position: Int): BaseForm {
        val pair = concatAdapter.getWrappedAdapterAndPosition(position)
        return (pair.first as BaseFormPartAdapter).getItem(pair.second)
    }

    fun updateForm() {
        recyclerView?.focusedChild?.clearFocus()
        partAdapters.forEach { it.update() }
    }

    fun refreshLinkage() {
        partAdapters.forEach {
            when (it) {
                is FormPartAdapter -> {
                    it.data.getItems().forEach { item ->
                        item.getLinkageBlock()
                            ?.invoke(LinkageForm(it), item.field, item.content)
                    }
                }

                is FormDynamicPartAdapter -> {
                    it.data.getGroups().forEach { group ->
                        group.getItems().forEach { item ->
                            item.getLinkageBlock()
                                ?.invoke(LinkageForm(it), item.field, item.content)
                        }
                    }
                }
            }
        }
    }

    fun triggerLinkage(item: BaseForm) {
        partAdapters.forEach {
            val findOfItem = it.findOfItem(item)
            if (findOfItem != null) item.triggerLinkage(it)
        }
    }

    //<editor-fold desc="listener 监听事件">

    /**
     * 设置菜单点击事件
     */
    fun setOnMenuClickListener(block: FormMenuClickGlobalBlock?) {
        menuClickListener = block
    }

    fun getOnMenuClickListener() = menuClickListener

    /**
     * 设置按钮点击事件
     */
    fun setOnItemClickListener(block: FormItemClickBlock?) {
        itemClickListener = block
    }

    fun getOnItemClickListener() = itemClickListener

    //</editor-fold>

    fun findOfField(
        field: String,
        update: Boolean = true,
        hasPayload: Boolean = true,
        block: BaseForm.() -> Unit
    ): Boolean {
        partAdapters.forEach {
            val item = it.findOfField(field)
            if (item != null) {
                block.invoke(item)
                if (update) {
                    if (hasPayload) it.notifyChangeItem(item, true) else it.update()
                }
                return true
            }
        }
        return false
    }

    fun findOfId(
        id: String,
        update: Boolean = true,
        hasPayload: Boolean = true,
        block: BaseForm.() -> Unit
    ): Boolean {
        partAdapters.forEach {
            val item = it.findOfId(id)
            if (item != null) {
                block.invoke(item)
                if (update) {
                    if (hasPayload) it.notifyChangeItem(item, true) else it.update()
                }
                return true
            }
        }
        return false
    }

    /**
     * 执行数据效验
     */
    fun executeDataVerification(): Boolean {
        return try {
            partAdapters.forEach { it.executeDataVerification() }
            true
        } catch (e: FormDataVerificationException) {
            errorNotify(e.item)
            if (recyclerView != null) {
                FormManager.Default.errorOutputTool.errorOutput(recyclerView!!.context, e)
            }
            false
        }
    }

    /**
     * 执行输出
     */
    fun executeOutput(): JSONObject {
        val json = JSONObject()
        partAdapters.forEach {
            it.executeOutput(json)
        }
        return json
    }

    /**
     * 错误通知
     */
    fun errorNotify(item: BaseForm) {
        var position = 0
        partAdapters.forEach {
            val indexOf = it.indexOf(item)
            if (it.indexOf(item) != -1) {
                item.errorNotify = System.currentTimeMillis()
                position += indexOf
                val layoutManager = recyclerView?.layoutManager as? FormLayoutManager
                if (layoutManager != null) {
                    val firstVisiblePosition =
                        layoutManager.findFirstVisibleItemPosition()
                    val lastVisiblePosition =
                        layoutManager.findLastVisibleItemPosition()
                    if (position in firstVisiblePosition..lastVisiblePosition) {
                        it.notifyItemChanged(indexOf, ERROR_NOTIFY_FLAG)
                    }
                    recyclerView?.smoothScrollToPosition(position)
                }
            } else {
                position += it.itemCount
            }
        }
    }

    //<editor-fold desc="ConcatAdapter 覆写">

    fun addPart(
        style: BaseStyle = NoneStyle(),
        block: FormPartData.() -> Unit
    ) {
        val adapter = FormPartAdapter(this, style)
        addPart(adapter)
        adapter.create { block(this) }
    }

    fun addPart(adapter: FormPartAdapter?) {
        if (adapter != null) concatAdapter.addAdapter(adapter)
    }

    fun addDynamicPart(
        style: BaseStyle = NoneStyle(),
        block: FormDynamicPartData.() -> Unit
    ) {
        val adapter = FormDynamicPartAdapter(this, style)
        adapter.create { block(this) }
        addDynamicPart(adapter)
    }

    fun addDynamicPart(adapter: FormDynamicPartAdapter?) {
        if (adapter != null) concatAdapter.addAdapter(adapter)
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
        var hasAddItemDecoration = true
        if (recyclerView.itemDecorationCount > 0) {
            for (i in 0 until recyclerView.itemDecorationCount) {
                if (recyclerView.getItemDecorationAt(i) is FormItemDecoration) {
                    hasAddItemDecoration = false
                }
            }
        }
        if (hasAddItemDecoration) recyclerView.addItemDecoration(FormItemDecoration())
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        concatAdapter.onDetachedFromRecyclerView(recyclerView)
        clearPool()
        this.recyclerView = null
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