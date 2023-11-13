package com.chooongg.form.simple.viewMode

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import androidx.lifecycle.ViewModel
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addButton
import com.chooongg.form.core.addCardOutlinedPart
import com.chooongg.form.core.addCheckBox
import com.chooongg.form.core.addDivider
import com.chooongg.form.core.addInput
import com.chooongg.form.core.addInputFilled
import com.chooongg.form.core.addInputOutlined
import com.chooongg.form.core.addLabel
import com.chooongg.form.core.addSelector
import com.chooongg.form.core.addSlider
import com.chooongg.form.core.addSliderRange
import com.chooongg.form.core.addSwitch
import com.chooongg.form.core.addSwitchMaterial
import com.chooongg.form.core.addText
import com.chooongg.form.core.addTime
import com.chooongg.form.core.addTip
import com.chooongg.form.core.enum.FormEnableMode
import com.chooongg.form.core.enum.FormSelectorOpenMode
import com.chooongg.form.core.enum.FormTimeMode
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.core.item.FormTime
import com.chooongg.form.core.option.Option
import kotlinx.coroutines.delay

class AdvancedViewModel : ViewModel() {

    val adapter = FormAdapter(true)

    init {
        adapter.setNewInstance {
            addCardOutlinedPart {
                partName = "VisibilityMode"
                addButton("ENABLED") {
                    visibilityMode = FormVisibilityMode.ENABLED
                }
                addButton("ALWAYS") {
                    visibilityMode = FormVisibilityMode.ALWAYS
                }
                addButton("DISABLED") {
                    visibilityMode = FormVisibilityMode.DISABLED
                }
                addButton("NEVER") {
                    visibilityMode = FormVisibilityMode.NEVER
                }
            }
            addCardOutlinedPart {
                partName = "EnableMode"
                addButton("ENABLED") {
                    enableMode = FormEnableMode.ENABLED
                }
                addButton("ALWAYS") {
                    enableMode = FormEnableMode.ALWAYS
                }
                addButton("DISABLED") {
                    enableMode = FormEnableMode.DISABLED
                }
                addButton("NEVER") {
                    enableMode = FormEnableMode.NEVER
                }
            }
            addCardOutlinedPart {
                partName = "ContentGravity"
                addText("None") {
                    content = "Content"
                }
                addText("Start") {
                    content = "Content"
                    contentGravity = Gravity.START
                }
                addText("End") {
                    content = "Content"
                    contentGravity = Gravity.END
                }
                addText("Center") {
                    content = "Content"
                    contentGravity = Gravity.CENTER
                }
            }
            addCardOutlinedPart {
                partName = "MultiColumnContentGravity"
                addText("None") {
                    content = "Content"
                }
                addText("Start") {
                    content = "Content"
                    multiColumnContentGravity = Gravity.START
                }
                addText("End") {
                    content = "Content"
                    multiColumnContentGravity = Gravity.END
                }
                addText("Center") {
                    content = "Content"
                    multiColumnContentGravity = Gravity.CENTER
                }
            }
            addCardOutlinedPart {
                partName = "Button"
                addButton("Default")
                addButton("Text") {
                    buttonStyle = FormButton.ButtonStyle.TEXT
                }
                addButton("Tonal") {
                    buttonStyle = FormButton.ButtonStyle.TONAL
                }
                addButton("Outlined") {
                    buttonStyle = FormButton.ButtonStyle.OUTLINED
                }
                addButton("Elevated") {
                    buttonStyle = FormButton.ButtonStyle.ELEVATED
                }
                addButton("UnElevated") {
                    buttonStyle = FormButton.ButtonStyle.UN_ELEVATED
                }
                addButton("Gravity Start") {
                    gravity = Gravity.START
                }
                addButton("Gravity End") {
                    gravity = Gravity.END
                }
                addButton("Gravity Center") {
                    gravity = Gravity.CENTER
                }
            }
            addCardOutlinedPart {
                partName = "CheckBox"
                addCheckBox("Default") {
                    options = listOf(
                        Option("Option1", "1"),
                        Option("Option2", "2"),
                        Option("Option3", "3"),
                        Option("Option4", "4")
                    )
                }
                addCheckBox("Remote") {
                    optionLoader {
                        delay(5000)
                        listOf(
                            Option("RemoteOption1", "1"),
                            Option("RemoteOption2", "2"),
                            Option("RemoteOption3", "3"),
                            Option("RemoteOption4", "4")
                        )
                    }
                }
            }
            addCardOutlinedPart {
                partName = "Divider"
                addDivider()
                addDivider {
                    matchParentEdge = true
                }
                addDivider {
                    matchParentStartEdge = true
                }
                addDivider {
                    matchParentEndEdge = true
                }
                addDivider {
                    thickness = 16
                }
                addDivider {
                    color = { Color.RED }
                    showAtEdge = true
                }
            }
            addCardOutlinedPart {
                partName = "Input"
                addInput("Default")
                addInput("Remote") {
                    optionLoader {
                        delay(5000)
                        listOf("One", "Two", "Three", "Four")
                    }
                }
                addInput("Prefix Suffix") {
                    prefix = "@"
                    suffix = "%"
                }
                addInput("SingleLine") {
                    maxLines = 1
                }
                addInput("Multiline") {
                    loneLine = true
                    showClearIcon = false
                    minLines = 3
                    counterMaxLength = 100
                }
                addInputFilled("Filled")
                addInputOutlined("Outlined")
            }
            addCardOutlinedPart {
                partName = "Label"
                addLabel("Color") {
                    color = { ColorStateList.valueOf(Color.RED) }
                }
            }
            addCardOutlinedPart {
                partName = "Menu"
            }
            addCardOutlinedPart {
                partName = "RadioButton"
            }
            addCardOutlinedPart {
                partName = "Rating"
            }
            addCardOutlinedPart {
                partName = "Selector"
                addSelector("Default") {
                    options = listOf(
                        Option("Option1", "1"),
                        Option("Option2", "2"),
                        Option("Option3", "3"),
                        Option("Option4", "4")
                    )
                }
                addSelector("Remote") {
                    optionLoader {
                        delay(5000)
                        listOf(
                            Option("RemoteOption1", "1"),
                            Option("RemoteOption2", "2"),
                            Option("RemoteOption3", "3"),
                            Option("RemoteOption4", "4")
                        )
                    }
                }
                addSelector("Page") {
                    openMode = FormSelectorOpenMode.PAGE
                    options = listOf(
                        Option("Option1", "1"),
                        Option("Option2", "2"),
                        Option("Option3", "3"),
                        Option("Option4", "4")
                    )
                }
            }
            addCardOutlinedPart {
                partName = "Slider"
                addSlider("Default")
                addSlider("Step") {
                    stepSize = 5f
                }
                addSlider("valueRange") {
                    valueFrom = -5f
                    valueTo = 5f
                    stepSize = 5f
                }
                addSliderRange("RangeSlider") {

                }
                addSliderRange("Step") {
                    stepSize = 20f
                }
                addSliderRange("Formatter") {
                    stepSize = 20f
                    formatter { "${it.toInt()} Size" }
                }
            }
            addCardOutlinedPart {
                partName = "Switch"
                addSwitch("Default")
                addSwitchMaterial("Material")
            }
            addCardOutlinedPart {
                partName = "Text"
                addText("Default") {
                    content = "Content"
                }
            }
            addCardOutlinedPart {
                partName = "Time"
                addTime("Time") {
                    timeMode = FormTimeMode.TIME
                }
                addTime("Date") {
                    timeMode = FormTimeMode.DATE
                }
                addTime("DateTime") {
                    timeMode = FormTimeMode.DATE_TIME
                }
                addTime("InputMode") {
                    inputMode = FormTime.INPUT_MODE_TEXT
                }
                addTime("ShowPattern") {
                    showFormatPattern = "yyyy-MM-dd HH:mm:ss"
                }
            }
            addCardOutlinedPart {
                partName = "Tip"
                addTip("Color") {
                    color = { ColorStateList.valueOf(Color.RED) }
                }
                addTip("TopPadding") {
                    enableTopPadding = true
                }
                addTip("BottomPadding") {
                    enableBottomPadding = true
                }
            }
        }
    }
}