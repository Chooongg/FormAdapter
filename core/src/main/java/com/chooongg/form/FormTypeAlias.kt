package com.chooongg.form

import android.content.Context
import android.content.res.ColorStateList
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.chooongg.form.data.FormGroupData
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.BaseOptionForm
import com.chooongg.form.item.LinkageForm
import com.chooongg.form.item.VariantChildGroup

/**
 * 颜色 Block
 */
typealias FormColorBlock = Context.() -> Int

/**
 * 颜色状态列表 Block
 */
typealias FormColorStateListBlock = Context.() -> ColorStateList?

/**
 * 菜单点击 Block
 * @return 是否拦截
 */
typealias FormMenuClickBlock = (context: Context, menu: MenuItem, item: BaseForm) -> Boolean

/**
 * 全局菜单点击 Block
 */
typealias FormMenuClickGlobalBlock = (context: Context, menu: MenuItem, item: BaseForm) -> Unit

/**
 * 菜单创建配置 Block
 */
typealias FormMenuCreateOptionBlock = (menu: Menu) -> Unit

/**
 * 组创建 Block
 */
typealias FormGroupCreateBlock = FormGroupData.() -> Unit

/**
 * 组创建 Block
 */
typealias FormChildGroupCreateBlock = VariantChildGroup.() -> Unit

/**
 * 组名称格式化工具 Block
 */
typealias FormGroupNameFormatter = (context: Context, name: CharSequence?, index: Int, count: Int) -> CharSequence?

/**
 * 联动 Block
 */
typealias FormLinkageBlock = (linkage: LinkageForm, field: String?, content: Any?) -> Unit

/**
 * 选项加载 Block
 */
typealias FormOptionLoader<T> = suspend (BaseOptionForm<T>) -> List<T>?

/**
 * 子项点击 Block
 */
typealias FormItemClickBlock = (item: BaseForm, view: View) -> Unit

/**
 * 操作按钮点击 Block
 */
typealias FormOperationButtonClickBlock = (view: View) -> Unit

/**
 * 滑块文字格式化工具 Block
 */
typealias FormSliderFormatter = (Float) -> String