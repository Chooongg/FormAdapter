package com.chooongg.form.core.option

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.R
import com.chooongg.form.core.getTextAppearance
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView

class OptionStateAdapter(private val style: BaseStyle) :
    RecyclerView.Adapter<OptionStateAdapter.OptionStateViewHolder>() {

    class OptionStateViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var optionLoadResult: OptionLoadResult<*>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun update(result: OptionLoadResult<*>?) {
        optionLoadResult = when (result) {
            is OptionLoadResult.Wait -> null
            is OptionLoadResult.Success -> null
            else -> result
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = if (optionLoadResult != null) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionStateViewHolder {
        return OptionStateViewHolder(LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.HORIZONTAL
            it.setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
            val textView = MaterialTextView(it.context).apply {
                id = R.id.formInternalContentChildView
                setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
            }
            val textHeight = FormUtils.getFontHeight(textView)
            it.addView(textView, LinearLayoutCompat.LayoutParams(0, -2).apply {
                weight = 1f
                gravity = Gravity.CENTER_VERTICAL
            })
            it.addView(CircularProgressIndicator(it.context).apply {
                id = R.id.formInternalContentChildSecondView
                trackThickness = textHeight / 10
                indicatorSize = textHeight / 2
                setIndicatorColor(textView.hintTextColors.defaultColor)
            }, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                gravity = Gravity.CENTER_VERTICAL
            })
        })
    }

    override fun onBindViewHolder(holder: OptionStateViewHolder, position: Int) {
        with(holder.itemView.findViewById<MaterialTextView>(R.id.formInternalContentChildView)) {
            hint = when (optionLoadResult) {
                is OptionLoadResult.Loading -> context.getString(R.string.formOptionsLoading)
                is OptionLoadResult.Empty -> context.getString(R.string.formOptionsEmpty)
                is OptionLoadResult.Error -> context.getString(R.string.formOptionsError)
                else -> null
            }
        }
        with(holder.itemView.findViewById<CircularProgressIndicator>(R.id.formInternalContentChildSecondView)) {
            visibility =
                if (optionLoadResult is OptionLoadResult.Loading) View.VISIBLE else View.GONE
        }
    }
}