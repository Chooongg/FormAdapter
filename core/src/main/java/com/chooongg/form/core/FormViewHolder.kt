package com.chooongg.form.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job

class FormViewHolder(
    val styleLayout: ViewGroup?,
    val typesetLayout: ViewGroup?,
    val view: View
) : RecyclerView.ViewHolder(styleLayout ?: typesetLayout ?: view) {

    var job: Job? = null

}