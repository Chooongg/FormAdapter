package com.chooongg.form.core

import androidx.annotation.StringRes
import com.chooongg.form.core.data.IFormCreator
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.core.item.FormDivider
import com.chooongg.form.core.item.FormInput
import com.chooongg.form.core.item.FormInputFilled
import com.chooongg.form.core.item.FormInputOutlined
import com.chooongg.form.core.item.FormLabel
import com.chooongg.form.core.item.FormRadioButton
import com.chooongg.form.core.item.FormRating
import com.chooongg.form.core.item.FormSelector
import com.chooongg.form.core.item.FormSlider
import com.chooongg.form.core.item.FormSliderRange
import com.chooongg.form.core.item.FormSwitch
import com.chooongg.form.core.item.FormSwitchMaterial
import com.chooongg.form.core.item.FormText
import com.chooongg.form.core.item.FormTime
import com.chooongg.form.core.item.FormTip

fun IFormCreator.addButton(
    name: CharSequence?, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(name).apply { block?.invoke(this) })

fun IFormCreator.addButton(
    @StringRes nameRes: Int?, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addDivider(
    block: (FormDivider.() -> Unit)? = null
) = addItem(FormDivider().apply { block?.invoke(this) })

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

fun IFormCreator.addLabel(
    name: CharSequence?, block: (FormLabel.() -> Unit)? = null
) = addItem(FormLabel(name).apply { block?.invoke(this) })

fun IFormCreator.addLabel(
    @StringRes nameRes: Int?, block: (FormLabel.() -> Unit)? = null
) = addItem(FormLabel(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addRadioButton(
    name: CharSequence?, block: (FormRadioButton.() -> Unit)? = null
) = addItem(FormRadioButton(name).apply { block?.invoke(this) })

fun IFormCreator.addRadioButton(
    @StringRes nameRes: Int?, block: (FormRadioButton.() -> Unit)? = null
) = addItem(FormRadioButton(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addRating(
    name: CharSequence?, block: (FormRating.() -> Unit)? = null
) = addItem(FormRating(name).apply { block?.invoke(this) })

fun IFormCreator.addRating(
    @StringRes nameRes: Int?, block: (FormRating.() -> Unit)? = null
) = addItem(FormRating(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addSelector(
    name: CharSequence?, block: (FormSelector.() -> Unit)? = null
) = addItem(FormSelector(name).apply { block?.invoke(this) })

fun IFormCreator.addSelector(
    @StringRes nameRes: Int?, block: (FormSelector.() -> Unit)? = null
) = addItem(FormSelector(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addSlider(
    name: CharSequence?, block: (FormSlider.() -> Unit)? = null
) = addItem(FormSlider(name).apply { block?.invoke(this) })

fun IFormCreator.addSlider(
    @StringRes nameRes: Int?, block: (FormSlider.() -> Unit)? = null
) = addItem(FormSlider(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addSliderRange(
    name: CharSequence?, block: (FormSliderRange.() -> Unit)? = null
) = addItem(FormSliderRange(name).apply { block?.invoke(this) })

fun IFormCreator.addSliderRange(
    @StringRes nameRes: Int?, block: (FormSliderRange.() -> Unit)? = null
) = addItem(FormSliderRange(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addSwitch(
    name: CharSequence?, block: (FormSwitch.() -> Unit)? = null
) = addItem(FormSwitch(name).apply { block?.invoke(this) })

fun IFormCreator.addSwitch(
    @StringRes nameRes: Int?, block: (FormSwitch.() -> Unit)? = null
) = addItem(FormSwitch(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addSwitchMaterial(
    name: CharSequence?, block: (FormSwitchMaterial.() -> Unit)? = null
) = addItem(FormSwitchMaterial(name).apply { block?.invoke(this) })

fun IFormCreator.addSwitchMaterial(
    @StringRes nameRes: Int?, block: (FormSwitchMaterial.() -> Unit)? = null
) = addItem(FormSwitchMaterial(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addText(
    name: CharSequence?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name).apply { block?.invoke(this) })

fun IFormCreator.addText(
    @StringRes nameRes: Int?, block: (FormText.() -> Unit)? = null
) = addItem(FormText(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addTime(
    name: CharSequence?, block: (FormTime.() -> Unit)? = null
) = addItem(FormTime(name).apply { block?.invoke(this) })

fun IFormCreator.addTime(
    @StringRes nameRes: Int?, block: (FormTime.() -> Unit)? = null
) = addItem(FormTime(nameRes).apply { block?.invoke(this) })

fun IFormCreator.addTip(
    name: CharSequence?, block: (FormTip.() -> Unit)? = null
) = addItem(FormTip(name).apply { block?.invoke(this) })

fun IFormCreator.addTip(
    @StringRes nameRes: Int?, block: (FormTip.() -> Unit)? = null
) = addItem(FormTip(nameRes).apply { block?.invoke(this) })