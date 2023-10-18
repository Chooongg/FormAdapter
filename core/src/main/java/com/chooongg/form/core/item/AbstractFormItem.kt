package com.chooongg.form.core.item

import com.chooongg.form.core.CacheCleanable
import com.chooongg.form.core.FormMenuBlock
import com.chooongg.form.core.enum.FormEnableMode
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.core.menu.FormMenuItem
import java.util.UUID

open class AbstractFormItem : CacheCleanable {

    /**
     * 表单项唯一标识
     */
    val id = UUID.randomUUID().toString()

    /**
     * 可见模式
     */
    var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 启用模式
     */
    var enableMode: FormEnableMode = FormEnableMode.ALWAYS

    protected var menuInternal: FormMenuItem? = null

    protected var menu: FormMenuItem? = null

    protected var menu2: FormMenuItem? = null

    fun menu(block: FormMenuBlock?) {
        menu = if (block != null) {
            FormMenuItem().apply(block)
        } else null
    }

    fun menu2(block: FormMenuBlock?) {
        menu2 = if (block != null) {
            FormMenuItem().apply(block)
        } else null
    }

    override fun cleanCache() {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractFormItem) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()
}