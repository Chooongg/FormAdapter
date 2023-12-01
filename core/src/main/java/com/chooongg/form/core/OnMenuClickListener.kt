package com.chooongg.form.core

import android.view.MenuItem
import com.chooongg.form.core.item.BaseForm

interface OnMenuClickListener {
    fun onMenuClick(menu: MenuItem, item: BaseForm): Boolean
}