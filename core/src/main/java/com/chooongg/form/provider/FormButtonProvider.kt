package com.chooongg.form.provider

import android.animation.AnimatorInflater
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.form.FormUtils
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.FormButton
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.style.BaseStyle
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.theme.overlay.MaterialThemeOverlay
import kotlinx.coroutines.CoroutineScope

open class FormButtonProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View {
        return ConstraintLayout(parent.context).also {
            it.addView(MaterialButton(parent.context).apply {
                id = R.id.formInternalContentView
                layoutParams = ConstraintLayout.LayoutParams(0, -2).apply {
                    matchConstraintMaxWidth = maxWidth
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topMargin = style.insideInfo.middleTop - insetTop
                    bottomMargin = style.insideInfo.middleBottom - insetBottom
                    marginStart = style.insideInfo.middleStart
                    marginEnd = style.insideInfo.middleEnd
                }
            })
        }
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemButton = item as? FormButton
        with(view.findViewById<MaterialButton>(R.id.formInternalContentView)) {
            isEnabled = enabled
            text = FormUtils.getText(context, item.name)
            hint = FormUtils.getText(context, item.hint)
            gravity = itemButton?.textGravity ?: Gravity.CENTER
            holder.style.iconProvider.setButtonIcon(this, itemButton?.icon)
            iconSize = itemButton?.iconSize ?: FormUtils.getFontHeight(this)
            iconGravity = itemButton?.iconGravity ?: MaterialButton.ICON_GRAVITY_TEXT_START
            configButtonGravity(this, holder.typeset.obtainContentGravity(holder, item))
            configButtonStyle(this, itemButton)
            configButtonClick(holder, this, item)
        }
    }

    private fun configButtonStyle(view: MaterialButton, itemButton: FormButton?) {
        val style = getButtonStyle(view.context, itemButton?.buttonStyle)
        val wrap = MaterialThemeOverlay.wrap(view.context, null, 0, style)
        view.setTextColor(wrap.obtainStyledAttributes(
            style, intArrayOf(android.R.attr.textColor)
        ).use { it.getColorStateList(0) })
        view.iconTint = if (itemButton?.iconTint == null) {
            wrap.obtainStyledAttributes(
                style, intArrayOf(androidx.appcompat.R.attr.iconTint)
            ).use { it.getColorStateList(0) }
        } else itemButton.iconTint!!.invoke(view.context)
        view.backgroundTintList = wrap.obtainStyledAttributes(
            style, intArrayOf(androidx.appcompat.R.attr.backgroundTint)
        ).use { it.getColorStateList(0) }
        view.strokeColor = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.strokeColor)
        ).use { it.getColorStateList(0) }
        view.strokeWidth = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.strokeWidth)
        ).use { it.getDimensionPixelSize(0, 0) }
        view.rippleColor = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.rippleColor)
        ).use { it.getColorStateList(0) }
        view.elevation = itemButton?.elevation ?: wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.elevation)
        ).use { it.getDimension(0, 0f) }
        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder(
                view.context, wrap.obtainStyledAttributes(
                    style, intArrayOf(com.google.android.material.R.attr.shapeAppearance)
                ).use {
                    it.getResourceId(
                        0, com.google.android.material.R.style.ShapeAppearance_Material3_Corner_Full
                    )
                }, 0
            ).build()
        val stateListId = wrap.obtainStyledAttributes(
            style, intArrayOf(android.R.attr.stateListAnimator)
        ).use { it.getResourceId(0, 0) }
        view.stateListAnimator = if (stateListId != 0 && itemButton?.elevation == null) {
            AnimatorInflater.loadStateListAnimator(wrap, stateListId)
        } else null
    }

    private fun configButtonGravity(view: MaterialButton, contentGravity: Int) {
        if (contentGravity == Gravity.NO_GRAVITY) {
            view.updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
        } else {
            view.updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                when {
                    contentGravity and Gravity.START == Gravity.START -> {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.UNSET
                    }

                    contentGravity and Gravity.END == Gravity.END -> {
                        startToStart = ConstraintLayout.LayoutParams.UNSET
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    }

                    contentGravity and Gravity.CENTER == Gravity.CENTER -> {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    }

                    contentGravity and Gravity.CENTER_HORIZONTAL == Gravity.CENTER_HORIZONTAL -> {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                }
            }
        }
    }

    protected open fun configButtonClick(holder: FormViewHolder, view: View, item: BaseForm) {
        view.setOnClickListener {
            val adapter = holder.bindingAdapter as? BaseFormPartAdapter
            adapter?.onItemClick(item, it)
        }
    }

    protected fun getButtonStyle(context: Context, buttonStyle: FormButton.ButtonStyle?): Int {
        val attr = when (buttonStyle) {
            FormButton.ButtonStyle.ELEVATED -> R.attr.formButtonElevatedStyle
            FormButton.ButtonStyle.UN_ELEVATED -> R.attr.formButtonUnelevatedStyle
            FormButton.ButtonStyle.PRIMARY -> R.attr.formButtonPrimaryStyle
            FormButton.ButtonStyle.PRIMARY_TONAL -> R.attr.formButtonPrimaryTonalStyle
            FormButton.ButtonStyle.TEXT -> R.attr.formButtonTextStyle
            FormButton.ButtonStyle.PRIMARY_OUTLINED -> R.attr.formButtonPrimaryOutlinedStyle
            FormButton.ButtonStyle.SECONDARY -> R.attr.formButtonSecondaryStyle
            FormButton.ButtonStyle.SECONDARY_TEXT -> R.attr.formButtonSecondaryTextStyle
            FormButton.ButtonStyle.TONAL -> R.attr.formButtonTonalStyle
            FormButton.ButtonStyle.OUTLINED -> R.attr.formButtonOutlinedStyle
            FormButton.ButtonStyle.TERTIARY -> R.attr.formButtonTertiaryStyle
            FormButton.ButtonStyle.TERTIARY_TONAL -> R.attr.formButtonTertiaryTonalStyle
            FormButton.ButtonStyle.TERTIARY_TEXT -> R.attr.formButtonTertiaryTextStyle
            FormButton.ButtonStyle.TERTIARY_OUTLINED -> R.attr.formButtonTertiaryOutlinedStyle
            FormButton.ButtonStyle.ERROR -> R.attr.formButtonErrorStyle
            FormButton.ButtonStyle.ERROR_TONAL -> R.attr.formButtonErrorTonalStyle
            FormButton.ButtonStyle.ERROR_TEXT -> R.attr.formButtonErrorTextStyle
            FormButton.ButtonStyle.ERROR_OUTLINED -> R.attr.formButtonErrorOutlinedStyle
            FormButton.ButtonStyle.CUSTOM1 -> R.attr.formButtonCustom1Style
            FormButton.ButtonStyle.CUSTOM2 -> R.attr.formButtonCustom2Style
            FormButton.ButtonStyle.CUSTOM3 -> R.attr.formButtonCustom3Style
            else -> R.attr.formButtonStyle
        }
        val default = when (buttonStyle) {
            FormButton.ButtonStyle.ELEVATED -> R.style.Form_Button_ElevatedButton
            FormButton.ButtonStyle.UN_ELEVATED -> R.style.Form_Button_UnelevatedButton
            FormButton.ButtonStyle.PRIMARY -> R.style.Form_Button_PrimaryButton
            FormButton.ButtonStyle.PRIMARY_TONAL -> R.style.Form_Button_PrimaryTonalButton
            FormButton.ButtonStyle.TEXT -> R.style.Form_Button_TextButton
            FormButton.ButtonStyle.PRIMARY_OUTLINED -> R.style.Form_Button_PrimaryOutlinedButton
            FormButton.ButtonStyle.SECONDARY -> R.style.Form_Button_SecondaryButton
            FormButton.ButtonStyle.TONAL -> R.style.Form_Button_TonalButton
            FormButton.ButtonStyle.SECONDARY_TEXT -> R.style.Form_Button_SecondaryTextButton
            FormButton.ButtonStyle.OUTLINED -> R.style.Form_Button_OutlinedButton
            FormButton.ButtonStyle.TERTIARY -> R.style.Form_Button_TertiaryButton
            FormButton.ButtonStyle.TERTIARY_TONAL -> R.style.Form_Button_TertiaryTonalButton
            FormButton.ButtonStyle.TERTIARY_TEXT -> R.style.Form_Button_TertiaryTextButton
            FormButton.ButtonStyle.TERTIARY_OUTLINED -> R.style.Form_Button_TertiaryOutlinedButton
            FormButton.ButtonStyle.ERROR -> R.style.Form_Button_ErrorButton
            FormButton.ButtonStyle.ERROR_TONAL -> R.style.Form_Button_ErrorTonalButton
            FormButton.ButtonStyle.ERROR_TEXT -> R.style.Form_Button_ErrorTextButton
            FormButton.ButtonStyle.ERROR_OUTLINED -> R.style.Form_Button_ErrorOutlinedButton
            else -> R.style.Form_Button
        }
        return context.obtainStyledAttributes(intArrayOf(attr)).use { it.getResourceId(0, default) }
    }
}