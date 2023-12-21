package com.chooongg.form.typeset

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.GravityInt
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.boundary.FormInsideInfo
import com.chooongg.form.enum.FormEmsMode
import com.chooongg.form.format.BaseNameFormatter
import com.chooongg.form.item.BaseForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.style.BaseStyle

/**
 * 排版
 */
abstract class BaseTypeset {

    /**
     * 名称格式化工具
     */
    open var nameFormatter: BaseNameFormatter? = null

    /**
     * 名称Ems模式
     */
    abstract var emsMode: FormEmsMode

    /**
     * 多列时Ems模式
     */
    abstract var multiColumnEmsMode: FormEmsMode

    /**
     * ems数值
     */
    open var emsSize: Int = FormManager.Default.emsSize

    /**
     * 多列时ems数值
     */
    open var multiColumnEmsSize: Int = FormManager.Default.emsSize

    /**
     * 内容重力
     */
    @GravityInt
    open var contentGravity: Int = FormManager.Default.contentGravity

    /**
     * 多列时内容重力
     */
    @GravityInt
    open var multiColumnContentGravity: Int =
        FormManager.Default.multiColumnContentGravity

    abstract fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup?

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup,
        item: BaseForm,
        enabled: Boolean
    )

    protected abstract fun addView(style: BaseStyle, parentView: ViewGroup, child: View)

    fun obtainNameFormatter(): BaseNameFormatter =
        nameFormatter ?: FormManager.Default.nameFormatter

    @GravityInt
    fun obtainContentGravity(holder: FormViewHolder, item: BaseForm): Int {
        val adapter = holder.bindingAdapter as? BaseFormPartAdapter
        return if ((adapter?.columnCount ?: adapter?.formAdapter?.columnCount ?: 1) > 1) {
            item.multiColumnContentGravity ?: item.gravity ?: multiColumnContentGravity
        } else item.contentGravity ?: item.gravity ?: contentGravity
    }


    fun executeAddView(style: BaseStyle, parentView: ViewGroup?, child: View?) {
        if (parentView != null && child != null) {
            addView(style, parentView, child)
        }
    }

    open fun setTypesetLayoutPadding(
        holder: FormViewHolder,
        layout: ViewGroup?,
        insideInfo: FormInsideInfo,
        item: BaseForm
    ) {
        if (item.preventAdjustmentBoundaries) return
        (layout ?: holder.itemView as? ViewGroup)?.setPaddingRelative(
            when (item.insideBoundary.start) {
                Boundary.GLOBAL -> insideInfo.start - insideInfo.middleStart
                else -> 0
            }, when (item.insideBoundary.top) {
                Boundary.GLOBAL -> insideInfo.top - insideInfo.middleTop
                else -> 0
            }, when (item.insideBoundary.end) {
                Boundary.GLOBAL -> insideInfo.end - insideInfo.middleEnd
                else -> 0
            }, when (item.insideBoundary.bottom) {
                Boundary.GLOBAL -> insideInfo.bottom - insideInfo.middleBottom
                else -> 0
            }
        )
    }

    protected fun setNameViewEms(holder: FormViewHolder, textView: TextView) {
        val adapter = holder.bindingAdapter as? BaseFormPartAdapter
        val isMultiColumn = (adapter?.formAdapter?.columnCount ?: 1) > 1
        when (if (isMultiColumn) multiColumnEmsMode else emsMode) {
            FormEmsMode.NONE -> {
                textView.minWidth = 0
                textView.maxWidth = Int.MAX_VALUE
            }

            FormEmsMode.MIN -> {
                textView.minEms = if (isMultiColumn) multiColumnEmsSize else emsSize
                textView.maxWidth = Int.MAX_VALUE
            }

            FormEmsMode.MAX -> {
                textView.minWidth = 0
                textView.maxEms = if (isMultiColumn) multiColumnEmsSize else emsSize
            }

            FormEmsMode.FIXED -> textView.setEms(if (isMultiColumn) multiColumnEmsSize else emsSize)
        }
    }

    open fun onViewRecycled(holder: FormViewHolder, layout: ViewGroup?) = Unit
    open fun onViewAttachedToWindow(holder: FormViewHolder, layout: ViewGroup?) = Unit
    open fun onViewDetachedFromWindow(holder: FormViewHolder, layout: ViewGroup?) = Unit

    override fun equals(other: Any?): Boolean {
        if (other !is BaseTypeset) return false
        if (javaClass != other.javaClass) return false
        if (nameFormatter != other.nameFormatter) return false
        if (emsMode != other.emsMode) return false
        if (multiColumnEmsMode != other.multiColumnEmsMode) return false
        if (emsSize != other.emsSize) return false
        if (multiColumnEmsSize != other.multiColumnEmsSize) return false
        if (contentGravity != other.contentGravity) return false
        if (multiColumnContentGravity != other.multiColumnContentGravity) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}