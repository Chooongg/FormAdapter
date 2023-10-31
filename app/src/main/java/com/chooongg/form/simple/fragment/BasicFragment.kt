package com.chooongg.form.simple.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addButton
import com.chooongg.form.core.addDivider
import com.chooongg.form.core.addInput
import com.chooongg.form.core.addInputFilled
import com.chooongg.form.core.addInputOutlined
import com.chooongg.form.core.addLabel
import com.chooongg.form.core.addText
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.simple.R

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
                    content = "测试内容"
                }
                addText("Text") {
                    content = "测试内容"
                }
                addDivider {
                    matchParentStartEdge = true
                    matchParentEndEdge = true
                }
                addInput("Input") {
                    hint = "请输入"
                    content = "测试"
                    maxLines = 1
//                    counterMaxLength = 11
                }
                addInputFilled("InputFilled") {
                    hint = "请输入"
                }
                addInputOutlined("InputOutlined") {
                    hint = "请输入"
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