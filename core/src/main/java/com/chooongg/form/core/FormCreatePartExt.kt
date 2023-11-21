package com.chooongg.form.core

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.chooongg.form.core.data.FormDynamicPartData
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.data.IFormAdapterCreator
import com.chooongg.form.core.style.CardElevatedStyle
import com.chooongg.form.core.style.CardFilledStyle
import com.chooongg.form.core.style.CardOutlinedStyle
import com.chooongg.form.core.style.NotAlignmentStyle

fun IFormAdapterCreator.initNotAlignmentPart(
    block: FormPartData.() -> Unit
) = initPart(NotAlignmentStyle(), block)

fun IFormAdapterCreator.initNotAlignmentDynamicPart(
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(NotAlignmentStyle(), block)

fun IFormAdapterCreator.initCardElevatedPart(
    @DimenRes elevationResId: Int? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardElevatedStyle { this.elevationResId = elevationResId }, block
)

fun IFormAdapterCreator.initCardElevatedDynamicPart(
    @DimenRes elevationResId: Int? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardElevatedStyle { this.elevationResId = elevationResId }, block
)

fun IFormAdapterCreator.initCardFilledPart(
    @ColorRes colorResId: Int? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardFilledStyle { this.colorResId = colorResId }, block
)

fun IFormAdapterCreator.initCardFilledDynamicPart(
    @ColorRes colorResId: Int? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardFilledStyle { this.colorResId = colorResId }, block
)

fun IFormAdapterCreator.initCardOutlinedPart(
    @ColorRes strokeColorResId: Int? = null,
    @DimenRes strokeWidthResId: Int? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardOutlinedStyle {
        this.strokeColorResId = strokeColorResId
        this.strokeWidthResId = strokeWidthResId
    }, block
)

fun IFormAdapterCreator.initCardOutlinedDynamicPart(
    @ColorRes strokeColorResId: Int? = null,
    @DimenRes strokeWidthResId: Int? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardOutlinedStyle {
        this.strokeColorResId = strokeColorResId
        this.strokeWidthResId = strokeWidthResId
    }, block
)