package com.chooongg.form.item

import androidx.annotation.IntRange
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormGroupCreateBlock
import com.chooongg.form.FormGroupNameFormatter
import com.chooongg.form.core.R
import com.chooongg.form.data.FormGroupData
import com.chooongg.form.provider.BaseFormProvider
import com.google.android.material.button.MaterialButton
import kotlin.reflect.KClass

class VariantChildDynamicGroup(name: Any?, field: String?) : BaseForm(name, field) {

    private val _groups = mutableListOf<FormGroupData>()

    internal val addButton = InternalFormDynamicAddButton()

    internal var dynamicGroupCreateBlock: FormGroupCreateBlock? = null

    internal var dynamicGroupNameFormatter: FormGroupNameFormatter? = { _, name, index, count ->
        if (count == 1) name else "$name ${index + 1}"
    }

    /**
     * 是否是独立的
     */
    var isIndependent: Boolean = false

    /**
     * 删除是否需要确认操作
     */
    var isHasDeleteConfirm: Boolean = false

    /**
     * 新增按钮样式
     */
    var addButtonStyle: FormButton.ButtonStyle = FormButton.ButtonStyle.TONAL

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
    var maxGroupCount: Int = 1

    /**
     * 初始化组
     */
    fun <T> initGroups(list: Iterable<T>, block: FormGroupData.(T) -> Unit) {
        list.forEach { _groups.add(FormGroupData().apply { block.invoke(this, it) }) }
    }

    /**
     * 添加组
     */
    fun addGroup(block: FormGroupData.() -> Unit) {
        _groups.add(FormGroupData().apply(block))
    }

    fun getGroups(): MutableList<FormGroupData> = _groups

    fun clearGroups() = _groups.clear()

    /**
     * 动态片段组创建器
     */
    fun dynamicGroupCreator(block: FormGroupCreateBlock?) {
        dynamicGroupCreateBlock = block
    }

    /**
     * 动态片段名称格式工具
     */
    fun dynamicPartNameFormatter(block: FormGroupNameFormatter?) {
        dynamicGroupNameFormatter = block
    }

    override fun initValue(value: Any?) {
        if (dynamicGroupCreateBlock != null) {
            while (getGroups().size < minGroupCount) {
                val groupData = FormGroupData()
                dynamicGroupCreateBlock!!.invoke(groupData)
                getGroups().add(groupData)
            }
        }
    }

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> {
        throw IllegalStateException("This item does not require a provider!")
    }
}