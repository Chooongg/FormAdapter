package com.chooongg.form.core.menu

import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.chooongg.form.core.CacheCleanable
import com.chooongg.form.core.FormColorStateListBlock

class FormMenuItem : CacheCleanable {

    /**
     * 文本
     */
    var text: CharSequence? = null

    /**
     * 文本资源ID
     */
    @StringRes
    var textResId: Int? = null

    /**
     * 图标资源ID
     */
    @DrawableRes
    var iconResId: Int? = null

    var iconTint: FormColorStateListBlock? = null

    var iconTintCache: ColorStateList? = null

    override fun cleanCache() {
        iconTintCache = null
    }
}