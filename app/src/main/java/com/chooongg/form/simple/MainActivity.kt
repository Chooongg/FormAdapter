package com.chooongg.form.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.item.FormText
import com.chooongg.form.simple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter = FormAdapter(true)

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.formView.setFormAdapter(adapter)
        adapter.setNewInstance {

        }
    }
}