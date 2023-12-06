package com.chooongg.form

import com.chooongg.form.data.FormDynamicPartData
import com.chooongg.form.data.FormPartData
import com.chooongg.form.data.IFormAdapterCreator
import com.chooongg.form.style.CardElevatedStyle
import com.chooongg.form.style.CardFilledStyle
import com.chooongg.form.style.CardOutlinedStyle
import com.chooongg.form.style.CardSeparateElevatedStyle
import com.chooongg.form.style.CardSeparateFilledStyle
import com.chooongg.form.style.CardSeparateOutlinedStyle
import com.chooongg.form.style.NotAlignmentStyle

fun IFormAdapterCreator.initNotAlignmentPart(
    styleBlock: (NotAlignmentStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    NotAlignmentStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initNotAlignmentDynamicPart(
    styleBlock: (NotAlignmentStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    NotAlignmentStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardElevatedPart(
    styleBlock: (CardElevatedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardElevatedDynamicPart(
    styleBlock: (CardElevatedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardFilledPart(
    styleBlock: (CardFilledStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardFilledDynamicPart(
    styleBlock: (CardFilledStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardOutlinedPart(
    styleBlock: (CardOutlinedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardOutlinedDynamicPart(
    styleBlock: (CardOutlinedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateElevatedPart(
    styleBlock: (CardSeparateElevatedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardSeparateElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateElevatedDynamicPart(
    styleBlock: (CardSeparateElevatedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardSeparateElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateFilledPart(
    styleBlock: (CardSeparateFilledStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardSeparateFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateFilledDynamicPart(
    styleBlock: (CardSeparateFilledStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardSeparateFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateOutlinedPart(
    styleBlock: (CardSeparateOutlinedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    CardSeparateOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateOutlinedDynamicPart(
    styleBlock: (CardSeparateOutlinedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    CardSeparateOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)