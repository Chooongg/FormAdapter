package com.chooongg.form.core.provider

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormMenu
import com.chooongg.form.core.part.BaseFormPartAdapter
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormMenuProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.HORIZONTAL
            it.isBaselineAligned = false
            it.gravity = Gravity.CENTER_VERTICAL
            it.addView(AppCompatImageView(it.context).apply {
                id = R.id.formInternalContentIconView
            }, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                topMargin = style.insideInfo.top
                bottomMargin = style.insideInfo.bottom
                marginEnd = style.insideInfo.middleStart
            })
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalNameView
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceName))
                tag = textColors
            }, LinearLayoutCompat.LayoutParams(0, -2).apply {
                weight = 1f
                topMargin = style.insideInfo.top
                bottomMargin = style.insideInfo.bottom
                marginEnd = style.insideInfo.middleEnd
            })
            val contentView = MaterialTextView(it.context).apply {
                id = R.id.formInternalContentView
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
                tag = textColors
            }
            it.addView(contentView, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                topMargin = style.insideInfo.top
                bottomMargin = style.insideInfo.bottom
                marginEnd = style.insideInfo.middleEnd
            })
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalBubbleView
                background = MaterialShapeDrawable(
                    ShapeAppearanceModel.builder(
                        context, R.style.Form_ShapeAppearance_Bubble, 0
                    ).build()
                )
                isElegantTextHeight = false
                gravity = Gravity.CENTER
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10f)
                val min = (18 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                minWidth = min
                minHeight = min
                val vertical = (2 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                val horizontal = (4 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                setPadding(horizontal, vertical, horizontal, vertical)
            }, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                topMargin = style.insideInfo.middleTop
                bottomMargin = style.insideInfo.middleBottom
                marginEnd = style.insideInfo.middleEnd
            })
            it.addView(
                AppCompatImageView(it.context).apply {
                    id = R.id.formInternalContentChildSecondView
                    setImageResource(R.drawable.ic_form_arrow_end)
                    imageTintList = contentView.hintTextColors
                },
                LinearLayoutCompat.LayoutParams(
                    FormUtils.getFontHeight(contentView),
                    FormUtils.getFontHeight(contentView)
                ).apply {
                    topMargin = style.insideInfo.top
                    bottomMargin = style.insideInfo.bottom
                    marginEnd = style.insideInfo.middleEnd
                })
            it.setPaddingRelative(
                style.insideInfo.start, 0, style.insideInfo.end - style.insideInfo.middleEnd, 0
            )
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        view.background = ResourcesCompat.getDrawable(
            view.resources, R.drawable.ripple_form_foreground, view.context.theme
        )
        val itemMenu = item as? FormMenu

        val nameView = view.findViewById<MaterialTextView>(R.id.formInternalNameView).apply {
            visibility = if (item.name != null) {
                text = FormUtils.getText(context, item.name)
                if (itemMenu?.nameColor != null) {
                    setTextColor(itemMenu.nameColor!!.invoke(context))
                } else {
                    setTextColor(tag as ColorStateList)
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        with(view.findViewById<AppCompatImageView>(R.id.formInternalContentIconView)) {
            visibility = if (itemMenu?.icon != null) {
                FormManager.Default.iconProvider.setSrc(this, itemMenu.icon)
                imageTintList = if (itemMenu.iconTint != null) {
                    itemMenu.iconTint!!.invoke(context)
                } else nameView.textColors
                updateLayoutParams<LinearLayoutCompat.LayoutParams> {
                    if (itemMenu.iconSize != null) {
                        width = itemMenu.iconSize!!
                        height = itemMenu.iconSize!!
                    } else {
                        width = FormUtils.getFontHeight(nameView)
                        height = FormUtils.getFontHeight(nameView)
                    }
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        with(view.findViewById<MaterialTextView>(R.id.formInternalContentView)) {
            visibility = if (item.content != null) {
                text = item.getContentText(context, enabled)
                if (itemMenu?.contentColor != null) {
                    setTextColor(itemMenu.contentColor!!.invoke(context))
                } else {
                    setTextColor(tag as ColorStateList)
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        with(view.findViewById<MaterialTextView>(R.id.formInternalBubbleView)) {
            visibility = if (itemMenu?.bubbleText != null) {
                text = when (val bubble = itemMenu.bubbleText!!) {
                    is Int -> if (bubble > 99) "99+" else bubble.toString()
                    else -> bubble.toString()
                }
                val bubbleBackground = background as MaterialShapeDrawable
                if (itemMenu.bubbleColor != null) {
                    bubbleBackground.fillColor = itemMenu.bubbleColor!!.invoke(context)
                } else {
                    bubbleBackground.fillColor =
                        ColorStateList.valueOf(context.obtainStyledAttributes(
                            intArrayOf(com.google.android.material.R.attr.colorError)
                        ).use { it.getColor(0, Color.GRAY) })
                }
                if (itemMenu.bubbleOnColor != null) {
                    setTextColor(itemMenu.bubbleOnColor!!.invoke(context))
                } else {
                    setTextColor(context.obtainStyledAttributes(
                        intArrayOf(com.google.android.material.R.attr.colorOnError)
                    ).use { it.getColor(0, Color.GRAY) })
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        with(view.findViewById<AppCompatImageView>(R.id.formInternalContentChildSecondView)) {
            visibility = if (itemMenu?.showNextIcon == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            val adapter = holder.bindingAdapter as? BaseFormPartAdapter
            adapter?.onItemClick(item)
        }
    }
}