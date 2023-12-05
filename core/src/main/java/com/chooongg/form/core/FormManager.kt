package com.chooongg.form.core

import android.content.res.Resources
import android.view.Gravity
import androidx.annotation.GravityInt
import androidx.annotation.IntRange
import androidx.annotation.Px
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.error.DefaultErrorFormatter
import com.chooongg.form.core.error.ErrorFormatter
import com.chooongg.form.core.format.BaseNameFormatter
import com.chooongg.form.core.format.NormalNameFormatter
import com.chooongg.form.core.groupNameProvider.BaseGroupNameProvider
import com.chooongg.form.core.groupNameProvider.DefaultGroupNameProvider
import com.chooongg.form.core.iconProvider.BaseIconProvider
import com.chooongg.form.core.iconProvider.DefaultIconProvider
import com.chooongg.form.core.inputMode.InputMode
import com.chooongg.form.core.inputMode.InputModeText
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

        var nameFormatter: BaseNameFormatter = NormalNameFormatter()

        var typeset: BaseTypeset = HorizontalTypeset()

        var groupNameProvider: BaseGroupNameProvider = DefaultGroupNameProvider()

        var iconProvider: BaseIconProvider = DefaultIconProvider()

        var errorFormatter: ErrorFormatter = DefaultErrorFormatter()

        var inputMode: InputMode = InputModeText()
    }

    fun defaultConfig(block: Default.() -> Unit) = apply {
        block(Default)
    }
}