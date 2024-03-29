package com.chooongg.form

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.style.BaseStyle
import com.chooongg.form.typeset.BaseTypeset
import kotlinx.coroutines.Job

class FormViewHolder(
    val style: BaseStyle,
    val styleLayout: ViewGroup?,
    val typeset: BaseTypeset,
    val typesetLayout: ViewGroup?,
    val view: View
) : RecyclerView.ViewHolder(styleLayout ?: typesetLayout ?: view) {

    var job: Job? = null

}