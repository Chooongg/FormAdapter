package com.chooongg.form.core.iconProvider

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.google.android.material.button.MaterialButton

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