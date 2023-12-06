package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chooongg.form.simple.R
import com.chooongg.form.simple.viewMode.StyleViewModel

class StyleFragment : BaseFragment() {

    private val model by viewModels<StyleViewModel>()


    override fun change() {
        model.adapter.isEnabled = !model.adapter.isEnabled
    }

    override fun output() {
        formView.smoothScrollToPosition(35)
    }

    override fun errorNotify() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.style)
        formView.setFormAdapter(model.adapter)
    }
}