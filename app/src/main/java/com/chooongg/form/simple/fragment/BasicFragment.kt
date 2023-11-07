package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.View
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addButton
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
import com.chooongg.form.simple.R
import kotlinx.coroutines.delay

class BasicFragment : BaseFragment() {

    private val adapter = FormAdapter(true)

    override fun change() {
        adapter.findOfField("button", false) {
            visibilityMode =
                if (visibilityMode == FormVisibilityMode.ALWAYS) FormVisibilityMode.NEVER else FormVisibilityMode.ALWAYS
        }
        adapter.isEnabled = !adapter.isEnabled
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.basic)
        formView.setFormAdapter(adapter)
        adapter.setNewInstance {
            addPart {
                addButton("Button(Tonal)") {
                    field = "button"
                    buttonStyle = FormButton.ButtonStyle.TONAL
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
                    content = 50f
                }
                addSliderRange("SliderRange") {
                    stepSize = 1f
                    valueTo = 5f
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