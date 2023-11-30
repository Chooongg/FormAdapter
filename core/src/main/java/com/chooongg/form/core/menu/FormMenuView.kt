package com.chooongg.form.core.menu

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("RestrictedApi")
class FormMenuView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val menuAdapter = Adapter()

    private val menu = MenuBuilder(context)

    init {
        adapter = menuAdapter
    }

    fun inflateMenu(@MenuRes menuRes: Int) {
        MenuInflater(context).inflate(menuRes, menu)
        menuAdapter.setMenu(menu)
    }

    fun clearMenu() {
        menu.clearAll()
        menuAdapter.setMenu(null)
    }

    private class Adapter : RecyclerView.Adapter<Adapter.Holder>() {

        private val items = ArrayList<MenuItem>()

        @SuppressLint("NotifyDataSetChanged")
        fun setMenu(menu: MenuBuilder?) {
            items.clear()
            if (menu != null) for (i in 0 until menu.size()) {
                items.add(menu.getItem(i))
            }
            notifyDataSetChanged()
        }

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