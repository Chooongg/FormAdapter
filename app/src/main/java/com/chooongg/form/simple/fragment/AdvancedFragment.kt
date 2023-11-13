package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chooongg.form.simple.R
import com.chooongg.form.simple.viewMode.AdvancedViewModel

class AdvancedFragment : BaseFragment() {

    private val model by viewModels<AdvancedViewModel>()

    override fun change() {
        model.adapter.isEnabled = !model.adapter.isEnabled
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.advanced)
        formView.setFormAdapter(model.adapter)
    }
}