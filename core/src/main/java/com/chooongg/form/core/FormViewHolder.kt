package com.chooongg.form.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.style.BaseStyle
import com.chooongg.form.core.typeset.BaseTypeset
import kotlinx.coroutines.Job

class FormViewHolder(
    val style: BaseStyle,
    val typeset: BaseTypeset,
    val styleLayout: ViewGroup?,
    val typesetLayout: ViewGroup?,
    val view: View
) : RecyclerView.ViewHolder(styleLayout ?: typesetLayout ?: view) {

    var job: Job? = null

}