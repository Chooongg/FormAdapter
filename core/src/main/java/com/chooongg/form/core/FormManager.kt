package com.chooongg.form.core

import androidx.annotation.Px
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.HorizontalTypeset
import com.chooongg.utils.ext.dp2px

object FormManager {

    object Default {

        var typeset: BaseTypeset = HorizontalTypeset()

        @Px
        var maxWidth: Int = dp2px(300f)

        var horizontalDividerType = Boundary.LOCAL
        var singleLineDividerType = Boundary.GLOBAL
    }

    fun defaultConfig(block: Default.() -> Unit) = apply {
        block(Default)
    }
}