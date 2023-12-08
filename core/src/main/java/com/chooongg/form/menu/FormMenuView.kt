package com.chooongg.form.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
import androidx.core.content.res.use
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.FormMenuCreateOptionBlock
import com.chooongg.form.FormUtils
import com.chooongg.form.core.R
import com.chooongg.form.getTextAppearance
import com.chooongg.form.style.BaseStyle
import com.google.android.material.button.MaterialButton

@SuppressLint("RestrictedApi", "ViewConstructor")
class FormMenuView constructor(
    context: Context,
    style: BaseStyle
) : RecyclerView(context) {

    private val menuAdapter = Adapter(style)

    private val menu = MenuBuilder(context)

    init {
        overScrollMode = View.OVER_SCROLL_NEVER
        isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = menuAdapter
    }

    fun inflateMenu(
        @MenuRes menuRes: Int,
        enabled: Boolean,
        menuCreateOptionCallback: FormMenuCreateOptionBlock?,
        onMenuItemClickListener: OnMenuItemClickListener?
    ) {
        menu.clearAll()
        MenuInflater(context).inflate(menuRes, menu)
        menuCreateOptionCallback?.invoke(menu)
        val items = ArrayList<MenuItemImpl>()
        menu.visibleItems.forEach {
            items.add(it)
        }
        menuAdapter.setMenu(items, enabled, onMenuItemClickListener)
        visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
    }

    fun clearMenu() {
        menu.clearAll()
        menuAdapter.setMenu(null, false, null)
        visibility = View.GONE
    }

    private class Adapter(private val style: BaseStyle) : RecyclerView.Adapter<Adapter.Holder>() {

        private var items = ArrayList<MenuItemImpl>()

        private var enabled: Boolean = false

        private var onMenuClickListener: OnMenuItemClickListener? = null

        @SuppressLint("NotifyDataSetChanged")
        fun setMenu(
            items: ArrayList<MenuItemImpl>?,
            enabled: Boolean,
            onMenuClickListener: OnMenuItemClickListener?,
        ) {
            this.items = items ?: ArrayList()
            this.enabled = enabled
            this.onMenuClickListener = onMenuClickListener
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(
                MaterialButton(
                    parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
                ).apply {
                    insetTop = 0
                    insetBottom = 0
                    minWidth = 0
                    minHeight = 0
                    minimumWidth = 0
                    minimumHeight = 0
                    iconPadding = 0
                    setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                    setPaddingRelative(
                        style.insideInfo.middleStart, style.insideInfo.middleTop,
                        style.insideInfo.middleEnd, style.insideInfo.middleBottom
                    )
                    iconSize = FormUtils.getFontHeight(this)
                })
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = items[position]
            with(holder.itemView as MaterialButton) {
                isEnabled = enabled && item.isEnabled
                icon = item.icon
                text = if (icon == null) item.titleCondensed else null
                iconTint = item.iconTintList ?: ColorStateList.valueOf(
                    context.obtainStyledAttributes(
                        intArrayOf(com.google.android.material.R.attr.colorOutline)
                    ).use { it.getColor(0, Color.GRAY) }
                )
                iconTintMode = item.iconTintMode
                ViewCompat.setTooltipText(this, item.tooltipText ?: item.title)
                setOnClickListener { onMenuClickListener?.onMenuItemClick(item) }
            }
        }

        private class Holder(view: View) : ViewHolder(view)
    }
}