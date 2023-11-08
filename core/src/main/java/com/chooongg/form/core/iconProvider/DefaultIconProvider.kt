package com.chooongg.form.core.iconProvider

import android.widget.ImageView
import com.google.android.material.button.MaterialButton

class DefaultIconProvider : BaseIconProvider() {

    override fun setSrc(imageView: ImageView, icon: Any?) {
        when (icon) {
            Int -> imageView.setImageResource(icon as Int)
            else -> imageView.setImageDrawable(null)
        }
    }

    override fun setButtonIcon(button: MaterialButton, icon: Any?) {
        when (icon) {
            Int -> button.setIconResource(icon as Int)
            else -> button.icon = null
        }
    }
}