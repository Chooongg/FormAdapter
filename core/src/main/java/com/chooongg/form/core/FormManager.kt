package com.chooongg.form.core

import android.content.res.Resources
import android.view.Gravity
import androidx.annotation.GravityInt
import androidx.annotation.Px
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.format.BaseNameFormatter
import com.chooongg.form.core.format.NormalNameFormatter
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.HorizontalTypeset

object FormManager {

    object Default {

        @Px
        var maxWidth: Int = (300 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

        var typeset: BaseTypeset = HorizontalTypeset()
        var emsSize = 5

        @GravityInt
        var contentGravity = Gravity.NO_GRAVITY

        @GravityInt
        var singleLineContentGravity = contentGravity

        @GravityInt
        var multiColumnContentGravity = Gravity.NO_GRAVITY

        var horizontalMiddleType = Boundary.MIDDLE

        var nameFormatter: BaseNameFormatter = NormalNameFormatter()
    }

    fun defaultConfig(block: Default.() -> Unit) = apply {
        block(Default)
    }
}