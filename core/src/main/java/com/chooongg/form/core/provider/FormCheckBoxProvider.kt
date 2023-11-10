package com.chooongg.form.core.provider

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.getTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.BaseOptionForm
import com.chooongg.form.core.item.FormCheckBox
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.OptionStateAdapter
import com.chooongg.form.core.part.BaseFormPartAdapter
import com.chooongg.form.core.style.BaseStyle
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.coroutines.CoroutineScope
import kotlin.math.min

class FormCheckBoxProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        RecyclerView(parent.context).also {
            it.id = R.id.formInternalContentView
            it.isNestedScrollingEnabled = false
            it.overScrollMode = View.OVER_SCROLL_NEVER
            it.layoutManager = FlexboxLayoutManager(it.context)
            it.adapter = ConcatAdapter(OptionStateAdapter(style), ChildAdapter(style))
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        with(view as RecyclerView) {
            val concatAdapter = adapter as ConcatAdapter
            val optionStateAdapter = concatAdapter.adapters[0] as OptionStateAdapter
            val childAdapter = concatAdapter.adapters[1] as ChildAdapter
            configOption(holder, optionStateAdapter, childAdapter, item, enabled)
            val flexboxLayoutManager = layoutManager as FlexboxLayoutManager
            val gravity = holder.typeset.obtainContentGravity(holder, item)
            flexboxLayoutManager.justifyContent = when {
                gravity and Gravity.END == Gravity.END -> JustifyContent.FLEX_END
                gravity and Gravity.CENTER == Gravity.CENTER -> JustifyContent.CENTER
                else -> JustifyContent.FLEX_START
            }
        }
        loadOption(holder, item)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(scope, holder, view, item, enabled, payloads)
            return
        }
        if (payloads.contains("changeOption")) {
            with(view as RecyclerView) {
                val concatAdapter = adapter as ConcatAdapter
                val optionStateAdapter = concatAdapter.adapters[0] as OptionStateAdapter
                val childAdapter = concatAdapter.adapters[1] as ChildAdapter
                configOption(holder, optionStateAdapter, childAdapter, item, enabled)
            }
        }
    }

    private fun loadOption(holder: FormViewHolder, item: BaseForm?) {
        val itemOption = item as? BaseOptionForm<*>
        if (itemOption?.isNeedToLoadOption(holder) == true) {
            val adapter = holder.bindingAdapter as? BaseFormPartAdapter ?: return
            item.loadOption(holder) {
                holder.itemView.post {
                    val position = adapter.indexOf(item)
                    if (position != null) {
                        adapter.notifyItemChanged(position, "changeOption")
                    }
                }
            }
        }
    }

    private fun configOption(
        holder: FormViewHolder,
        optionStateAdapter: OptionStateAdapter,
        childAdapter: ChildAdapter,
        item: BaseForm?,
        enabled: Boolean
    ) {
        if (item !is FormCheckBox) return
        optionStateAdapter.update(item.optionLoadResult, item.options.isNullOrEmpty(), enabled)
        childAdapter.provider = this
        childAdapter.formHolder = holder
        childAdapter.item = item
        childAdapter.update(item.options, enabled)
    }

    private class ChildAdapter(private val style: BaseStyle) :
        RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

        var provider: FormCheckBoxProvider? = null

        var formHolder: FormViewHolder? = null

        var item: FormCheckBox? = null

        var enabled: Boolean = false

        private var options: List<IOption> = listOf()

        private class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view)

        @SuppressLint("NotifyDataSetChanged")
        fun update(options: List<IOption>?, enabled: Boolean) {
            this.options = options ?: listOf()
            this.enabled = enabled
            notifyDataSetChanged()
        }

        override fun getItemCount() = options.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChildViewHolder(
            MaterialCheckBox(parent.context).apply {
                minWidth = 0
                minHeight = 0
                minimumWidth = 0
                minimumHeight = 0
                compoundDrawablePadding = 0
                setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                background = RippleDrawable(
                    ColorStateList.valueOf(
                        context.obtainStyledAttributes(intArrayOf(android.R.attr.colorControlHighlight))
                            .use { it.getColor(0, Color.GRAY) }),
                    null,
                    null
                ).also {
                    it.radius = FormUtils.getFontHeight(this) / 2 + min(
                        style.insideInfo.middleStart,
                        min(style.insideInfo.middleTop, style.insideInfo.middleBottom)
                    )
                }
                setPaddingRelative(
                    0, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
            }
        )

        @Suppress("UNCHECKED_CAST")
        override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
            val option = options[position]
            with(holder.itemView as MaterialCheckBox) {
                setOnCheckedChangeListener(null)
                isEnabled = enabled
                isChecked = item?.content == option
                text = option.getSpannableString(context)
                if (provider != null && formHolder != null && item != null) {
                    setOnCheckedChangeListener { _, checked ->
                        FormUtils.hideIme(this)
                        if (item!!.content is List<*>) {
                            val content = ArrayList(item!!.content as List<IOption>)
                            if (checked) content.add(option) else content.remove(option)
                            provider!!.changeContentAndNotifyLinkage(formHolder!!, item!!, content)
                        }
                    }
                }
            }
        }
    }
}