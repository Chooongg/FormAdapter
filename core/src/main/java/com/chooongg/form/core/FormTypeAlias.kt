package com.chooongg.form.core

import android.content.Context
import android.content.res.ColorStateList
import com.chooongg.form.core.menu.FormMenuItem

typealias FormColorBlock = Context.() -> Int

typealias FormColorStateListBlock = Context.() -> ColorStateList

typealias FormMenuBlock = FormMenuItem.() -> Unit