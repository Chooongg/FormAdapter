package com.chooongg.form.core.typeset

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.GravityInt
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.enum.FormEmsMode
import com.chooongg.form.core.format.BaseNameFormatter
import com.chooongg.form.core.item.BaseForm

abstract class BaseTypeset {

    open var nameFormatter: BaseNameFormatter? = null

    open var emsMode: FormEmsMode = FormEmsMode.NONE

    open var emsSize: Int = FormManager.Default.emsSize

    @GravityInt
    open var contentGravity: Int = FormManager.Default.contentGravity

    @GravityInt
    open var singleLineGravity: Int = FormManager.Default.singleLineContentGravity

    @GravityInt
    open var multiColumnContentGravity: Int = FormManager.Default.multiColumnContentGravity

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup?,
        item: BaseForm
    )

    protected abstract fun addView(parentView: ViewGroup, child: View)

    fun obtainNameFormatter(): BaseNameFormatter =
        nameFormatter ?: FormManager.Default.nameFormatter

    @GravityInt
    open fun obtainContentGravity(): Int {
        return contentGravity
    }

    fun executeAddView(parentView: ViewGroup?, child: View?) {
        if (parentView != null && child != null) {
            addView(parentView, child)
        }
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

            FormEmsMode.FIXED -> {
                textView.minEms = emsSize
                textView.maxEms = emsSize
            }
        }
    }

    open fun onViewRecycled(holder: FormViewHolder, layout: ViewGroup?) = Unit
    open fun onViewAttachedToWindow(holder: FormViewHolder, layout: ViewGroup?) = Unit
    open fun onViewDetachedFromWindow(holder: FormViewHolder, layout: ViewGroup?) = Unit

    override fun equals(other: Any?): Boolean {
        return other is BaseTypeset && javaClass == other.javaClass
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}