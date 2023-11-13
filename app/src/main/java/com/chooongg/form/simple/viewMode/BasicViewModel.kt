package com.chooongg.form.simple.viewMode

import android.view.Gravity
import androidx.lifecycle.ViewModel
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addButton
import com.chooongg.form.core.addCheckBox
import com.chooongg.form.core.addDivider
import com.chooongg.form.core.addInput
import com.chooongg.form.core.addInputFilled
import com.chooongg.form.core.addInputOutlined
import com.chooongg.form.core.addLabel
import com.chooongg.form.core.addRadioButton
import com.chooongg.form.core.addRating
import com.chooongg.form.core.addSelector
import com.chooongg.form.core.addSlider
import com.chooongg.form.core.addSliderRange
import com.chooongg.form.core.addSwitch
import com.chooongg.form.core.addSwitchMaterial
import com.chooongg.form.core.addText
import com.chooongg.form.core.addTime
import com.chooongg.form.core.addTip
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.core.option.Option
import com.chooongg.form.core.style.CardElevatedStyle
import kotlinx.coroutines.delay

class BasicViewModel : ViewModel() {

    val adapter = FormAdapter(true)

    init {
        adapter.setNewInstance {
            addPart(CardElevatedStyle()) {
                addButton("Button(Tonal)") {
                    field = "button"
                    buttonStyle = FormButton.ButtonStyle.TONAL
                }
                addCheckBox("CheckBox") {
                    loneLine = true
                    optionLoader {
                        delay(2000)
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("Test${i}", i.toString()))
                        }
                        list
                    }
                }
                addDivider()
                addInputFilled("InputFilled") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addInputOutlined("InputOutlined") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addInput("Input") {
                    field = "input"
                    maxLines = 1
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addInput("Input") {
                    field = "input"
                    maxLines = 1
                    contentGravity = Gravity.NO_GRAVITY
                    counterMaxLength = 11
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addLabel("Label")
                addTip("Tip") {
                    visibilityMode = FormVisibilityMode.ENABLED
                    enableBottomPadding = true
                }
                addRadioButton("RadioButton") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("Test${i}", i.toString()))
                        }
                        list
                    }
                }
                addRating("Rating")
                addSelector("Selector") {
                    optionLoader {
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("Test${i}", "remarks"))
                        }
                        list
                    }
                }
                addSelector("Selector") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<Option>()
                        for (i in 0..100) {
                            list.add(Option("Test${i}", "remarks"))
                        }
                        list
                    }
                }
                addSlider("Slider") {
                    stepSize = 1f
                    content = "20"
                }
                addSliderRange("SliderRange") {
                    stepSize = 1f
                    valueTo = 5f
                    content = listOf("1", "2")
                }
                addSwitch("Switch")
                addSwitchMaterial("SwitchMaterial")
                addText("Text") {
                    content = "Test"
                }
                addTime("Time")
                addDivider {
                    matchParentStartEdge = true
                    matchParentEndEdge = true
                }
                addButton("Button(Elevated)") {
                    buttonStyle = FormButton.ButtonStyle.ELEVATED
                }
                addButton("Button(UnElevated)") {
                    buttonStyle = FormButton.ButtonStyle.UN_ELEVATED
                }
                addButton("Button(Text)") {
                    buttonStyle = FormButton.ButtonStyle.TEXT
                }
                addButton("Button(OutLined)") {
                    buttonStyle = FormButton.ButtonStyle.OUTLINED
                }
            }
        }
    }
}