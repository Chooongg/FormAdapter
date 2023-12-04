package com.chooongg.form.core.data

import androidx.annotation.MenuRes
import com.chooongg.form.core.FormMenuCreateOptionBlock
import com.chooongg.form.core.enum.FormEnableMode
import com.chooongg.form.core.enum.FormVisibilityMode

class FormPartData : FormGroupData(), BaseFormPartData {

    override var isEnablePart: Boolean = true

    override var partField: String? = null

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

    private var menuCreateOptionCallback: FormMenuCreateOptionBlock? = null

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