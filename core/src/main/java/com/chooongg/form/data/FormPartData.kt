package com.chooongg.form.data

import androidx.annotation.MenuRes
import com.chooongg.form.FormMenuCreateOptionBlock
import com.chooongg.form.enum.FormEnableMode
import com.chooongg.form.enum.FormVisibilityMode

class FormPartData : FormGroupData(), BaseFormPartData {

    private var menuCreateOptionCallback: FormMenuCreateOptionBlock? = null

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
    override var partName: Any? = null

    /**
     * 菜单
     */
    @MenuRes
    var menu: Int? = null

    /**
     * 菜单显示标题
     */
    var menuShowTitle: Boolean = false

    /**
     * 菜单可见模式
     */
    var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ENABLED

    /**
     * 菜单启用模式
     */
    var menuEnableMode: FormEnableMode = FormEnableMode.ENABLED

    /**
     * 菜单创建选项时的监听
     */
    fun menuCreateOptionCallback(block: FormMenuCreateOptionBlock?) {
        menuCreateOptionCallback = block
    }

    fun getMenuCreateOptionCallback() = menuCreateOptionCallback
}