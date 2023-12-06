package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.View
import com.chooongg.form.FormAdapter
import com.chooongg.form.addSlider
import com.chooongg.form.addText
import com.chooongg.form.data.FormPartData
import com.chooongg.form.multiColumn
import com.chooongg.form.singleLine
import com.chooongg.form.style.CardElevatedStyle
import com.chooongg.form.style.CardFilledStyle
import com.chooongg.form.style.CardOutlinedStyle
import com.chooongg.form.style.NoneStyle
import com.chooongg.form.style.NotAlignmentStyle
import com.chooongg.form.typeset.VerticalTypeset
import com.chooongg.form.simple.R

class StyleFragment : BaseFragment() {

    private val adapter = FormAdapter(true)

    override fun change() {
        adapter.isEnabled = !adapter.isEnabled
    }

    override fun output() {
        formView.smoothScrollToPosition(35)
    }

    override fun errorNotify() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.style)
        formView.setFormAdapter(adapter)

    }


}