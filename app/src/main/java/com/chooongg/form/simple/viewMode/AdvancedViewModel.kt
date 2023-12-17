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
import com.chooongg.form.enum.FormEnableMode
import com.chooongg.form.enum.FormSelectorOpenMode
import com.chooongg.form.enum.FormTimeMode
import com.chooongg.form.enum.FormVisibilityMode
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
            initCardOutlinedDynamicPart("Test Group") {
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
                    childDynamicGroup("ChildGroup", "temp") {
                        isIndependent = true
                        addGroup {
                            addSwitch("ChildSwitch")
                            addText("ChildText") {
                                content = "ChildTest"
                            }
                        }
                        dynamicGroupCreator {
                            addSwitch("ChildSwitch")
                            addText("ChildText") {
                                content = "ChildTest"
                            }
                        }
                    }
                }
            }
            initCardOutlinedPart("VisibilityMode") {
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
            initCardOutlinedPart("EnableMode") {
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
            initCardOutlinedPart("ContentGravity") {
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
            initCardOutlinedPart("MultiColumnContentGravity") {
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
            initCardOutlinedPart("Linkage") {
                addSelector("Selector", "selector") {
                    options = listOf(Option("Item1"), Option("Item2"), Option("Item3"))
                    setLinkage { linkage, _, content ->
                        linkage.updateItem("linkageText") {
                            it.visibilityMode = if (content != null) {
                                FormVisibilityMode.ALWAYS
                            } else {
                                FormVisibilityMode.NEVER
                            }
                        }
                    }
                }
                addText("Text") {
                    field = "linkageText"
                    content = "selector is empty hide it"
                }
            }
            initCardOutlinedPart("Button") {
                addButton("Default")
                addButton("Elevated") {
                    buttonStyle = FormButton.ButtonStyle.ELEVATED
                }
                addButton("UnElevated") {
                    buttonStyle = FormButton.ButtonStyle.UN_ELEVATED
                }
                addButton("Primary") {
                    buttonStyle = FormButton.ButtonStyle.PRIMARY
                }
                addButton("PrimaryTonal") {
                    buttonStyle = FormButton.ButtonStyle.PRIMARY_TONAL
                }
                addButton("Text") {
                    buttonStyle = FormButton.ButtonStyle.TEXT
                }
                addButton("PrimaryOutlined") {
                    buttonStyle = FormButton.ButtonStyle.PRIMARY_OUTLINED
                }
                addButton("Secondary") {
                    buttonStyle = FormButton.ButtonStyle.SECONDARY
                }
                addButton("Tonal") {
                    buttonStyle = FormButton.ButtonStyle.TONAL
                }
                addButton("SecondaryText") {
                    buttonStyle = FormButton.ButtonStyle.SECONDARY_TEXT
                }
                addButton("Outlined") {
                    buttonStyle = FormButton.ButtonStyle.OUTLINED
                }
                addButton("Tertiary") {
                    buttonStyle = FormButton.ButtonStyle.TERTIARY
                }
                addButton("TertiaryTonal") {
                    buttonStyle = FormButton.ButtonStyle.TERTIARY_TONAL
                }
                addButton("TertiaryText") {
                    buttonStyle = FormButton.ButtonStyle.TERTIARY_TEXT
                }
                addButton("TertiaryOutlined") {
                    buttonStyle = FormButton.ButtonStyle.TERTIARY_OUTLINED
                }
                addButton("Error") {
                    buttonStyle = FormButton.ButtonStyle.ERROR
                }
                addButton("ErrorTonal") {
                    buttonStyle = FormButton.ButtonStyle.ERROR_TONAL
                }
                addButton("ErrorText") {
                    buttonStyle = FormButton.ButtonStyle.ERROR_TEXT
                }
                addButton("ErrorOutlined") {
                    buttonStyle = FormButton.ButtonStyle.ERROR_OUTLINED
                }
                addButton("Custom1") {
                    buttonStyle = FormButton.ButtonStyle.CUSTOM1
                }
                addButton("Custom2") {
                    buttonStyle = FormButton.ButtonStyle.CUSTOM2
                }
                addButton("Custom3") {
                    buttonStyle = FormButton.ButtonStyle.CUSTOM3
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
            initCardOutlinedPart("CheckBox") {
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
            initCardOutlinedPart("Divider") {
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
            initCardOutlinedPart("Input") {
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
            initCardOutlinedPart("Label") {
                addLabel("Color") {
                    color = { ColorStateList.valueOf(Color.RED) }
                }
            }
            initCardOutlinedPart("Menu") {
            }
            initCardOutlinedPart("RadioButton") {
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
            initCardOutlinedPart("Rating") {
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
                    enableMode = FormEnableMode.NEVER
                    stepSize = 0.5f
                    content = 2.5f
                }
            }
            initCardOutlinedPart("Selector") {
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
            initCardOutlinedPart("Slider") {
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
            initCardOutlinedPart("Switch") {
                addSwitch("Switch")
            }
            initCardOutlinedPart("Text") {
                addText("Default") {
                    content = "Content"
                }
            }
            initCardOutlinedPart("Time") {
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
            initCardOutlinedPart("Tip") {
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