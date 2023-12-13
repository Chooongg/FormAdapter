package com.chooongg.form.simple.viewMode

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.chooongg.form.FormAdapter
import com.chooongg.form.addButton
import com.chooongg.form.addCheckBox
import com.chooongg.form.addDivider
import com.chooongg.form.addInput
import com.chooongg.form.addInputFilled
import com.chooongg.form.addInputOutlined
import com.chooongg.form.addLabel
import com.chooongg.form.addMenu
import com.chooongg.form.addRadioButton
import com.chooongg.form.addRating
import com.chooongg.form.addSelector
import com.chooongg.form.addSlider
import com.chooongg.form.addSliderRange
import com.chooongg.form.addSwitch
import com.chooongg.form.addText
import com.chooongg.form.addTime
import com.chooongg.form.addTip
import com.chooongg.form.enum.FormOutputMode
import com.chooongg.form.enum.FormVisibilityMode
import com.chooongg.form.initCardElevatedPart
import com.chooongg.form.inputMode.InputModePassword
import com.chooongg.form.item.FormButton
import com.chooongg.form.option.Option
import com.chooongg.form.simple.R
import com.chooongg.form.typeset.VerticalTypeset
import kotlinx.coroutines.delay

class BasicViewModel : ViewModel() {

    val adapter = FormAdapter(true)

    init {
        adapter.setNewInstance {
            initCardElevatedPart("Default") {
                addButton("Button(Tonal)", "button") {
                    buttonStyle = FormButton.ButtonStyle.TONAL
                    icon = R.drawable.ic_main_advanced
                }
                addCheckBox("CheckBox", "checkBox") {
                    required = true
                    loneLine = true
                    minSelectCount = 2
                    maxSelectCount = 4
                    optionLoader {
                        delay(2000)
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("Test${i}", i.toString()))
                        }
                        list
                    }
                }
                singleLine {
                    name = "测试"
                    isIndependent = true
                    addButton("Button1")
                    addButton("Button2")
                    addButton("Button3")
                }
                addDivider()
                addInputFilled("InputFilled", "inputFilled") {
                    startIcon = R.drawable.ic_main_basic
                    setStartIconOnClickListener { item, view ->
                        Toast.makeText(view.context, "点击了", Toast.LENGTH_SHORT).show()
                    }
                    optionLoader {
                        delay(2000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addInputOutlined("InputOutlined", "inputOutlined") {
                    startIcon = R.drawable.ic_main_style
                    setStartIconOnClickListener { item, view ->
                        Toast.makeText(view.context, "点击了", Toast.LENGTH_SHORT).show()
                    }
                    optionLoader {
                        delay(2000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}")
                        }
                        list
                    }
                }
                addInput("Input", "input") {
                    optionLoader {
                        delay(2000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("Test${i}asdfasdfrefgwesrgasdrgzsefasefsdf")
                        }
                        list
                    }
                }
                addInput("Input", "input2") {
                    required = true
                    startIcon = R.drawable.ic_main_advanced
                    setStartIconOnClickListener { item, view ->
                        Toast.makeText(view.context, "点击了", Toast.LENGTH_SHORT).show()
                    }
                    maxLines = 1
                    minLength = 6
                    maxLength = 16
                    showCounter = true
                    inputMode = InputModePassword()
                    contentGravity = Gravity.NO_GRAVITY
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
                        delay(2000)
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("Test${i}"))
                        }
                        list
                    }
                }
                addRating("Rating", "rating") {
                    stepSize = 0.2f
                    tint = {
                        ColorStateList.valueOf(Color.parseColor("#ea9518"))
                    }
                }
                addSelector("Selector", "selector") {
                    optionLoader {
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(
                                Option(
                                    "Test${i}asdfasdfasdfasdfawerawerawdfasdgawerawserfasdfasdf",
                                    "remarks"
                                )
                            )
                        }
                        list
                    }
                }
                addSelector("Selector", "selector2") {
                    optionLoader {
                        delay(2000)
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
                    menuCreateOptionCallback {
                        it.findItem(R.id.error).isVisible = false
                    }
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
                    customTrueValue = "1"
                    customFalseValue = "2"
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
                    menu = R.menu.operation
                }
                addMenu("Menu", "menu") {
                    nameColor = {
                        ColorStateList.valueOf(Color.GRAY)
                    }
                    icon = R.drawable.ic_main_advanced
                    hint = "有新版本"
                    bubbleText = "v1.0.0"
                }
            }
        }
        adapter.showOperationButton {
            visibilityMode = FormVisibilityMode.ALWAYS
            name = "Operation"
        }
    }
}