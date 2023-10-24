package com.chooongg.form.core

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RestrictTo
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

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
            resources.getDimensionPixelSize(R.dimen.formMarginStart)
        )
        formMarginTop = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginTop,
            resources.getDimensionPixelSize(R.dimen.formMarginTop)
        )
        formMarginEnd = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginEnd,
            resources.getDimensionPixelSize(R.dimen.formMarginEnd)
        )
        formMarginBottom = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginBottom,
            resources.getDimensionPixelSize(R.dimen.formMarginBottom)
        )
        a.recycle()
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    SCROLL_STATE_IDLE -> {
                        recyclerView.focusedChild?.clearFocus()
                        ContextCompat.getSystemService(context, InputMethodManager::class.java)
                            ?.hideSoftInputFromWindow(recyclerView.windowToken, 0)
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