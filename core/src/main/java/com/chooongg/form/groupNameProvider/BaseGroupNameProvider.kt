package com.chooongg.form.groupNameProvider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.item.InternalFormGroupName
import com.chooongg.form.style.BaseStyle

abstract class BaseGroupNameProvider {

    /**
     * 创建分组标题
     */
    abstract fun onCreateGroupName(style: BaseStyle, parent: ViewGroup): View

    /**
     * 绑定分组标题
     */
    abstract fun onBindGroupName(
        holder: FormViewHolder,
        view: View,
        item: InternalFormGroupName,
        enabled: Boolean
    )

    override fun equals(other: Any?): Boolean {
        if (other !is BaseGroupNameProvider) return false
        if (javaClass != other.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}