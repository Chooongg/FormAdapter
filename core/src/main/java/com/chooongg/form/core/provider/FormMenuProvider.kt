package com.chooongg.form.core.provider

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
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
import com.google.android.material.theme.overlay.MaterialThemeOverlay
import kotlinx.coroutines.CoroutineScope

class FormMenuProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        LinearLayoutCompat(parent.context).also { layout ->
            layout.orientation = LinearLayoutCompat.HORIZONTAL
            layout.isBaselineAligned = false
            layout.gravity = Gravity.CENTER_VERTICAL
            layout.addView(AppCompatImageView(layout.context).apply {
                id = R.id.formInternalContentIconView
            }, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                topMargin = style.insideInfo.top
                bottomMargin = style.insideInfo.bottom
                marginEnd = style.insideInfo.middleStart
            })
            layout.addView(MaterialTextView(layout.context).apply {
                id = R.id.formInternalNameView
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceName))
                tag = textColors
            }, LinearLayoutCompat.LayoutParams(0, -2).apply {
                weight = 1f
                topMargin = style.insideInfo.top
                bottomMargin = style.insideInfo.bottom
                marginEnd = style.insideInfo.middleEnd
            })
            val contentView = MaterialTextView(layout.context).apply {
                id = R.id.formInternalContentView
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
                tag = textColors
            }
            layout.addView(contentView, LinearLayoutCompat.LayoutParams(-2, -2).apply {
                topMargin = style.insideInfo.top
                bottomMargin = style.insideInfo.bottom
                marginEnd = style.insideInfo.middleEnd
            })
            layout.addView(MaterialTextView(layout.context).apply {
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
            layout.addView(
                AppCompatImageView(layout.context).apply {
                    id = R.id.formInternalContentChildSecondView
                    setImageResource(R.drawable.ic_form_arrow_end)
                },
                LinearLayoutCompat.LayoutParams(
                    FormUtils.getFontHeight(contentView),
                    FormUtils.getFontHeight(contentView)
                ).apply {
                    topMargin = style.insideInfo.top
                    bottomMargin = style.insideInfo.bottom
                    marginEnd = style.insideInfo.middleEnd
                })
            layout.setPaddingRelative(
                style.insideInfo.start, 0, style.insideInfo.end - style.insideInfo.middleEnd, 0
            )
            val buttonStyle = layout.context.obtainStyledAttributes(
                intArrayOf(R.attr.formButtonTextStyle)
            ).use { it.getResourceId(0, R.style.Form_Button_TextButton) }
            val wrap = MaterialThemeOverlay.wrap(layout.context, null, 0, buttonStyle)
            val rippleColor = wrap.obtainStyledAttributes(
                buttonStyle, intArrayOf(com.google.android.material.R.attr.rippleColor)
            ).use { it.getColorStateList(0) } ?: ColorStateList.valueOf(Color.GRAY)
            val shapeDrawable = MaterialShapeDrawable(
                ShapeAppearanceModel.builder(
                    layout.context,
                    layout.context.obtainStyledAttributes(intArrayOf(R.attr.formShapeAppearanceCorner))
                        .use {
                            it.getResourceId(
                                0,
                                com.google.android.material.R.style.ShapeAppearance_Material3_Corner_Medium
                            )
                        },
                    0
                ).build()
            )
            layout.background = RippleDrawable(rippleColor, null, shapeDrawable)
        }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        view.isEnabled = item.isRealEnable(enabled)
        val itemMenu = item as? FormMenu
        val nameView = view.findViewById<MaterialTextView>(R.id.formInternalNameView).apply {
            visibility = if (item.name != null) {
                text = FormUtils.getText(context, item.name)
                setTextColor(itemMenu?.nameColor?.invoke(context) ?: tag as ColorStateList)
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        with(view.findViewById<AppCompatImageView>(R.id.formInternalContentIconView)) {
            visibility = if (itemMenu?.icon != null) {
                holder.style.iconProvider.setSrc(this, itemMenu.icon)
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
            visibility = if (item.hint != null) {
                text = FormUtils.getText(context, item.hint)
                setTextColor(itemMenu?.contentColor?.invoke(context) ?: tag as ColorStateList)
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
                bubbleBackground.fillColor = itemMenu.bubbleColor?.invoke(context)
                    ?: ColorStateList.valueOf(context.obtainStyledAttributes(
                        intArrayOf(com.google.android.material.R.attr.colorError)
                    ).use { it.getColor(0, Color.GRAY) })
                setTextColor(
                    itemMenu.bubbleOnColor?.invoke(context)
                        ?: ColorStateList.valueOf(context.obtainStyledAttributes(
                            intArrayOf(com.google.android.material.R.attr.colorOnError)
                        ).use { it.getColor(0, Color.GRAY) })
                )
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
        view.setOnClickListener {
            val adapter = holder.bindingAdapter as? BaseFormPartAdapter
            adapter?.onItemClick(item, it)
        }
    }
}