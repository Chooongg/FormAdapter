package com.chooongg.form.core

import androidx.annotation.Px
import com.chooongg.form.core.boundary.Boundary
import com.chooongg.utils.ext.dp2px

object FormManager {
    object Default {
        @Px
        var minWidth: Int = dp2px(300f)

        var horizontalDividerType = Boundary.LOCAL
        var singleLineDividerType = Boundary.GLOBAL
    }
}