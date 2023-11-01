package com.chooongg.form.core.nameProvider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.InternalFormPartName
import com.chooongg.form.core.style.BaseStyle

abstract class BasePartNameProvider {

    /**
     * 创建分组标题
     */
    abstract fun onCreatePartName(style: BaseStyle, parent: ViewGroup): View

    /**
     * 绑定分组标题
     */
    abstract fun onBindPartName(
        holder: FormViewHolder,
        view: View,
        item: InternalFormPartName,
        enabled: Boolean
    )

    override fun equals(other: Any?): Boolean {
        if (other !is BasePartNameProvider) return false
        if (javaClass != other.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}