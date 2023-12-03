package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.simple.R
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
        model.adapter.setOnItemClickListener {
            Toast.makeText(context, "点击了按钮", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun change() {
        model.adapter.findOfField("button", false) {
            visibilityMode =
                if (visibilityMode == FormVisibilityMode.ALWAYS) FormVisibilityMode.NEVER else FormVisibilityMode.ALWAYS
        }
        model.adapter.isEnabled = !model.adapter.isEnabled
    }

    override fun output() {
        if (model.adapter.executeDataVerification()) {
            val output = model.adapter.executeOutput()
            model.adapter.findOfField("output", true, true) {
                content = output.toString(4)
            }
        }
    }

    override fun errorNotify() {
        model.adapter.errorNotifyOfField("input")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.basic)
        formView.setFormAdapter(model.adapter)
    }
}