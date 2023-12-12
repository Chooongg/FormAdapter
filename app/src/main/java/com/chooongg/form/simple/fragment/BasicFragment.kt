package com.chooongg.form.simple.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.chooongg.form.enum.FormVisibilityMode
import com.chooongg.form.simple.R
import com.chooongg.form.simple.TestActivity
import com.chooongg.form.simple.viewMode.BasicViewModel

class BasicFragment : BaseFragment() {

    private val model by viewModels<BasicViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        model.adapter.setOnMenuClickListener { context, menu, item ->
            Toast.makeText(context, "${item.name}, ${menu.title}", Toast.LENGTH_SHORT)
                .show()
        }
        model.adapter.setOnItemClickListener { item, view ->
            Toast.makeText(context, "点击了按钮", Toast.LENGTH_SHORT).show()
        }
        model.adapter.setOnOperationButtonClickListener {
            startActivity(Intent(context, TestActivity::class.java))
            Toast.makeText(context, "点击了操作按钮", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun change() {
        model.adapter.updateItemOfField("button", false) {
            visibilityMode =
                if (visibilityMode == FormVisibilityMode.ALWAYS) FormVisibilityMode.NEVER else FormVisibilityMode.ALWAYS
        }
        model.adapter.isEnabled = !model.adapter.isEnabled
    }

    override fun output() {
        if (model.adapter.executeDataVerification()) {
            val output = model.adapter.executeOutput()
            model.adapter.updateItemOfField("output", true) {
                content = output.toString(4)
            }
        }
    }

    override fun errorNotify() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.basic)
        formView.setFormAdapter(model.adapter)
    }
}