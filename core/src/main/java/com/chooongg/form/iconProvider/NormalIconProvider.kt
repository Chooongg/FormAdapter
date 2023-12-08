package com.chooongg.form.iconProvider

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.button.MaterialButton

class NormalIconProvider : BaseIconProvider() {

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

    override fun getDrawable(context: Context, icon: Any?): Drawable? {
        return when (icon) {
            is Drawable -> icon
            is Int -> ResourcesCompat.getDrawable(context.resources, icon, context.theme)
            else -> null
        }
    }
}