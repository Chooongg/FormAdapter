package com.chooongg.form.core

import androidx.annotation.StringRes
import com.chooongg.form.core.data.IFormCreator
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.core.item.FormInput
import com.chooongg.form.core.item.FormInputFilled
import com.chooongg.form.core.item.FormInputOutlined
import com.chooongg.form.core.item.FormText

fun IFormCreator.addButton(
    name: CharSequence?, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(name).apply { block?.invoke(this) })

fun IFormCreator.addButton(
    @StringRes nameRes: Int?, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addInput(
    name: CharSequence?, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(name).apply { block?.invoke(this) })

fun IFormCreator.addInput(
    @StringRes nameRes: Int?, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addInputFilled(
    name: CharSequence?, block: (FormInputFilled.() -> Unit)? = null
) = addItem(FormInputFilled(name).apply { block?.invoke(this) })

fun IFormCreator.addInputFilled(
    @StringRes nameRes: Int?, block: (FormInputFilled.() -> Unit)? = null
) = addItem(FormInputFilled(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addInputOutlined(
    name: CharSequence?, block: (FormInputOutlined.() -> Unit)? = null
) = addItem(FormInputOutlined(name).apply { block?.invoke(this) })

fun IFormCreator.addInputOutlined(
    @StringRes nameRes: Int?, block: (FormInputOutlined.() -> Unit)? = null
) = addItem(FormInputOutlined(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addText(
    name: CharSequence?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name).apply { block?.invoke(this) })

fun IFormCreator.addText(
    @StringRes nameRes: Int?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(nameRes).apply { block?.invoke(this) })