package com.chooongg.form.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chooongg.form.FormLayoutManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.boundary.FormInsideInfo
import com.chooongg.form.boundary.FormMarginInfo
import com.chooongg.form.core.R
import com.chooongg.form.groupNameProvider.BaseGroupNameProvider
import com.chooongg.form.iconProvider.BaseIconProvider
import com.chooongg.form.item.BaseForm
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.typeset.BaseTypeset

/**
 * 主题
 */
abstract class BaseStyle {

    /**
     * 片段名称提供器
     */
    var partNameProvider: BaseGroupNameProvider = FormManager.Default.groupNameProvider

    /**
     * 图标提供器
     */
    var iconProvider: BaseIconProvider = FormManager.Default.iconProvider

    /**
     * 水平中间的边界类型
     */
    @Boundary.BoundaryInt
    open var horizontalMiddleBoundary: Int = FormManager.Default.horizontalMiddleBoundary

    /**
     * 排版
     */
    var typeset: BaseTypeset? = null

    var marginInfo: FormMarginInfo = FormMarginInfo(0, 0, 0, 0, 0, 0, 0, 0)
        private set
    var insideInfo: FormInsideInfo = FormInsideInfo(0, 0, 0, 0)
        private set

    /**
     * 水平是否是独立项目
     */
    protected open var horizontalIsSeparateItem: Boolean = false

    private var isInstanceMarginInsideInfo = false

    open fun isDecorateNoneItem(): Boolean = true

    internal fun createSizeInfo(adapter: BaseFormPartAdapter) {
        if (isInstanceMarginInsideInfo) return
        val recyclerView = adapter.formAdapter.recyclerView ?: return
        val layoutManager = recyclerView.layoutManager as? FormLayoutManager ?: return
        var start: Int? = layoutManager.formMarginStart
        if (start == -1) start = null
        var end: Int? = layoutManager.formMarginEnd
        if (end == -1) end = null
        marginInfo = onCreateMarginInfo(recyclerView.context, start, end)
        insideInfo = onCreateInsideInfo(recyclerView.context)
        isInstanceMarginInsideInfo = true
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

    internal fun getHorizontalIsSeparateItem() = horizontalIsSeparateItem

    override fun equals(other: Any?): Boolean {
        if (other !is BaseStyle) return false
        if (javaClass != other.javaClass) return false
        if (partNameProvider != other.partNameProvider) return false
        if (typeset != other.typeset) return false
        if (horizontalIsSeparateItem != other.horizontalIsSeparateItem) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}