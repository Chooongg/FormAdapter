package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.GravityInt
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormDataVerificationException
import com.chooongg.form.core.FormLinkageBlock
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.enum.FormOutputMode
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.typeset.BaseTypeset
import org.json.JSONObject
import kotlin.reflect.KClass

abstract class BaseForm(
    /**
     * 名称: Int(StringRes), String, CharSequence
     */
    var name: Any?,
    /**
     * 字段
     */
    var field: String?
) : AbstractForm() {

    abstract fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider>

    //<editor-fold desc="初始化方法 initialize">

    /**
     * 初始化 content 值
     * 该操作主要作用为修复
     */
    open fun initContentValue(value: Any?) {
    }

    //</editor-fold>

    //<editor-fold desc="基础 Basic">

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, Any?>? = null

    /**
     * 提示: Int(StringRes), String, CharSequence
     */
    var hint: Any? = null

    /**
     * 内容
     */
    var content: Any? = null

    /**
     * 是否为必填项
     */
    var required: Boolean = false

    /**
     * 获取内容文本
     */
    open fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return FormUtils.getText(context, content)
    }

    //</editor-fold>

    //<editor-fold desc="排版 Typeset">

    /**
     * 排版
     */
    open var typeset: BaseTypeset? = null

    @GravityInt
    open var gravity: Int? = null

    /**
     * 内容重力
     */
    @GravityInt
    open var contentGravity: Int? = null

    /**
     * 多列内容重力
     */
    @GravityInt
    open var multiColumnContentGravity: Int? = null

    /**
     * 独占一行
     */
    open var loneLine = false

    /**
     * 在边缘显示
     */
    open var showAtEdge = true

    /**
     * 自动填充
     */
    open var autoFill = true

    /**
     * 错误通知
     */
    var errorNotify: Long = 0

    //</editor-fold>

    //<editor-fold desc="内部 Internal">

    /**
     * 边界信息
     */
    var marginBoundary: Boundary = Boundary()
        internal set

    /**
     * 填充信息
     */
    var insideBoundary: Boundary = Boundary()
        internal set

    /**
     * Part 组数量
     */
    var groupCount = -1
        internal set

    /**
     * Part 组索引
     */
    var groupIndex = -1
        internal set

    /**
     * 组中项数
     */
    var countInGroup = -1
        internal set

    /**
     * 组中位置
     */
    var positionInGroup = -1
        internal set

    /**
     * 组中的变量索引
     */
    var variantIndexInGroup = -1
        internal set

    /**
     * 当前变体中的项数
     */
    var countInCurrentVariant = -1
        internal set

    /**
     * 当前变体中的索引
     */
    var indexInCurrentVariant = -1
        internal set

    var parentItem: VariantForm? = null

    /**
     * 跨度
     */
    var spanSize: Int = -1
        internal set

    /**
     * 跨度索引
     */
    var spanIndex: Int = -1
        internal set

    internal fun resetInternalValues() {
        groupCount = -1
        groupIndex = -1
        countInGroup = -1
        positionInGroup = -1
        variantIndexInGroup = -1
        countInCurrentVariant = -1
        indexInCurrentVariant = -1
        parentItem = null
    }

    //</editor-fold>

    //<editor-fold desc="联动 linkage">

    /**
     * 联动接口
     */
    internal var linkageBlock: FormLinkageBlock? = null

    /**
     * 设置联动接口
     */
    fun setLinkage(block: FormLinkageBlock?) {
        linkageBlock = block
    }

    //</editor-fold>

    //<editor-fold desc="验证 verification">

    val ignoreVerification: Boolean = false

    /**
     * 执行数据正确性
     */
    @Throws(FormDataVerificationException::class)
    fun executeDataVerification(adapter: FormAdapter) {
        if (ignoreVerification) return
        val isRealVisible = isRealVisible(adapter.isEnabled)
        val isRealEnable = isRealEnable(adapter.isEnabled)
        if (outputMode == FormOutputMode.VISIBLE && !isRealVisible) return
        if (outputMode == FormOutputMode.ENABLED && !isRealEnable) return
        if (outputMode == FormOutputMode.VISIBLE_AND_ENABLED && !isRealVisible && !isRealEnable) return
//        val provider = FormManager.getItemDataProvider(javaClass)
//        if (provider != null) {
//            provider.dataCorrectness(this)
//            return
//        }
        dataVerification()
    }

    /**
     * 数据验证
     */
    @Throws(FormDataVerificationException::class)
    open fun dataVerification() {
        if (required && content == null) throw FormDataVerificationException(id)
    }

    //</editor-fold>

    //<editor-fold desc="输出 output">

    /**
     * 输出模式
     */
    var outputMode: FormOutputMode = FormOutputMode.VISIBLE

    /**
     * 自定义输出接口
     */
    private var customOutputBlock: ((json: JSONObject) -> Unit)? = null

    /**
     * 设置自定义输出监听
     */
    fun customOutput(block: ((json: JSONObject) -> Unit)?) {
        customOutputBlock = block
    }

    /**
     * 执行输出
     */
    fun executeOutput(adapter: FormAdapter, json: JSONObject) {
        val isRealVisible = isRealVisible(adapter.isEnabled)
        val isRealEnable = isRealEnable(adapter.isEnabled)
        if (outputMode == FormOutputMode.VISIBLE && !isRealVisible) return
        if (outputMode == FormOutputMode.ENABLED && !isRealEnable) return
        if (outputMode == FormOutputMode.VISIBLE_AND_ENABLED && !isRealVisible && !isRealEnable) return
        if (customOutputBlock != null) {
            customOutputBlock!!.invoke(json)
            return
        }
//        val provider = FormManager.getItemDataProvider(javaClass)
//        if (provider != null) {
//            provider.output(this, json)
//            return
//        }
        outputData(json)
        outputExtensionData(json)
    }

    /**
     * 输出处理
     */
    protected open fun outputData(json: JSONObject) {
        if (field != null && content != null) json.put(field!!, content)
    }

    /**
     * 扩展输出处理
     */
    protected open fun outputExtensionData(json: JSONObject) {
        extensionFieldAndContent?.forEach {
            json.put(it.key, it.value)
        }
    }

    //</editor-fold>

    //<editor-fold desc="扩展 extend">

    /**
     * 放置扩展内容
     */
    fun putExtensionContent(key: String, value: Any?) {
        if (value != null) {
            if (extensionFieldAndContent == null) extensionFieldAndContent = HashMap()
            extensionFieldAndContent!![key] = value
        } else if (extensionFieldAndContent != null) {
            extensionFieldAndContent!!.remove(key)
            if (extensionFieldAndContent!!.isEmpty()) extensionFieldAndContent = null
        }
    }

    /**
     * 获取扩展内容
     */
    fun getExtensionContent(key: String): Any? = extensionFieldAndContent?.get(key)

    /**
     * 是否有扩展内容
     */
    fun hasExtensionContent(key: String) = extensionFieldAndContent?.containsKey(key) ?: false

    /**
     * 快照扩展字段和内容
     */
    fun snapshotExtensionFieldAndContent() = extensionFieldAndContent ?: emptyMap()

    /**
     * 删除扩展字段
     */
    fun removeExtensionContent(key: String) = extensionFieldAndContent?.remove(key)

    //</editor-fold>
}