package com.chooongg.form

import com.chooongg.form.data.IFormGroupCreator
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

fun IFormGroupCreator.addButton(
    name: Any?, field: String? = null, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addCheckBox(
    name: Any?, field: String? = null, block: (FormCheckBox.() -> Unit)? = null
) = addItem(FormCheckBox(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addDivider(
    block: (FormDivider.() -> Unit)? = null
) = addItem(FormDivider().apply { block?.invoke(this) })

fun IFormGroupCreator.addInput(
    name: Any?, field: String? = null, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addInputFilled(
    name: Any?, field: String? = null, block: (FormInputFilled.() -> Unit)? = null
) = addItem(FormInputFilled(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addInputOutlined(
    name: Any?, field: String? = null, block: (FormInputOutlined.() -> Unit)? = null
) = addItem(FormInputOutlined(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addLabel(
    name: Any?, field: String? = null, block: (FormLabel.() -> Unit)? = null
) = addItem(FormLabel(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addMenu(
    name: Any?, field: String? = null, block: (FormMenu.() -> Unit)? = null
) = addItem(FormMenu(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addRadioButton(
    name: Any?, field: String? = null, block: (FormRadioButton.() -> Unit)? = null
) = addItem(FormRadioButton(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addRating(
    name: Any?, field: String? = null, block: (FormRating.() -> Unit)? = null
) = addItem(FormRating(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addSelector(
    name: Any?, field: String? = null, block: (FormSelector.() -> Unit)? = null
) = addItem(FormSelector(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addSlider(
    name: Any?, field: String? = null, block: (FormSlider.() -> Unit)? = null
) = addItem(FormSlider(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addSliderRange(
    name: Any?, field: String? = null, block: (FormSliderRange.() -> Unit)? = null
) = addItem(FormSliderRange(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addSwitch(
    name: Any?, field: String? = null, block: (FormSwitch.() -> Unit)? = null
) = addItem(FormSwitch(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addText(
    name: Any?, field: String? = null, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addTime(
    name: Any?, field: String? = null, block: (FormTime.() -> Unit)? = null
) = addItem(FormTime(name, field).apply { block?.invoke(this) })

fun IFormGroupCreator.addTip(
    name: Any?, field: String? = null, block: (FormTip.() -> Unit)? = null
) = addItem(FormTip(name, field).apply { block?.invoke(this) })