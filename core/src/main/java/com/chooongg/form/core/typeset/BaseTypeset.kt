package com.chooongg.form.core.typeset

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.item.BaseForm

abstract class BaseTypeset {

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    abstract fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup, item: BaseForm)

    protected abstract fun addView(parentView: ViewGroup, child: View)

    open fun onViewRecycled(holder: FormViewHolder, view: View) {
    }

    open fun onViewAttachedToWindow(holder: FormViewHolder, view: View) {
    }

    open fun onViewDetachedFromWindow(holder: FormViewHolder, view: View) {
    }

    fun executeAddView(parentView: ViewGroup?, child: View?) {
        if (parentView != null && child != null) {
            addView(parentView, child)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is BaseTypeset && javaClass == other.javaClass
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}