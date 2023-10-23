package com.chooongg.form.core

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.utils.ext.inputMethodManager
import com.chooongg.utils.ext.resDimensionPixelSize

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var formMarginStart: Int = 0
        private set

    var formMarginTop: Int = 0
        private set

    var formMarginEnd: Int = 0
        private set

    var formMarginBottom: Int = 0
        private set

    init {
        clipChildren = false
        clipToPadding = false
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.FormView, defStyleAttr, 0
        )
        formMarginStart = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginStart,
            context.resDimensionPixelSize(R.dimen.formInsideStart)
        )
        formMarginTop = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginTop,
            context.resDimensionPixelSize(R.dimen.formInsideTop)
        )
        formMarginEnd = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginEnd,
            context.resDimensionPixelSize(R.dimen.formInsideStart)
        )
        formMarginBottom = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginBottom,
            context.resDimensionPixelSize(R.dimen.formInsideBottom)
        )
        a.recycle()
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    SCROLL_STATE_IDLE -> {
                        recyclerView.focusedChild?.clearFocus()
                        context.inputMethodManager.hideSoftInputFromWindow(
                            recyclerView.windowToken, 0
                        )
                    }
                }
            }
        })
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        (adapter as? FormAdapter)?.isEnabled = enabled
    }

    fun setFormAdapter(formAdapter: FormAdapter?) {
        adapter = formAdapter
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
    }

    fun setFormMargin(start: Int, top: Int, end: Int, bottom: Int) {
        formMarginStart = start
        formMarginTop = top
        formMarginEnd = end
        formMarginBottom = bottom
        updateFormMargin4LayoutManager()
    }

    fun updateFormMargin(
        start: Int = formMarginStart,
        top: Int = formMarginTop,
        end: Int = formMarginEnd,
        bottom: Int = formMarginBottom
    ) {
        formMarginStart = start
        formMarginTop = top
        formMarginEnd = end
        formMarginBottom = bottom
        updateFormMargin4LayoutManager()
    }

    private fun updateFormMargin4LayoutManager() {
        (layoutManager as? FormLayoutManager)?.also {
            it.setFormMargin(formMarginStart, formMarginTop, formMarginEnd, formMarginBottom)
        }
    }
}