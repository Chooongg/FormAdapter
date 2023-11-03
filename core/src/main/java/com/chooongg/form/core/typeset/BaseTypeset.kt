package com.chooongg.form.core.typeset

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.GravityInt
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.boundary.FormInsideInfo
import com.chooongg.form.core.enum.FormEmsMode
import com.chooongg.form.core.format.BaseNameFormatter
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.part.BaseFormPartAdapter
import com.chooongg.form.core.style.BaseStyle

abstract class BaseTypeset {

    abstract var emsMode: FormEmsMode

    @Suppress("LeakingThis")
    open var multiColumnEmsMode: FormEmsMode = emsMode

    open var emsSize: Int = FormManager.Default.emsSize

    open var nameFormatter: BaseNameFormatter? = null

    @GravityInt
    open var contentGravity: Int = FormManager.Default.contentGravity

    @GravityInt
    open var multiColumnContentGravity: Int =
        FormManager.Default.multiColumnContentGravity

    abstract fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup,
        item: BaseForm
    )

    protected abstract fun addView(style: BaseStyle, parentView: ViewGroup, child: View)

    fun obtainNameFormatter(): BaseNameFormatter =
        nameFormatter ?: FormManager.Default.nameFormatter

    @GravityInt
    fun obtainContentGravity(holder: FormViewHolder, item: BaseForm): Int {
        val adapter = holder.bindingAdapter as? BaseFormPartAdapter
        return if ((adapter?.formAdapter?.columnCount ?: 1) > 1) {
            item.multiColumnContentGravity ?: multiColumnContentGravity
        } else item.contentGravity ?: contentGravity
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
        layout?.setPaddingRelative(
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

    protected fun setNameViewEms(textView: TextView) {
        when (emsMode) {
            FormEmsMode.NONE -> {
                textView.minWidth = 0
                textView.maxWidth = Int.MAX_VALUE
            }

            FormEmsMode.MIN -> {
                textView.minEms = emsSize
                textView.maxWidth = Int.MAX_VALUE
            }

            FormEmsMode.MAX -> {
                textView.minWidth = 0
                textView.maxEms = emsSize
            }

            FormEmsMode.FIXED -> textView.setEms(emsSize)
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
        if (emsSize != other.emsSize) return false
        if (contentGravity != other.contentGravity) return false
        if (multiColumnContentGravity != other.multiColumnContentGravity) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}