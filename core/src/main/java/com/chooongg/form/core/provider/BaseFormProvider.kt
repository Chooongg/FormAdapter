package com.chooongg.form.core.provider

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.TransitionDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.BaseOptionForm
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
    ) {
        onBindViewHolder(scope, holder, view, item, enabled)
    }

    open fun onBindViewHolderErrorNotify(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) = errorNotify(holder, item)

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
        holder.itemView.foreground = null
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

    private fun notifyLinkage(
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

    protected fun loadOption(holder: FormViewHolder, item: BaseForm?) {
        val itemOption = item as? BaseOptionForm<*>
        if (itemOption?.isNeedToLoadOption(holder) == true) {
            val adapter = holder.bindingAdapter as? BaseFormPartAdapter ?: return
            item.loadOption(holder) {
                holder.itemView.post {
                    val position = adapter.indexOf(item)
                    if (position >= 0) {
                        adapter.notifyItemChanged(
                            position, BaseOptionForm.CHANGE_OPTION_PAYLOAD_FLAG
                        )
                    }
                }
            }
        }
    }

    open fun errorNotify(holder: FormViewHolder, item: BaseForm) {
        if (item.errorNotify == 0L) {
            holder.itemView.foreground = null
            return
        }
        if (System.currentTimeMillis() - item.errorNotify > 5000) {
            item.errorNotify = 0L
            holder.itemView.foreground = null
            return
        }
        item.errorNotify = 0L
        val color = holder.itemView.context
            .obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorPrimary))
            .use { it.getColor(0, Color.GRAY) }
        val corner = holder.itemView.resources.getDimension(R.dimen.formErrorNotifyCorner)
        val shapeDrawable = ShapeDrawable(
            RoundRectShape(
                floatArrayOf(corner, corner, corner, corner, corner, corner, corner, corner),
                null,
                null
            )
        )
        shapeDrawable.paint.color = (66 shl 24) + (0x00ffffff and color)
        val drawable = TransitionDrawable(
            arrayOf(shapeDrawable, ColorDrawable(Color.TRANSPARENT))
        ).apply {
            isCrossFadeEnabled = true
            startTransition(2000)
        }
        holder.itemView.foreground = drawable
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