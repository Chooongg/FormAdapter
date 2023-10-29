package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addButton
import com.chooongg.form.core.addInput
import com.chooongg.form.core.addInputFilled
import com.chooongg.form.core.addInputOutlined
import com.chooongg.form.core.addText
import com.chooongg.form.core.item.FormButton
import com.chooongg.form.simple.databinding.FragmentBasicBinding

class BasicFragment : Fragment() {

    private lateinit var binding: FragmentBasicBinding

    private val adapter = FormAdapter(true)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.formView.setFormAdapter(adapter)
        adapter.setNewInstance {
            addPart {
                addText("Text") {
                    content = "测试内容"
                }
                addText("Text") {
                    content = "测试内容"
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
            }
        }
    }
}