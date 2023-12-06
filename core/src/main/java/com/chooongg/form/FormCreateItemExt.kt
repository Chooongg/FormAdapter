package com.chooongg.form

import androidx.annotation.StringRes
import com.chooongg.form.data.IFormCreator
import com.chooongg.form.item.FormButton
import com.chooongg.form.item.FormCheckBox
import com.chooongg.form.item.FormDivider
import com.chooongg.form.item.FormInput
import com.chooongg.form.item.FormInputFilled
import com.chooongg.form.item.FormInputOutlined
import com.chooongg.form.item.FormLabel
import com.chooongg.form.item.FormMenu
import com.chooongg.form.item.FormRadioButton
import com.chooongg.form.item.FormRating
import com.chooongg.form.item.FormSelector
import com.chooongg.form.item.FormSlider
import com.chooongg.form.item.FormSliderRange
import com.chooongg.form.item.FormSwitch
import com.chooongg.form.item.FormText
import com.chooongg.form.item.FormTime
import com.chooongg.form.item.FormTip
import com.chooongg.form.item.MultiColumnForm
import com.chooongg.form.item.SingleLineForm

fun IFormCreator.singleLine(block: SingleLineForm.() -> Unit) =
    addItem(SingleLineForm().apply(block))

fun IFormCreator.multiColumn(block: MultiColumnForm.() -> Unit) =
    addItem(MultiColumnForm().apply(block))

fun IFormCreator.addButton(
    name: CharSequence?, field: String? = null, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(name, field).apply { block?.invoke(this) })

fun IFormCreator.addButton(
    @StringRes nameRes: Int?, field: String? = null, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addCheckBox(
    name: CharSequence?, field: String? = null, block: (FormCheckBox.() -> Unit)? = null
) = addItem(FormCheckBox(name, field).apply { block?.invoke(this) })

fun IFormCreator.addCheckBox(
    @StringRes nameRes: Int?, field: String? = null, block: (FormCheckBox.() -> Unit)? = null
) = addItem(FormCheckBox(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addDivider(
    block: (FormDivider.() -> Unit)? = null
) = addItem(FormDivider().apply { block?.invoke(this) })

fun IFormCreator.addInput(
    name: CharSequence?, field: String? = null, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(name, field).apply { block?.invoke(this) })

fun IFormCreator.addInput(
    @StringRes nameRes: Int?, field: String? = null, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addInputFilled(
    name: CharSequence?, field: String? = null, block: (FormInputFilled.() -> Unit)? = null
) = addItem(FormInputFilled(name, field).apply { block?.invoke(this) })

fun IFormCreator.addInputFilled(
    @StringRes nameRes: Int?, field: String? = null, block: (FormInputFilled.() -> Unit)? = null
) = addItem(FormInputFilled(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addInputOutlined(
    name: CharSequence?, field: String? = null, block: (FormInputOutlined.() -> Unit)? = null
) = addItem(FormInputOutlined(name, field).apply { block?.invoke(this) })

fun IFormCreator.addInputOutlined(
    @StringRes nameRes: Int?, field: String? = null, block: (FormInputOutlined.() -> Unit)? = null
) = addItem(FormInputOutlined(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addLabel(
    name: CharSequence?, field: String? = null, block: (FormLabel.() -> Unit)? = null
) = addItem(FormLabel(name, field).apply { block?.invoke(this) })

fun IFormCreator.addLabel(
    @StringRes nameRes: Int?, field: String? = null, block: (FormLabel.() -> Unit)? = null
) = addItem(FormLabel(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addMenu(
    name: CharSequence?, field: String? = null, block: (FormMenu.() -> Unit)? = null
) = addItem(FormMenu(name, field).apply { block?.invoke(this) })

fun IFormCreator.addMenu(
    @StringRes nameRes: Int?, field: String? = null, block: (FormMenu.() -> Unit)? = null
) = addItem(FormMenu(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addRadioButton(
    name: CharSequence?, field: String? = null, block: (FormRadioButton.() -> Unit)? = null
) = addItem(FormRadioButton(name, field).apply { block?.invoke(this) })

fun IFormCreator.addRadioButton(
    @StringRes nameRes: Int?, field: String? = null, block: (FormRadioButton.() -> Unit)? = null
) = addItem(FormRadioButton(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addRating(
    name: CharSequence?, field: String? = null, block: (FormRating.() -> Unit)? = null
) = addItem(FormRating(name, field).apply { block?.invoke(this) })

fun IFormCreator.addRating(
    @StringRes nameRes: Int?, field: String? = null, block: (FormRating.() -> Unit)? = null
) = addItem(FormRating(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addSelector(
    name: CharSequence?, field: String? = null, block: (FormSelector.() -> Unit)? = null
) = addItem(FormSelector(name, field).apply { block?.invoke(this) })

fun IFormCreator.addSelector(
    @StringRes nameRes: Int?, field: String? = null, block: (FormSelector.() -> Unit)? = null
) = addItem(FormSelector(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addSlider(
    name: CharSequence?, field: String? = null, block: (FormSlider.() -> Unit)? = null
) = addItem(FormSlider(name, field).apply { block?.invoke(this) })

fun IFormCreator.addSlider(
    @StringRes nameRes: Int?, field: String? = null, block: (FormSlider.() -> Unit)? = null
) = addItem(FormSlider(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addSliderRange(
    name: CharSequence?, field: String? = null, block: (FormSliderRange.() -> Unit)? = null
) = addItem(FormSliderRange(name, field).apply { block?.invoke(this) })

fun IFormCreator.addSliderRange(
    @StringRes nameRes: Int?, field: String? = null, block: (FormSliderRange.() -> Unit)? = null
) = addItem(FormSliderRange(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addSwitch(
    name: CharSequence?, field: String? = null, block: (FormSwitch.() -> Unit)? = null
) = addItem(FormSwitch(name, field).apply { block?.invoke(this) })

fun IFormCreator.addSwitch(
    @StringRes nameRes: Int?, field: String? = null, block: (FormSwitch.() -> Unit)? = null
) = addItem(FormSwitch(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addText(
    name: CharSequence?, field: String? = null, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name, field).apply { block?.invoke(this) })

fun IFormCreator.addText(
    @StringRes nameRes: Int?, field: String? = null, block: (FormText.() -> Unit)? = null
) = addItem(FormText(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addTime(
    name: CharSequence?, field: String? = null, block: (FormTime.() -> Unit)? = null
) = addItem(FormTime(name, field).apply { block?.invoke(this) })

fun IFormCreator.addTime(
    @StringRes nameRes: Int?, field: String? = null, block: (FormTime.() -> Unit)? = null
) = addItem(FormTime(nameRes, field).apply { block?.invoke(this) })

fun IFormCreator.addTip(
    name: CharSequence?, field: String? = null, block: (FormTip.() -> Unit)? = null
) = addItem(FormTip(name, field).apply { block?.invoke(this) })

fun IFormCreator.addTip(
    @StringRes nameRes: Int?, field: String? = null, block: (FormTip.() -> Unit)? = null
) = addItem(FormTip(nameRes, field).apply { block?.invoke(this) })