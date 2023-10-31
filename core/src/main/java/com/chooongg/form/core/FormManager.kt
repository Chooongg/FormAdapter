package com.chooongg.form.core

import android.content.res.Resources
import android.view.Gravity
import androidx.annotation.GravityInt
import androidx.annotation.IntRange
import androidx.annotation.Px
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.format.BaseNameFormatter
import com.chooongg.form.core.format.ColonNameFormatter
import com.chooongg.form.core.nameProvider.BasePartNameProvider
import com.chooongg.form.core.nameProvider.DefaultPartNameProvider
import com.chooongg.form.core.style.BaseStyle
import com.chooongg.form.core.style.NoneNotAlignmentStyle
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.HorizontalTypeset

object FormManager {

    object Default {

        @Px
        var maxWidth: Int = (300 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

        @IntRange(from = 1)
        var emsSize: Int = 5

        @GravityInt
        var contentGravity: Int = Gravity.END

        @GravityInt
        var multiColumnContentGravity: Int = Gravity.NO_GRAVITY

        @Boundary.BoundaryInt
        var horizontalMiddleBoundary: Int = Boundary.MIDDLE

        var nameFormatter: BaseNameFormatter = ColonNameFormatter()

        var typeset: BaseTypeset = HorizontalTypeset()

        var partNameProvider: BasePartNameProvider = DefaultPartNameProvider()

        var style: BaseStyle = NoneNotAlignmentStyle()
    }

    fun defaultConfig(block: Default.() -> Unit) = apply {
        block(Default)
    }
}