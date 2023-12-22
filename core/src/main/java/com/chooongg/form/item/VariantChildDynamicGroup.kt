package com.chooongg.form.item

import androidx.annotation.IntRange
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormChildGroupCreateBlock
import com.chooongg.form.FormGroupNameFormatter
import com.chooongg.form.core.R
import com.chooongg.form.provider.VariantChildDynamicGroupProvider
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.style.NegativePaddingStyle
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.EmptyTypeset
import com.google.android.material.button.MaterialButton

class VariantChildDynamicGroup(name: Any?, field: String?) : VariantBaseForm(name, field) {

    private val _groups = mutableListOf<VariantChildGroup>()

    internal var addButton = InternalFormDynamicAddButton()

    internal var dynamicGroupCreateBlock: FormChildGroupCreateBlock? = null

    internal var dynamicGroupNameFormatter: FormGroupNameFormatter = { _, name, index, count ->
        if (count == 1) name else "$name ${index + 1}"
    }

    /**
     * 新增按钮样式
     */
    var addButtonStyle: FormButton.ButtonStyle = FormButton.ButtonStyle.TEXT

    /**
     * 新增图标
     */
    var addIcon: Any? = R.drawable.ic_form_add

    /**
     * 新增图标重力
     */
    @MaterialButton.IconGravity
    var addIconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    /**
     * 最小组数量
     */
    @IntRange(from = 0)
    var minGroupCount: Int = 1

    /**
     * 最大组数量
     */
    @IntRange(from = 1)
    var maxGroupCount: Int = 10

    override var loneLine: Boolean = true

    override var style: BaseStyle = NegativePaddingStyle()

    override var typeset: BaseTypeset? = EmptyTypeset()

    /**
     * 初始化组
     */
    fun <T> initGroups(list: Iterable<T>, block: VariantChildGroup.(T) -> Unit) {
        list.forEach { _groups.add(VariantChildGroup(null, null).apply { block.invoke(this, it) }) }
    }

    /**
     * 添加组
     */
    fun addGroup(block: VariantChildGroup.() -> Unit) {
        _groups.add(VariantChildGroup(null, null).apply(block))
    }

    fun getGroups(): MutableList<VariantChildGroup> = _groups

    fun clearGroups() = _groups.clear()

    /**
     * 动态片段组创建器
     */
    fun setDynamicGroupCreator(block: FormChildGroupCreateBlock?) {
        dynamicGroupCreateBlock = block
    }

    /**
     * 动态片段名称格式工具
     */
    fun setDynamicPartNameFormatter(block: FormGroupNameFormatter) {
        dynamicGroupNameFormatter = block
    }

    override fun initValue(value: Any?) {
        if (name == null) {
            name = R.string.formDefaultChildGroupName
        }
    }

    override fun getColumn(count: Int, layoutColumn: Int): Int = layoutColumn

    override fun getProvider(adapter: FormAdapter) = VariantChildDynamicGroupProvider::class
}