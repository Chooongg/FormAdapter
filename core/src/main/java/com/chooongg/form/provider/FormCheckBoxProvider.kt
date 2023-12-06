package com.chooongg.form.provider

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.use
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chooongg.form.FormUtils
import com.chooongg.form.FormViewChildHolder
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.getTextAppearance
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.BaseOptionForm
import com.chooongg.form.item.FormCheckBox
import com.chooongg.form.option.IOption
import com.chooongg.form.option.OptionLoadResult
import com.chooongg.form.style.BaseStyle
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlin.math.min

class FormCheckBoxProvider : BaseFormProvider() {

    private val recycledPool = RecycledViewPool()

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        FrameLayout(parent.context).also { outer ->
            outer.addView(LinearLayoutCompat(outer.context).also {
                it.orientation = LinearLayoutCompat.HORIZONTAL
                it.setPaddingRelative(
                    style.insideInfo.middleStart, style.insideInfo.middleTop,
                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
                )
                val textView = MaterialTextView(it.context).apply {
                    id = R.id.formInternalContentChildView
//                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
//                    textDirection = TextView.TEXT_DIRECTION_LOCALE
                    setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                }
                val textHeight = FormUtils.getFontHeight(textView)
                it.addView(textView, LinearLayoutCompat.LayoutParams(0, -2).apply {
                    weight = 1f
                    gravity = Gravity.CENTER_VERTICAL
                })
                it.addView(CircularProgressIndicator(it.context).apply {
                    id = R.id.formInternalContentChildSecondView
                    isIndeterminate = true
                    trackThickness = textHeight / 10
                    indicatorSize = textHeight / 2
                }, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = style.insideInfo.middleEnd
                })
            })
            outer.addView(RecyclerView(outer.context).apply {
                id = R.id.formInternalContentView
                isNestedScrollingEnabled = false
                overScrollMode = View.OVER_SCROLL_NEVER
                setRecycledViewPool(recycledPool)
                layoutManager = FlexboxLayoutManager(context)
                adapter = ChildAdapter(style)
            })
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        with(view.findViewById<RecyclerView>(R.id.formInternalContentView)) {
            val gravity = holder.typeset.obtainContentGravity(holder, item)
            configOption(holder, adapter as ChildAdapter, item, gravity, enabled)
            val flexboxLayoutManager = layoutManager as FlexboxLayoutManager
            flexboxLayoutManager.justifyContent = when {
                gravity and Gravity.END == Gravity.END -> JustifyContent.FLEX_END
                gravity and Gravity.CENTER == Gravity.CENTER -> JustifyContent.CENTER
                else -> JustifyContent.FLEX_START
            }
        }
        loadOption(holder, item)
    }

    override fun onBindViewHolderOtherPayload(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean,
        payload: Any
    ) {
        if (payload == BaseOptionForm.CHANGE_OPTION_PAYLOAD_FLAG) {
            with(view.findViewById<RecyclerView>(R.id.formInternalContentView)) {
                configOption(holder, adapter as ChildAdapter, item, null, enabled)
            }
        }
    }

    private fun configOption(
        holder: FormViewHolder,
        childAdapter: ChildAdapter,
        item: BaseForm?,
        gravity: Int?,
        enabled: Boolean
    ) {
        if (item !is FormCheckBox) return
        with(holder.itemView.findViewById<MaterialTextView>(R.id.formInternalContentChildView)) {
            if (gravity != null) setGravity(gravity)
            when (item.optionLoadResult) {
                is OptionLoadResult.Loading -> {
                    visibility = View.VISIBLE
                    hint = context.getString(R.string.formOptionsLoading)
                }

                is OptionLoadResult.Error -> {
                    visibility = View.VISIBLE
                    hint = context.getString(R.string.formOptionsError)
                }

                is OptionLoadResult.Empty -> {
                    visibility = View.VISIBLE
                    hint = context.getString(R.string.formOptionsEmpty)
                }

                else -> if (item.options.isNullOrEmpty()) {
                    visibility = View.VISIBLE
                    hint = context.getString(R.string.formOptionsEmpty)
                } else {
                    visibility = View.GONE
                    hint = null
                }
            }
        }
        with(holder.itemView.findViewById<CircularProgressIndicator>(R.id.formInternalContentChildSecondView)) {
            visibility =
                if (item.optionLoadResult is OptionLoadResult.Loading) View.VISIBLE else View.GONE
        }
        childAdapter.provider = this
        childAdapter.formHolder = holder
        childAdapter.item = item
        childAdapter.update(item.options, enabled)
    }

    private class ChildAdapter(private val style: BaseStyle) :
        RecyclerView.Adapter<FormViewChildHolder>() {

        var provider: FormCheckBoxProvider? = null

        var formHolder: FormViewHolder? = null

        var item: FormCheckBox? = null

        var enabled: Boolean = false

        private var options: List<IOption> = listOf()

        @SuppressLint("NotifyDataSetChanged")
        fun update(options: List<IOption>?, enabled: Boolean) {
            this.options = options ?: listOf()
            this.enabled = enabled
            notifyDataSetChanged()
        }

        override fun getItemCount() = options.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FormViewChildHolder(
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
        override fun onBindViewHolder(holder: FormViewChildHolder, position: Int) {
            val option = options[position]
            with(holder.itemView as MaterialCheckBox) {
                setOnCheckedChangeListener(null)
                isEnabled = enabled
                isChecked = (item?.content as? List<*>)?.contains(option) ?: false
                text = option.getSpannableString(context)
                if (provider != null && formHolder != null && item != null) {
                    setOnCheckedChangeListener { _, checked ->
                        FormUtils.hideIme(this)
                        val content = if (item!!.content is List<*>) {
                            ArrayList<IOption>(item!!.content as List<IOption>)
                        } else ArrayList()
                        if (checked) {
                            if (!content.contains(option)) content.add(option)
                        } else content.remove(option)
                        provider!!.changeContentAndNotifyLinkage(formHolder!!, item!!, content)
                    }
                }
            }
        }
    }
}