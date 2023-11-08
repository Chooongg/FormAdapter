package com.chooongg.form.core.iconProvider

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
}