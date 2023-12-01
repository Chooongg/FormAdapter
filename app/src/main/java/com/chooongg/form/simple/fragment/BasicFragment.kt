package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.chooongg.form.core.OnMenuClickGlobalListener
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.simple.R
import com.chooongg.form.simple.viewMode.BasicViewModel

class BasicFragment : BaseFragment() {

    private val model by viewModels<BasicViewModel>()

    override fun onViewCreated() {
        model.adapter.onMenuClickListener(object : OnMenuClickGlobalListener {
            override fun onMenuClick(menu: MenuItem, item: BaseForm) {

            }
        })
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
            Log.e("Form", output.toString(4), null)
            Toast.makeText(context, output.toString(2), Toast.LENGTH_SHORT).show()
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