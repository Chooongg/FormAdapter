package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chooongg.form.core.enum.FormVisibilityMode
import com.chooongg.form.simple.R
import com.chooongg.form.simple.viewMode.BasicViewModel

class BasicFragment : BaseFragment() {

    private val model by viewModels<BasicViewModel>()

    override fun change() {
        model.adapter.findOfField("button", false) {
            visibilityMode =
                if (visibilityMode == FormVisibilityMode.ALWAYS) FormVisibilityMode.NEVER else FormVisibilityMode.ALWAYS
        }
        model.adapter.isEnabled = !model.adapter.isEnabled
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.basic)
        formView.setFormAdapter(model.adapter)
    }
}