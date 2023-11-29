package com.chooongg.form.core.iconProvider

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.google.android.material.button.MaterialButton

class DefaultIconProvider : BaseIconProvider() {

    override fun setSrc(imageView: ImageView, icon: Any?) {
        when (icon) {
            is Drawable -> imageView.setImageDrawable(icon)
            is Int -> imageView.setImageResource(icon)
            else -> imageView.setImageDrawable(null)
        }
    }

    override fun setButtonIcon(button: MaterialButton, icon: Any?) {
        when (icon) {
            is Drawable -> button.icon = icon
            is Int -> button.setIconResource(icon)
            else -> button.icon = null
        }
    }
}