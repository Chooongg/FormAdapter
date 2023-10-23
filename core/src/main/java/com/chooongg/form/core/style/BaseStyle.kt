package com.chooongg.form.core.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.typeset.BaseTypeset

abstract class BaseStyle(val typeset: BaseTypeset?) {

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    abstract fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup)

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
        return other is BaseStyle && javaClass == other.javaClass && typeset == other.typeset
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}