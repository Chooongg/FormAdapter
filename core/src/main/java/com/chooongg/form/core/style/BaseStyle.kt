package com.chooongg.form.core.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.boundary.FormInsideInfo
import com.chooongg.form.core.boundary.FormMarginInfo
import com.chooongg.form.core.typeset.BaseTypeset

abstract class BaseStyle(val typeset: BaseTypeset?) {

    var marginInfo: FormMarginInfo = FormMarginInfo(0, 0, 0, 0)
        private set
    var insideInfo: FormInsideInfo = FormInsideInfo(0, 0, 0, 0)
        private set

    internal fun createSizeInfo(context: Context) {
        marginInfo = onCreateMarginInfo(context)
        insideInfo = onCreateInsideInfo(context)
    }

    protected open fun onCreateMarginInfo(context: Context): FormMarginInfo {
        return FormMarginInfo(
            context.resources.getDimensionPixelSize(R.dimen.formMarginStart),
            context.resources.getDimensionPixelSize(R.dimen.formMarginTop),
            context.resources.getDimensionPixelSize(R.dimen.formMarginEnd),
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

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup?
    )

    protected abstract fun addView(parentView: ViewGroup, child: View)

    open fun onViewRecycled(holder: FormViewHolder, layout: ViewGroup?) {
    }

    open fun onViewAttachedToWindow(holder: FormViewHolder, layout: ViewGroup?) {
    }

    open fun onViewDetachedFromWindow(holder: FormViewHolder, layout: ViewGroup?) {
    }

    fun executeAddView(parentView: ViewGroup?, child: View?) {
        if (parentView != null && child != null) {
            addView(parentView, child)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is BaseStyle && javaClass == other.javaClass && typeset == other.typeset
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}