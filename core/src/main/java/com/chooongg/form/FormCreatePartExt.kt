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
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (NotAlignmentStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    partName, partField, NotAlignmentStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initNotAlignmentDynamicPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (NotAlignmentStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    partName, partField, NotAlignmentStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardElevatedPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardElevatedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    partName, partField, CardElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardElevatedDynamicPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardElevatedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    partName, partField, CardElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardFilledPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardFilledStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    partName, partField, CardFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardFilledDynamicPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardFilledStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    partName, partField, CardFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardOutlinedPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardOutlinedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    partName, partField, CardOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardOutlinedDynamicPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardOutlinedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    partName, partField, CardOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateElevatedPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardSeparateElevatedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    partName, partField, CardSeparateElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateElevatedDynamicPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardSeparateElevatedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    partName, partField, CardSeparateElevatedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateFilledPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardSeparateFilledStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    partName, partField, CardSeparateFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateFilledDynamicPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardSeparateFilledStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    partName, partField, CardSeparateFilledStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateOutlinedPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardSeparateOutlinedStyle.() -> Unit)? = null,
    block: FormPartData.() -> Unit
) = initPart(
    partName, partField, CardSeparateOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)

fun IFormAdapterCreator.initCardSeparateOutlinedDynamicPart(
    partName: Any? = null,
    partField: String? = null,
    styleBlock: (CardSeparateOutlinedStyle.() -> Unit)? = null,
    block: FormDynamicPartData.() -> Unit
) = initDynamicPart(
    partName, partField, CardSeparateOutlinedStyle().apply { styleBlock?.invoke(this) }, block
)