package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.GravityInt
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormLinkageBlock
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.enum.FormValidateStatus
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.typeset.BaseTypeset
import org.json.JSONObject
import kotlin.reflect.KClass

abstract class BaseForm(
    /**
     * 名称: Int(StringRes), String, CharSequence
     */
    var name: Any?
) : AbstractForm() {

    abstract fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider>

    //<editor-fold desc="基础 Basic">

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, Any?>? = null

    /**
     * 字段
     */
    var field: String? = null

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
     * 验证表单项
     */
    var validate: Boolean = false

    /**
     * 效验的状态
     */
    var validateStatus: FormValidateStatus? = null

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

    //</editor-fold>

    //<editor-fold desc="接口 interface">

    /**
     * 联动接口
     */
    internal var linkageBlock: FormLinkageBlock? = null

    /**
     * 自定义输出接口
     */
    private var customOutputBlock: ((json: JSONObject) -> Unit)? = null

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

    //<editor-fold desc="设置方法 set">

    fun setLinkage(block: FormLinkageBlock?) {
        linkageBlock = block
    }

    //</editor-fold>

    //<editor-fold desc="获取方法 get">

    open fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return FormUtils.getText(context, content)
    }

    //</editor-fold>

    //<editor-fold desc="初始化方法 initialize">

    /**
     * 初始化 content 值
     * 该操作主要作用为修复
     */
    open fun initContentValue(value: Any?) {
    }

    //</editor-fold>

    internal fun resetInternalValues() {
        marginBoundary = Boundary()
        insideBoundary = Boundary()
        groupCount = -1
        groupIndex = -1
        countInGroup = -1
        positionInGroup = -1
        variantIndexInGroup = -1
        countInCurrentVariant = -1
        indexInCurrentVariant = -1
        parentItem = null
    }
}