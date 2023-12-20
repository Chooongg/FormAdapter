package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.chooongg.form.simple.R
import com.chooongg.form.simple.viewMode.StyleViewModel

class StyleFragment : BaseFragment() {

    private val model by viewModels<StyleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        toolbar.setTitle(R.string.style)
        formView.setFormAdapter(model.adapter)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model.created()
    }

    override fun change() {
        model.adapter.isEnabled = !model.adapter.isEnabled
    }

    override fun output() {
        formView.smoothScrollToPosition(35)
    }

    override fun errorNotify() {

    }
}