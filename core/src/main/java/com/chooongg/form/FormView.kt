package com.chooongg.form

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.R

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    constructor(context: Context, maxItemWidth: Int) : this(context) {
        this.maxItemWidth = maxItemWidth
    }

    private var maxItemWidth: Int = FormManager.Default.maxItemWidth

    init {
        clipChildren = false
        clipToPadding = false
        val a = context.obtainStyledAttributes(attrs, R.styleable.FormView, defStyleAttr, 0)
        val formMarginStart = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginStart,
            resources.getDimensionPixelSize(R.dimen.formMarginStart)
        )
        val formMarginEnd = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginEnd,
            resources.getDimensionPixelSize(R.dimen.formMarginEnd)
        )
        val maxItemWidth = a.getDimensionPixelSize(
            R.styleable.FormView_formMaxItemWidth, maxItemWidth
        )
        a.recycle()
        layoutManager = FormLayoutManager(context, maxItemWidth).apply {
            setFormMargin(formMarginStart, formMarginEnd)
        }
        addItemDecoration(FormItemDecoration())
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

    override fun setEnabled(enabled: Boolean) {
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

    fun setMaxItemWidth(width: Int) {
        (layoutManager as? FormLayoutManager)?.maxItemWidth = width
    }

    fun setFormMargin(
        start: Int,
        end: Int,
    ) {
        (layoutManager as? FormLayoutManager)?.setFormMargin(start, end)
    }

    fun clearPool() {
        if (adapter is FormAdapter) {
            (adapter as FormAdapter).clearPool()
        } else recycledViewPool.clear()
    }
}