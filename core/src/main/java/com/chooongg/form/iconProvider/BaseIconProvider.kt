package com.chooongg.form.iconProvider

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.google.android.material.button.MaterialButton

/**
 * 图标提供器
 */
abstract class BaseIconProvider {

    /**
     * 设置图片资源
     */
    abstract fun setSrc(imageView: ImageView, icon: Any?)

    /**
     * 设置按钮图标
     */
    abstract fun setButtonIcon(button: MaterialButton, icon: Any?)

    /**
     * 获取 Drawable
     */
    abstract fun getDrawable(context: Context, icon: Any?): Drawable?
}