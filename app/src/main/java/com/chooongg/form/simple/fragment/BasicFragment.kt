package com.chooongg.form.simple.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
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
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.core.option.Option
import com.chooongg.form.simple.R
import kotlinx.coroutines.delay

class BasicFragment : BaseFragment() {

    private val adapter = FormAdapter(true)

    override fun change() {
        adapter.isEnabled = !adapter.isEnabled
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.basic)
        formView.setFormAdapter(adapter)
        adapter.setNewInstance {
            addPart {
                addDivider()
                addText("Text") {
                    content =
                        "测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容"
                }
                addText("Text") {
                    content = "测试内容"
                }
                addDivider {
                    matchParentStartEdge = true
                    matchParentEndEdge = true
                }
                addSelector("测试") {
                    optionLoader {
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("测试${i}", "附属"))
                        }
                        list
                    }
                }
                addInput("Input") {
                    field = "input"
                    hint = "请输入"
                    content = "测试"
                    maxLines = 1
                    counterMaxLength = 11
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("测试${i}")
                        }
                        list
                    }
                }
                addSlider("测试") {
                    stepSize = 1f
                    content = 50f
                }
                addSliderRange("测试") {
                    stepSize = 1f
                    valueTo = 5f
                }
                addSwitch("测试")
                addSwitchMaterial("测试")
                addSelector("测试") {
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<Option>()
                        for (i in 0..100) {
                            list.add(Option("测试${i}", "附属"))
                        }
                        list
                    }
                }
                addInputFilled("InputFilled") {
                    hint = "请输入"
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("测试${i}")
                        }
                        list
                    }
                }
                addInputOutlined("InputOutlined") {
                    hint = "请输入"
                    optionLoader {
                        delay(5000)
                        val list = mutableListOf<CharSequence>()
                        for (i in 0..100) {
                            list.add("测试${i}")
                        }
                        list
                    }
                }
                addRating("测试")
                addRadioButton("测试") {
                    optionLoader {
                        val list = mutableListOf<Option>()
                        for (i in 0..5) {
                            list.add(Option("测试${i}", "1"))
                        }
                        list
                    }
                }
                addButton("测试") {
                    buttonStyle = FormButton.ButtonStyle.ELEVATED
                }
                addLabel("测试")
                addLabel("测试") {
                    color = { ColorStateList.valueOf(Color.RED) }
                }
                addButton("测试") {
                    buttonStyle = FormButton.ButtonStyle.UN_ELEVATED
                    contentGravity = Gravity.CENTER
                }
                addButton("测试") {
                    buttonStyle = FormButton.ButtonStyle.TEXT
                }
                addButton("测试") {
                    buttonStyle = FormButton.ButtonStyle.OUTLINED
                }
                addButton("测试") {
                    buttonStyle = FormButton.ButtonStyle.TONAL
                }
                addDivider()
            }
        }
    }
}