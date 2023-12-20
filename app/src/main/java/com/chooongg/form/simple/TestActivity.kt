package com.chooongg.form.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chooongg.form.simple.databinding.ActivityTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestActivity : AppCompatActivity() {

    val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.test.text = "测试文本"
        lifecycleScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.animateText("测试输入文本")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.animateText("测试文本111")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.animateText("222测试文本")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.animateText("测试文本44")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.animateText("测试22文本")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.animateText("测试文本")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.animateText("123测试文本")
            }
        }
    }
}