package com.chooongg.form.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chooongg.form.simple.databinding.ActivityTestBinding
import com.yy.mobile.rollingtextview.CharOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestActivity : AppCompatActivity() {

    val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.test.addCharOrder(arrayOf('.'))
        binding.test.addCharOrder(CharOrder.Number)
        binding.test.setText("421315.51")
        lifecycleScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.setText("845.21")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.setText("876.5")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.setText("1")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.setText("5124.32")
            }
            delay(2000)
            withContext(Dispatchers.Main) {
                binding.test.setText("5093.4")
            }
        }
    }
}