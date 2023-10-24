package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import kotlinx.coroutines.CoroutineScope

/**
 * 子类必须实现空参构造方法
 */
abstract class BaseFormProvider {

    abstract fun onCreateViewHolder(parent: ViewGroup): View

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
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}