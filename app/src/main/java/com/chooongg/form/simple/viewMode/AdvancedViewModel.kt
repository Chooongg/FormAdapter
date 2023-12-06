package com.chooongg.form.simple.viewMode

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import androidx.lifecycle.ViewModel
import com.chooongg.form.FormAdapter
import com.chooongg.form.addButton
import com.chooongg.form.addCheckBox
import com.chooongg.form.addDivider
import com.chooongg.form.addInput
import com.chooongg.form.addInputFilled
import com.chooongg.form.addInputOutlined
import com.chooongg.form.addLabel
import com.chooongg.form.addRadioButton
import com.chooongg.form.addRating
import com.chooongg.form.addSelector
import com.chooongg.form.addSlider
import com.chooongg.form.addSliderRange
import com.chooongg.form.addSwitch
import com.chooongg.form.addText
import com.chooongg.form.addTime
import com.chooongg.form.addTip
import com.chooongg.form.enum.FormSelectorOpenMode
import com.chooongg.form.initCardOutlinedDynamicPart
import com.chooongg.form.initCardOutlinedPart
import com.chooongg.form.inputMode.InputModeDecimal
import com.chooongg.form.inputMode.InputModeNumber
import com.chooongg.form.inputMode.InputModePassword
import com.chooongg.form.item.FormButton
import com.chooongg.form.item.FormTime
import com.chooongg.form.option.Option
import kotlinx.coroutines.delay

class AdvancedViewModel : ViewModel() {

    val adapter = FormAdapter(true)

    init {
        adapter.setNewInstance {
            initCardOutlinedDynamicPart {
                partName = "Test Group"
                minGroupCount = 0
                maxGroupCount = 10
                addGroup {
                    addSwitch("Switch")
                    addText("Text") {
                        content = "Test"
                    }
                }
                dynamicGroupCreator {
                    addSwitch("Switch")
                    addText("Text") {
                        content = "Test"
                    }
                }
            }
            initCardOutlinedPart {
                partName = "VisibilityMode"
                addButton("ENABLED") {
                    visibilityMode = com.chooongg.form.enum.FormVisibilityMode.ENABLED
                }
                addButton("ALWAYS") {
                    visibilityMode = com.chooongg.form.enum.FormVisibilityMode.ALWAYS
                }
                addButton("DISABLED") {
                    visibilityMode = com.chooongg.form.enum.FormVisibilityMode.DISABLED
                }
                addButton("NEVER") {
                    visibilityMode = com.chooongg.form.enum.FormVisibilityMode.NEVER
                }
            }
            initCardOutlinedPart {
                partName = "EnableMode"
                addButton("ENABLED") {
                    enableMode = com.chooongg.form.enum.FormEnableMode.ENABLED
                }
                addButton("ALWAYS") {
                    enableMode = com.chooongg.form.enum.FormEnableMode.ALWAYS
                }
                addButton("DISABLED") {
                    enableMode = com.chooongg.form.enum.FormEnableMode.DISABLED
                }
                addButton("NEVER") {
                    enableMode = com.chooongg.form.enum.FormEnableMode.NEVER
                }
            }
            initCardOutlinedPart {
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
            initCardOutlinedPart {
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
            initCardOutlinedPart {
                partName = "Linkage"
                addSelector("Selector") {
                    options = listOf(Option("Item1"), Option("Item2"), Option("Item3"))
                    setLinkage { linkage, _, content ->
                        linkage.findItem("linkageText") {
                            it.visibilityMode = if (content != null) {
                                com.chooongg.form.enum.FormVisibilityMode.ALWAYS
                            } else {
                                com.chooongg.form.enum.FormVisibilityMode.NEVER
                            }
                        }
                    }
                }
                addText("Text") {
                    field = "linkageText"
                    content = "selector is empty hide it"
                }
            }
            initCardOutlinedPart {
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
            initCardOutlinedPart {
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
            initCardOutlinedPart {
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
            initCardOutlinedPart {
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
                    showCounter = true
                }
                addInputFilled("Filled")
                addInputOutlined("Outlined")
                addInput("Number") {
                    inputMode = InputModeNumber()
                }
                addInput("Decimal") {
                    inputMode = InputModeDecimal()
                }
                addInput("Password") {
                    inputMode = InputModePassword()
                }
            }
            initCardOutlinedPart {
                partName = "Label"
                addLabel("Color") {
                    color = { ColorStateList.valueOf(Color.RED) }
                }
            }
            initCardOutlinedPart {
                partName = "Menu"
            }
            initCardOutlinedPart {
                partName = "RadioButton"
                addRadioButton("Default") {
                    options = listOf(
                        Option("Option1", "1"),
                        Option("Option2", "2"),
                        Option("Option3", "3"),
                        Option("Option4", "4")
                    )
                }
                addRadioButton("Remote") {
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
            initCardOutlinedPart {
                partName = "Rating"
                addRating("Default")
                addRating("3 Star") {
                    numStars = 3
                }
                addRating("Step") {
                    stepSize = 0.5f
                }
                addRating("Tint") {
                    tint = { ColorStateList.valueOf(Color.YELLOW) }
                }
                addRating("OnlyShow") {
                    enableMode = com.chooongg.form.enum.FormEnableMode.NEVER
                    stepSize = 0.5f
                    content = 2.5f
                }
            }
            initCardOutlinedPart {
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
                    field = "selector"
                    openMode = FormSelectorOpenMode.PAGE
                    options = listOf(
                        Option("Option1", "1"),
                        Option("Option2", "2"),
                        Option("Option3", "3"),
                        Option("Option4", "4")
                    )
                }
            }
            initCardOutlinedPart {
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
            initCardOutlinedPart {
                partName = "Switch"
                addSwitch("Switch")
            }
            initCardOutlinedPart {
                partName = "Text"
                addText("Default") {
                    content = "Content"
                }
            }
            initCardOutlinedPart {
                partName = "Time"
                addTime("Time") {
                    timeMode = com.chooongg.form.enum.FormTimeMode.TIME
                }
                addTime("Date") {
                    timeMode = com.chooongg.form.enum.FormTimeMode.DATE
                }
                addTime("DateTime") {
                    timeMode = com.chooongg.form.enum.FormTimeMode.DATE_TIME
                }
                addTime("InputMode") {
                    inputMode = FormTime.INPUT_MODE_TEXT
                }
                addTime("ShowPattern") {
                    showFormatPattern = "yyyy-MM-dd HH:mm:ss"
                }
            }
            initCardOutlinedPart {
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