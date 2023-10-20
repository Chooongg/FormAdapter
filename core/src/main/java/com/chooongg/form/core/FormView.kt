package com.chooongg.form.core

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.utils.ext.resDimensionPixelSize

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var formPaddingStart: Int = 0
        private set

    var formPaddingTop: Int = 0
        private set

    var formPaddingEnd: Int = 0
        private set

    var formPaddingBottom: Int = 0
        private set

    init {
        clipChildren = false
        clipToPadding = false
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.FormView, defStyleAttr, 0
        )
        formPaddingStart = a.getDimensionPixelSize(
            R.styleable.FormView_formPaddingStart,
            context.resDimensionPixelSize(R.dimen.formPaddingStart)
        )
        formPaddingTop = a.getDimensionPixelSize(
            R.styleable.FormView_formPaddingTop,
            context.resDimensionPixelSize(R.dimen.formPaddingTop)
        )
        formPaddingEnd = a.getDimensionPixelSize(
            R.styleable.FormView_formPaddingEnd,
            context.resDimensionPixelSize(R.dimen.formPaddingStart)
        )
        formPaddingBottom = a.getDimensionPixelSize(
            R.styleable.FormView_formPaddingBottom,
            context.resDimensionPixelSize(R.dimen.formPaddingBottom)
        )
        a.recycle()
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_DRAGGING) recyclerView.focusedChild?.clearFocus()
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

    fun setFormPadding(start: Int, top: Int, end: Int, bottom: Int) {
        formPaddingStart = start
        formPaddingTop = top
        formPaddingEnd = end
        formPaddingBottom = bottom
        updateFormPadding4LayoutManager()
    }

    fun updateFormPadding(
        start: Int = formPaddingStart,
        top: Int = formPaddingTop,
        end: Int = formPaddingEnd,
        bottom: Int = formPaddingBottom
    ) {
        formPaddingStart = start
        formPaddingTop = top
        formPaddingEnd = end
        formPaddingBottom = bottom
        updateFormPadding4LayoutManager()
    }

    private fun updateFormPadding4LayoutManager() {
        (layoutManager as? FormLayoutManager)?.also {
            it.setPadding(formPaddingStart, formPaddingTop, formPaddingEnd, formPaddingBottom)
        }
    }
}