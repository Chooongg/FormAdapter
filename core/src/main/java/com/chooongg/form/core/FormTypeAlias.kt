package com.chooongg.form.core

import android.content.Context
import android.content.res.ColorStateList
import com.chooongg.form.core.data.FormGroupData
import com.chooongg.form.core.item.LinkageForm
import com.chooongg.form.core.menu.FormMenuItem

typealias FormColorBlock = Context.() -> Int

typealias FormColorStateListBlock = Context.() -> ColorStateList

typealias FormMenuBlock = FormMenuItem.() -> Unit

typealias FormGroupCreateBlock = FormGroupData.() -> Unit

typealias FormPartNameFormatter = (context: Context, name: CharSequence?, index: Int) -> CharSequence

typealias FormLinkageBlock = (linkage: LinkageForm, field: String?, content: Any?) -> Unit