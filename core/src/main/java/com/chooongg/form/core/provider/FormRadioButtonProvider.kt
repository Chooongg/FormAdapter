package com.chooongg.form.core.provider

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.getTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormRadioButton
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.OptionStateAdapter
import com.chooongg.form.core.style.BaseStyle
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlinx.coroutines.CoroutineScope

class FormRadioButtonProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        RecyclerView(parent.context).apply {
            id = R.id.formInternalContentView
            isNestedScrollingEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = FlexboxLayoutManager(context)
            adapter = ConcatAdapter(OptionStateAdapter(style), ChildAdapter(style))
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemRadioButton = item as? FormRadioButton
        with(view as RecyclerView) {
            val concatAdapter = adapter as ConcatAdapter
            val optionStateAdapter = concatAdapter.adapters[0] as OptionStateAdapter
            val childAdapter = concatAdapter.adapters[1] as ChildAdapter
            configOption(optionStateAdapter, childAdapter, itemRadioButton, enabled)
            val flexboxLayoutManager = layoutManager as FlexboxLayoutManager
            val gravity = holder.typeset.obtainContentGravity(holder, item)
            flexboxLayoutManager.justifyContent = when {
                gravity and Gravity.END == Gravity.END -> JustifyContent.FLEX_END
                gravity and Gravity.CENTER == Gravity.CENTER -> JustifyContent.CENTER
                else -> JustifyContent.FLEX_START
            }
        }
    }

    private fun configOption(
        optionStateAdapter: OptionStateAdapter,
        childAdapter: ChildAdapter,
        item: FormRadioButton?,
        enabled: Boolean
    ) {
        optionStateAdapter.update(item?.optionLoadResult)
        childAdapter.update(item?.options, enabled)
    }

    private class ChildAdapter(private val style: BaseStyle) :
        RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

        private var options: List<IOption> = listOf()

        private var enabled: Boolean = false

        private class ChildViewHolder(view: View) : ViewHolder(view)

        @SuppressLint("NotifyDataSetChanged")
        fun update(options: List<IOption>?, enabled: Boolean) {
            this.options = options ?: listOf()
            this.enabled = enabled
            notifyDataSetChanged()
        }

        override fun getItemCount() = options.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChildViewHolder(
            MaterialRadioButton(parent.context).apply {
                setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
            }
        )

        override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
            val option = options[position]
            with(holder.itemView as MaterialRadioButton) {
                isEnabled = enabled
                text = option.getSpannableString(context)
            }
        }
    }
}