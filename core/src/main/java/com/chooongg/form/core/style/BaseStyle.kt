package com.chooongg.form.core.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormLayoutManager
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.boundary.FormInsideInfo
import com.chooongg.form.core.boundary.FormMarginInfo
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.nameProvider.BasePartNameProvider
import com.chooongg.form.core.typeset.BaseTypeset

abstract class BaseStyle {

    var partNameProvider: BasePartNameProvider = FormManager.Default.partNameProvider

    var typeset: BaseTypeset? = null

    var marginInfo: FormMarginInfo = FormMarginInfo(0, 0, 0, 0, 0, 0, 0, 0)
        private set
    var insideInfo: FormInsideInfo = FormInsideInfo(0, 0, 0, 0)
        private set

    internal fun createSizeInfo(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as? FormLayoutManager
        var start = layoutManager?.formMarginStart
        if (start == -1) start = null
        var end = layoutManager?.formMarginEnd
        if (end == -1) end = null
        marginInfo = onCreateMarginInfo(recyclerView.context, start, end)
        insideInfo = onCreateInsideInfo(recyclerView.context)
    }

    protected open fun onCreateMarginInfo(
        context: Context,
        start: Int?,
        end: Int?
    ): FormMarginInfo {
        return FormMarginInfo(
            start ?: context.resources.getDimensionPixelSize(R.dimen.formMarginStart),
            context.resources.getDimensionPixelSize(R.dimen.formMarginTop),
            end ?: context.resources.getDimensionPixelSize(R.dimen.formMarginEnd),
            context.resources.getDimensionPixelSize(R.dimen.formMarginBottom),
            context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleStart),
            context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleTop),
            context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleEnd),
            context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleBottom)
        )
    }

    protected open fun onCreateInsideInfo(context: Context): FormInsideInfo {
        return FormInsideInfo(
            context.resources.getDimensionPixelSize(R.dimen.formInsideStart),
            context.resources.getDimensionPixelSize(R.dimen.formInsideTop),
            context.resources.getDimensionPixelSize(R.dimen.formInsideEnd),
            context.resources.getDimensionPixelSize(R.dimen.formInsideBottom),
            context.resources.getDimensionPixelSize(R.dimen.formInsideMiddleStart),
            context.resources.getDimensionPixelSize(R.dimen.formInsideMiddleTop),
            context.resources.getDimensionPixelSize(R.dimen.formInsideMiddleEnd),
            context.resources.getDimensionPixelSize(R.dimen.formInsideMiddleBottom)
        )
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    abstract fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm)

    protected abstract fun addView(parentView: ViewGroup, child: View)

    open fun onViewRecycled(holder: FormViewHolder, layout: ViewGroup?) = Unit
    open fun onViewAttachedToWindow(holder: FormViewHolder, layout: ViewGroup?) = Unit
    open fun onViewDetachedFromWindow(holder: FormViewHolder, layout: ViewGroup?) = Unit

    fun executeAddView(parentView: ViewGroup?, child: View?) {
        if (parentView != null && child != null) {
            addView(parentView, child)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BaseStyle) return false
        if (javaClass != other.javaClass) return false
        if (partNameProvider != other.partNameProvider) return false
        if (typeset != other.typeset) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}