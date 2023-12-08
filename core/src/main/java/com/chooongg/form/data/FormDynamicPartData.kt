package com.chooongg.form.data

import androidx.annotation.IntRange
import androidx.annotation.MenuRes
import com.chooongg.form.FormGroupCreateBlock
import com.chooongg.form.FormGroupNameFormatter
import com.chooongg.form.FormMenuCreateOptionBlock
import com.chooongg.form.core.R
import com.chooongg.form.enum.FormEnableMode
import com.chooongg.form.enum.FormVisibilityMode
import com.chooongg.form.item.FormButton
import com.google.android.material.button.MaterialButton

class FormDynamicPartData : BaseFormPartData {

    private val _groups = mutableListOf<FormGroupData>()

    private var menuCreateOptionCallback: FormMenuCreateOptionBlock? = null

    internal var dynamicGroupCreateBlock: FormGroupCreateBlock? = null

    internal var dynamicGroupNameFormatter: FormGroupNameFormatter? = { _, name, index, count ->
        if (count == 1) name else "$name ${index + 1}"
    }

    /**
     * 是否启用片段
     */
    override var isEnablePart: Boolean = true

    /**
     * 片段字段
     */
    override var partField: String? = null

    /**
     * 片段名称
     */
    override var partName: Any? = R.string.formDefaultGroupName

    /**
     * 删除是否需要确认操作
     */
    var isHasDeleteConfirm: Boolean = true

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
     * 菜单
     */
    @MenuRes
    var menu: Int? = null

    /**
     * 菜单可见模式
     */
    var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ENABLED

    /**
     * 菜单启用模式
     */
    var menuEnableMode: FormEnableMode = FormEnableMode.ENABLED

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

    /**
     * 菜单创建选项时的监听
     */
    fun menuCreateOptionCallback(block: FormMenuCreateOptionBlock?) {
        menuCreateOptionCallback = block
    }

    fun getMenuCreateOptionCallback() = menuCreateOptionCallback
}