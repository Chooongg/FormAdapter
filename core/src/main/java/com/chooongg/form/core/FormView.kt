package com.chooongg.form.core

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var formMarginStart: Int = 0
        private set

    var formMarginEnd: Int = 0
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
        formMarginEnd = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginEnd,
            resources.getDimensionPixelSize(R.dimen.formMarginEnd)
        )
        a.recycle()
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    SCROLL_STATE_IDLE -> {
                        recyclerView.focusedChild?.clearFocus()
                        FormUtils.hideIme(recyclerView)
                    }
                }
            }
        })
    }

    @Deprecated("Under normal circumstances, the View enable status cannot be modified")
    override fun setEnabled(enabled: Boolean) {
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

    fun setFormMargin(start: Int, end: Int) {
        formMarginStart = start
        formMarginEnd = end
        updateFormMargin4LayoutManager()
    }

    fun updateFormMargin(
        start: Int = formMarginStart,
        end: Int = formMarginEnd,
    ) {
        formMarginStart = start
        formMarginEnd = end
        updateFormMargin4LayoutManager()
    }

    private fun updateFormMargin4LayoutManager() {
        (layoutManager as? FormLayoutManager)?.also {
            it.setFormMargin(formMarginStart, formMarginEnd)
        }
    }
}