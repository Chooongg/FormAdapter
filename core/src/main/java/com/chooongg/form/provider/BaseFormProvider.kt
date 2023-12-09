package com.chooongg.form.provider

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.BaseOptionForm
import com.chooongg.form.item.LinkageForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.style.BaseStyle
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
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
        val adapter = holder.bindingAdapter as? BaseFormPartAdapter ?: return
        item.getLinkageBlock()?.invoke(LinkageForm(adapter), field, content)
    }

    protected fun loadOption(holder: FormViewHolder, item: BaseForm?) {
        val itemOption = item as? BaseOptionForm<*>
        if (itemOption?.isNeedToLoadOption(holder) == true) {
            val adapter = holder.bindingAdapter as? BaseFormPartAdapter ?: return
            item.loadOption(holder) {
                holder.itemView.post {
                    val position = adapter.indexOfShow(item)
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
        val shapeResId =
            holder.itemView.context.obtainStyledAttributes(intArrayOf(R.attr.formShapeAppearanceCorner))
                .use {
                    it.getResourceId(
                        0,
                        com.google.android.material.R.style.ShapeAppearance_Material3_Corner_Medium
                    )
                }
        val shapeDrawable = MaterialShapeDrawable(
            ShapeAppearanceModel.builder(
                holder.itemView.context,
                shapeResId,
                0
            ).build()
        )
        val color = holder.itemView.context
            .obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorError))
            .use { it.getColor(0, Color.GRAY) }
        shapeDrawable.fillColor = ColorStateList.valueOf((44 shl 24) + (0x00ffffff and color))
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