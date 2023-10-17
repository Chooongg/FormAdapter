package com.chooongg.form.core.menu

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("RestrictedApi")
class FormMenuView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        adapter = Adapter()
    }

    private class Adapter : RecyclerView.Adapter<Adapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(TextView(parent.context))
        }

        override fun getItemCount(): Int {
            return 0
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
        }

        private class Holder(view: View) : ViewHolder(view) {

        }
    }
}