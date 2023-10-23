package com.chooongg.form.core

import androidx.annotation.StringRes
import com.chooongg.form.core.data.IFormCreator
import com.chooongg.form.core.item.FormInput
import com.chooongg.form.core.item.FormInputNoTypeset
import com.chooongg.form.core.item.FormText

fun IFormCreator.addText(
    name: CharSequence?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name).apply { block?.invoke(this) })

fun IFormCreator.addText(
    @StringRes nameRes: Int?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addInput(
    name: CharSequence?, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(name).apply { block?.invoke(this) })

fun IFormCreator.addInput(
    @StringRes nameRes: Int?, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addInputNoTypeset(
    name: CharSequence?, block: (FormInputNoTypeset.() -> Unit)? = null
) = addItem(FormInputNoTypeset(name).apply { block?.invoke(this) })

fun IFormCreator.addInputNoTypeset(
    @StringRes nameRes: Int?, block: (FormInputNoTypeset.() -> Unit)? = null
) = addItem(FormInputNoTypeset(nameRes).apply { block?.invoke(this) })