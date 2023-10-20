package com.chooongg.form.core

import androidx.annotation.StringRes
import com.chooongg.form.core.data.IFormCreator
import com.chooongg.form.core.item.FormText

fun IFormCreator.addText(
    name: CharSequence?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name).apply { block?.invoke(this) })

fun IFormCreator.addText(
    @StringRes nameRes: Int?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(nameRes).apply { block?.invoke(this) })