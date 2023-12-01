package com.chooongg.form.core.item

import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import com.chooongg.form.core.CacheCleanable
import com.chooongg.form.core.OnMenuClickListener
import com.chooongg.form.core.enum.FormEnableMode
import com.chooongg.form.core.enum.FormVisibilityMode
import java.util.UUID

abstract class AbstractForm : CacheCleanable {

    /**
     * 表单项唯一标识
     */
    open val id = UUID.randomUUID().toString()

    /**
     * 可见模式
     */
    var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 启用模式
     */
    var enableMode: FormEnableMode = FormEnableMode.ENABLED

    /**
     * 菜单
     */
    @MenuRes
    var menu: Int? = null

    /**
     * 菜单显示标题
     */
    var menuShowTitle: Boolean = false

    internal var menuCreateOptionCallback: (MenuBuilder.() -> Unit)? = null

    /**
     * 菜单可见模式
     */
    var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ENABLED

    /**
     * 菜单启用模式
     */
    var menuEnableMode: FormEnableMode = FormEnableMode.ENABLED

    private var menuClickListener: OnMenuClickListener? = null

    /**
     * 真实的可见性
     */
    fun isRealVisible(isEnabled: Boolean): Boolean {
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ENABLED -> isEnabled
            FormVisibilityMode.DISABLED -> !isEnabled
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 真实的可用性
     */
    fun isRealEnable(isEnabled: Boolean): Boolean {
        return when (enableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ENABLED -> isEnabled
            FormEnableMode.DISABLED -> !isEnabled
            FormEnableMode.NEVER -> false
        }
    }

    /**
     * 菜单创建选项时的监听
     */
    fun menuCreateOptionCallback(block: (MenuBuilder.() -> Unit)?) {
        menuCreateOptionCallback = block
    }

    fun getMenuCreateOptionCallback() = menuCreateOptionCallback

    fun onMenuClickListener(menuClickListener: OnMenuClickListener?) {
        this.menuClickListener = menuClickListener
    }

    fun getMenuClickListener() = menuClickListener

    /**
     * 真实的菜单可见性
     */
    fun isRealMenuVisible(isEnabled: Boolean): Boolean {
        return when (menuVisibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ENABLED -> isEnabled
            FormVisibilityMode.DISABLED -> !isEnabled
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 真实的菜单可用性
     */
    fun isRealMenuEnable(isEnabled: Boolean): Boolean {
        return when (menuEnableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ENABLED -> isEnabled
            FormEnableMode.DISABLED -> !isEnabled
            FormEnableMode.NEVER -> false
        }
    }

    override fun cleanCache() {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractForm) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()
}