package com.chooongg.form.simple.viewMode

import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addButton
import com.chooongg.form.core.addCheckBox
import com.chooongg.form.core.addDivider
import com.chooongg.form.core.addInput
import com.chooongg.form.core.addInputFilled
import com.chooongg.form.core.addInputOutlined
import com.chooongg.form.core.addLabel
import com.chooongg.form.core.addMenu
import com.chooongg.form.core.addRadioButton
import com.chooongg.form.core.addRating
import com.chooongg.form.core.addSelector
import com.chooongg.form.core.addSlider
import com.chooongg.form.core.addSliderRange
import com.chooongg.form.core.addSwitch
import com.chooongg.form.core.addText
import com.chooongg.form.core.addTime
import com.chooongg.form.core.addTip
import com.chooongg.form.core.enum.FormOutputMode
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.core.option.Option
import com.chooongg.form.core.style.CardElevatedStyle
import com.chooongg.form.core.typeset.VerticalTypeset
import com.chooongg.form.simple.R
import kotlinx.coroutines.delay

class BasicViewModel : ViewModel() {

    val adapter = FormAdapter(true)

    init {
        adapter.setNewInstance {
            initPart(CardElevatedStyle()) {
                addButton("Button(Tonal)", "button") {
                    buttonStyle = FormButton.ButtonStyle.TONAL
                }
                addCheckBox("CheckBox", "checkBox") {
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
                addInputFilled("InputFilled", "inputFilled") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addInputOutlined("InputOutlined", "inputOutlined") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addInput("Input", "input") {
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
                addInput("Input", "input2") {
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
                addMenu("Menu") {
                    content = "Test"
                    icon = R.drawable.ic_main_advanced
                    hint = "测试"
                }
                addTip("Tip") {
                    visibilityMode = FormVisibilityMode.ENABLED
                    enableBottomPadding = true
                }
                addRadioButton("RadioButton", "radioButton") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("Test${i}"))
                        }
                        list
                    }
                }
                addRating("Rating", "rating")
                addSelector("Selector", "selector") {
                    optionLoader {
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("Test${i}", "remarks"))
                        }
                        list
                    }
                }
                addSelector("Selector", "selector2") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<Option>()
                        for (i in 0..100) {
                            list.add(Option("Test${i}", "remarks"))
                        }
                        list
                    }
                }
                addSlider("Slider", "slider") {
                    stepSize = 1f
                    content = "20"
                    menu = R.menu.operation
                    menuVisibilityMode = FormVisibilityMode.ALWAYS
                    onMenuClickListener { context, menu, item ->
                        if (menu.itemId == R.id.error) {
                            Toast.makeText(context, "${item.name}", Toast.LENGTH_SHORT).show()
                            true
                        } else false
                    }
                }
                addSliderRange("SliderRange", "sliderRange") {
                    stepSize = 1f
                    valueTo = 5f
                    content = listOf("1", "2")
                    typeset = VerticalTypeset()
                    menu = R.menu.operation
                }
                addSwitch("Switch", "switch") {
                    customOutputTrue = "1"
                    customOutputFalse = "2"
                }
                addText("Text") {
                    content = "Test"
                }
                addTime("Time", "time")
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
                addText("输出", "output") {
                    outputMode = FormOutputMode.NEVER
                    typeset = VerticalTypeset()
                }
                addMenu("Menu") {
                    content = "Test"
                    icon = R.drawable.ic_main_advanced
                    hint = "测试"
                    bubbleText = "v2.9.6"
                }
            }
        }
    }
}