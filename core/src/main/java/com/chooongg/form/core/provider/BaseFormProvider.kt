package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.style.BaseStyle
import kotlinx.coroutines.CoroutineScope

/**
 * 子类必须实现空参构造方法
 */
abstract class BaseFormProvider {

    abstract fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View

    abstract fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm
    )

    open fun onViewRecycled(holder: FormViewHolder, view: View) {
        holder.job?.cancel()
    }

    open fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
    }

    open fun onViewDetachedFromWindow(holder: FormViewHolder, view: View) {
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BaseFormProvider) return false
        if (javaClass != other.javaClass) return false
        return true
    }
}