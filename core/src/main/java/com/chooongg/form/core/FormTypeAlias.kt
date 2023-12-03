package com.chooongg.form.core

import android.content.Context
import android.content.res.ColorStateList
import android.view.MenuItem
import com.chooongg.form.core.data.FormGroupData
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.BaseOptionForm
import com.chooongg.form.core.item.LinkageForm

typealias FormColorBlock = Context.() -> Int

typealias FormColorStateListBlock = Context.() -> ColorStateList

typealias FormMenuClickBlock = (context: Context, menu: MenuItem, item: BaseForm) -> Boolean

typealias FormMenuClickGlobalBlock = (context: Context, menu: MenuItem, item: BaseForm) -> Unit

typealias FormGroupCreateBlock = FormGroupData.() -> Unit

typealias FormGroupNameFormatter = (context: Context, name: CharSequence?, index: Int, count: Int) -> CharSequence?

typealias FormLinkageBlock = (linkage: LinkageForm, field: String?, content: Any?) -> Unit

typealias FormOptionLoader<T> = suspend (BaseOptionForm<T>) -> List<T>?

typealias FormItemClickBlock = (item: BaseForm) -> Unit

typealias FormSliderFormatter = (Float) -> String