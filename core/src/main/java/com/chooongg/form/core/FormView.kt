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

    private val formAdapter = FormAdapter(isEnabled)

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_DRAGGING) recyclerView.focusedChild?.clearFocus()
            }
        })
        recycledViewPool
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        formAdapter.isEnabled = enabled
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
    }
}