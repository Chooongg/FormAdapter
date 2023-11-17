package com.chooongg.form.core.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.LinkageForm
import com.chooongg.form.core.part.BaseFormPartAdapter
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
        item: BaseForm,
        enabled: Boolean
    )

    open fun onBindViewHolderUpdate(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) = Unit

    open fun onBindViewHolderOtherPayload(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean,
        payload: Any
    ) = Unit

    open fun onViewRecycled(holder: FormViewHolder, view: View) {
        holder.job?.cancel()
    }

    open fun onViewAttachedToWindow(holder: FormViewHolder, view: View) = Unit
    open fun onViewDetachedFromWindow(holder: FormViewHolder, view: View) = Unit

    open fun changeContentAndNotifyLinkage(
        holder: FormViewHolder,
        item: BaseForm,
        content: Any?
    ) {
        if (item.content != content) {
            item.content = content
            notifyLinkage(holder, item, item.field, content)
        }
    }

    fun changeExtensionAndNotifyLinkage(
        holder: FormViewHolder,
        item: BaseForm,
        field: String,
        content: Any?
    ) {
        if (item.getExtensionContent(field) != content) {
            item.putExtensionContent(field, content)
            notifyLinkage(holder, item, field, content)
        }
    }

    fun notifyLinkage(
        holder: FormViewHolder,
        item: BaseForm,
        field: String?,
        content: Any?
    ) {
        item.linkageBlock?.invoke(
            LinkageForm(holder.bindingAdapter as? BaseFormPartAdapter),
            field,
            content
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BaseFormProvider) return false
        if (javaClass != other.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}