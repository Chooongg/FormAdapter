package com.chooongg.form.core

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.style.CardElevatedStyle
import com.chooongg.form.core.style.CardFilledStyle
import com.chooongg.form.core.style.CardOutlinedStyle

fun FormAdapter.addCardElevatedPart(
    @DimenRes elevationResId: Int? = null,
    updateAdjacentAdapter: Boolean = false,
    block: FormPartData.() -> Unit
) = addPart(
    CardElevatedStyle { this.elevationResId = elevationResId },
    updateAdjacentAdapter,
    block
)

fun FormAdapter.addCardFilledPart(
    @ColorRes colorResId: Int? = null,
    updateAdjacentAdapter: Boolean = false,
    block: FormPartData.() -> Unit
) = addPart(
    CardFilledStyle { this.colorResId = colorResId },
    updateAdjacentAdapter,
    block
)

fun FormAdapter.addCardOutlinedPart(
    @ColorRes strokeColorResId: Int? = null,
    updateAdjacentAdapter: Boolean = false,
    block: FormPartData.() -> Unit
) = addPart(
    CardOutlinedStyle { this.strokeColorResId = strokeColorResId },
    updateAdjacentAdapter,
    block
)