package com.chooongg.form.core.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
import androidx.core.content.res.use
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormMenuCreateOptionBlock
import com.chooongg.form.core.R
import com.chooongg.form.core.getTextAppearance
import com.chooongg.form.core.style.BaseStyle
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
        onMenuItemClickListener: OnMenuItemClickListener?,
        isShowTitle: Boolean
    ) {
        menu.clearAll()
        MenuInflater(context).inflate(menuRes, menu)
        menuCreateOptionCallback?.invoke(menu)
        menuAdapter.setMenu(menu, enabled, onMenuItemClickListener, isShowTitle)
    }

    fun clearMenu() {
        menu.clearAll()
        menuAdapter.setMenu(null, false, null, false)
    }

    private class Adapter(private val style: BaseStyle) : RecyclerView.Adapter<Adapter.Holder>() {

        private val items = ArrayList<MenuItem>()

        private var isShowTitle: Boolean = false

        private var enabled: Boolean = false

        private var onMenuClickListener: OnMenuItemClickListener? = null

        @SuppressLint("NotifyDataSetChanged")
        fun setMenu(
            menu: MenuBuilder?,
            enabled: Boolean,
            onMenuClickListener: OnMenuItemClickListener?,
            isShowTitle: Boolean
        ) {
            this.isShowTitle = isShowTitle
            this.enabled = enabled
            this.onMenuClickListener = onMenuClickListener
            items.clear()
            menu?.visibleItems?.forEach {
                it.shouldShowIcon()
                items.add(it)
            }
            Log.e("Form", "setMenu: ${items.size}")
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(MaterialButton(
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
                val textView = TextView(context).apply {
                    setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                    measure(0, 0)
                }
                iconSize = textView.measuredHeight
            })
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = items[position]
            with(holder.itemView as MaterialButton) {
                isEnabled = enabled && item.isEnabled
                val tempIcon = item.icon
                if (isShowTitle || tempIcon == null) {
                    text = item.titleCondensed
                }
                icon = tempIcon
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    iconTint = item.iconTintList ?: ColorStateList.valueOf(
                        context.obtainStyledAttributes(
                            intArrayOf(com.google.android.material.R.attr.colorOutline)
                        ).use { it.getColor(0, Color.GRAY) }
                    )
                    iconTintMode = item.iconTintMode
                    tooltipText = item.tooltipText ?: item.title
                }
                setOnClickListener { onMenuClickListener?.onMenuItemClick(item) }
            }
        }

        private class Holder(view: View) : ViewHolder(view)
    }
}