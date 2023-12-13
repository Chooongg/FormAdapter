package com.chooongg.form

import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chooongg.form.data.FormAdapterData
import com.chooongg.form.data.FormDynamicPartData
import com.chooongg.form.data.FormPartData
import com.chooongg.form.enum.FormEnableMode
import com.chooongg.form.enum.FormVisibilityMode
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.FormButton
import com.chooongg.form.item.InternalFormOperationButton
import com.chooongg.form.item.LinkageForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.part.FormDynamicPartAdapter
import com.chooongg.form.part.FormPartAdapter
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NoneStyle
import com.chooongg.form.style.NotAlignmentStyle
import com.chooongg.form.typeset.BaseTypeset
import org.json.JSONObject

open class FormAdapter(isEnabled: Boolean = false) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val UPDATE_PAYLOAD_FLAG = "UPDATE"
        const val ERROR_NOTIFY_FLAG = "ERROR_NOTIFY"
    }

    private val concatAdapter = ConcatAdapter(
        ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
    )

    val partAdapters get() = concatAdapter.adapters.map { it as BaseFormPartAdapter }

    internal var recyclerView: RecyclerView? = null

    private var operationPart: FormPartAdapter? = null

    var isEnabled: Boolean = isEnabled
        set(value) {
            if (field != value) {
                field = value
                updateForm()
            }
        }

    var columnCount: Int = 1
        internal set(value) {
            if (field != value) {
                field = value
                updateForm()
            }
        }


    /**
     * 创建时刷新联动操作
     */
    var refreshLinkageWhileCreate: Boolean = true

    private var menuClickListener: FormMenuClickGlobalBlock? = null

    private var itemClickListener: FormItemClickBlock? = null

    private var operationClickListener: FormOperationButtonClickBlock? = null

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
                is FormPartAdapter -> addPart(it, false)
                is FormDynamicPartAdapter -> addDynamicPart(it, false)
            }
        }
        if (refreshLinkageWhileCreate) refreshLinkage()
        updateForm()
    }

    fun showOperationButton(style: BaseStyle = NotAlignmentStyle(), block: FormButton.() -> Unit) {
        operationPart = addPart(style) {
            addItem(InternalFormOperationButton("").apply {
                visibilityMode = FormVisibilityMode.ENABLED
                enableMode = FormEnableMode.ALWAYS
                block.invoke(this)
            })
        }
    }

    fun removeOperationButton() {
        if (operationPart != null) {
            concatAdapter.removeAdapter(operationPart!!)
            operationPart = null
        }
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

    /**
     * 设置操作点击事件
     */
    fun setOnOperationButtonClickListener(block: FormOperationButtonClickBlock?) {
        operationClickListener = block
    }

    fun getOnOperationButtonClickListener() = operationClickListener

    //</editor-fold>

    fun findItemOfField(field: String): BaseForm? {
        partAdapters.forEach {
            val item = it.findOfField(field)
            if (item != null) {
                return item
            }
        }
        return null
    }

    fun findItemOfId(id: String): BaseForm? {
        partAdapters.forEach {
            val item = it.findOfId(id)
            if (item != null) {
                return item
            }
        }
        return null
    }

    fun updateItemOfField(
        field: String,
        hasPayload: Boolean = true,
        block: BaseForm.() -> Unit
    ): Boolean {
        partAdapters.forEach {
            val item = it.findOfField(field)
            if (item != null) {
                block.invoke(item)
                if (hasPayload) it.notifyChangeItem(item, true) else it.update()
                return true
            }
        }
        return false
    }

    fun updateItemOfId(
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
    fun executeDataVerification(notifyAllError: Boolean = FormManager.Default.dataVerificationNotifyAllError): Boolean {
        return if (notifyAllError) {
            val errors = getDataVerificationList()
            if (errors.isNotEmpty()) {
                val messages = ArrayList<CharSequence>()
                errors.forEachIndexed { index, e ->
                    e.item.errorNotify = System.currentTimeMillis()
                    if (e.item.globalPosition >= 0) {
                        notifyItemChanged(e.item.globalPosition, ERROR_NOTIFY_FLAG)
                        if (index == 0) {
                            recyclerView?.smoothScrollToPosition(e.item.globalPosition)
                        }
                    }
                    FormManager.Default.errorFormatter.format(recyclerView!!.context, e)?.apply {
                        messages.add(this)
                    }
                }
                if (recyclerView != null) {
                    FormManager.Default.errorOutputTool.errorOutput(
                        recyclerView!!.context, messages
                    )
                }
                true
            } else false
        } else try {
            partAdapters.forEach { it.executeDataVerification() }
            true
        } catch (e: FormDataVerificationException) {
            e.item.errorNotify = System.currentTimeMillis()
            if (e.item.globalPosition >= 0) {
                notifyItemChanged(e.item.globalPosition, ERROR_NOTIFY_FLAG)
                recyclerView?.smoothScrollToPosition(e.item.globalPosition)
            }
            if (recyclerView != null) {
                val message = FormManager.Default.errorFormatter.format(recyclerView!!.context, e)
                FormManager.Default.errorOutputTool.errorOutput(recyclerView!!.context, message)
            }
            false
        }
    }

    fun getDataVerificationList(): List<FormDataVerificationException> {
        val errors = ArrayList<FormDataVerificationException>()
        partAdapters.forEach { errors.addAll(it.getDataVerificationError()) }
        return errors
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
    fun errorNotify(item: BaseForm, isToScrollHere: Boolean = true) {
        item.errorNotify = System.currentTimeMillis()
        if (item.globalPosition >= 0) {
            notifyItemChanged(item.globalPosition, ERROR_NOTIFY_FLAG)
            if (isToScrollHere) recyclerView?.smoothScrollToPosition(item.globalPosition)
        }
    }

    //<editor-fold desc="ConcatAdapter 覆写">

    fun addPart(
        style: BaseStyle = NoneStyle(),
        block: FormPartData.() -> Unit
    ): FormPartAdapter {
        val adapter = FormPartAdapter(this, style)
        adapter.create { block(this) }
        addPart(adapter)
        return adapter
    }

    fun addPart(adapter: FormPartAdapter?, update: Boolean = true) {
        if (adapter != null) {
            if (operationPart != null) {
                concatAdapter.addAdapter(concatAdapter.adapters.size - 1, adapter)
            } else concatAdapter.addAdapter(adapter)
            if (update) adapter.update()
        }
    }

    fun addDynamicPart(
        style: BaseStyle = NoneStyle(),
        block: FormDynamicPartData.() -> Unit
    ): FormDynamicPartAdapter {
        val adapter = FormDynamicPartAdapter(this, style)
        adapter.create { block(this) }
        addDynamicPart(adapter)
        return adapter
    }

    fun addDynamicPart(adapter: FormDynamicPartAdapter?, update: Boolean = true) {
        if (adapter != null) {
            if (operationPart != null) {
                concatAdapter.addAdapter(concatAdapter.adapters.size - 1, adapter)
            } else concatAdapter.addAdapter(adapter)
            if (update) adapter.update()
        }
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
            recyclerView.layoutManager = layoutManager
        }
        columnCount = (recyclerView.layoutManager as FormLayoutManager).columnCount
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