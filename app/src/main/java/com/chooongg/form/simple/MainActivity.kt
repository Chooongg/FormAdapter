package com.chooongg.form.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addInput
import com.chooongg.form.core.addInputFilled
import com.chooongg.form.core.addInputOutlined
import com.chooongg.form.core.addText
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.style.CardStyle
import com.chooongg.form.core.typeset.HorizontalTypeset
import com.chooongg.form.core.typeset.VerticalTypeset
import com.chooongg.form.simple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter = FormAdapter(true)

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.formView.setFormAdapter(adapter)
        adapter.setNewInstance {
            addPart { fillData() }
            addPart(CardStyle {
                typeset = HorizontalTypeset {
                    emsSize = 4
                }
            }) { fillData() }
            addPart(CardStyle {
                typeset = VerticalTypeset()
            }) { fillData() }
        }
    }

    private fun FormPartData.fillData() {
        addText("Text") {
            content = "测试内容"
        }
        addText("Text") {
            content = "测试内容"
        }
        addInput("Input") {
            hint = "请输入"
            content = "测试"
        }
//        addInputFilled("InputFilled") {
//            hint = "请输入"
//        }
        addInputOutlined("InputOutlined") {
            hint = "请输入"
        }
    }
}