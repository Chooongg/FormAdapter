package com.chooongg.form.provider

import android.animation.AnimatorInflater
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
        val style = when (itemButton?.buttonStyle ?: FormButton.ButtonStyle.DEFAULT) {
            FormButton.ButtonStyle.DEFAULT -> view.context.obtainStyledAttributes(
                intArrayOf(R.attr.formButtonStyle)
            ).use { it.getResourceId(0, R.style.Form_Button) }

            FormButton.ButtonStyle.TEXT -> view.context.obtainStyledAttributes(
                intArrayOf(R.attr.formButtonTextStyle)
            ).use { it.getResourceId(0, R.style.Form_Button_TextButton) }

            FormButton.ButtonStyle.TONAL -> view.context.obtainStyledAttributes(
                intArrayOf(R.attr.formButtonTonalStyle)
            ).use { it.getResourceId(0, R.style.Form_Button_TonalButton) }

            FormButton.ButtonStyle.OUTLINED -> view.context.obtainStyledAttributes(
                intArrayOf(R.attr.formButtonOutlinedStyle)
            ).use { it.getResourceId(0, R.style.Form_Button_OutlinedButton) }

            FormButton.ButtonStyle.ELEVATED -> view.context.obtainStyledAttributes(
                intArrayOf(R.attr.formButtonElevatedStyle)
            ).use { it.getResourceId(0, R.style.Form_Button_ElevatedButton) }

            FormButton.ButtonStyle.UN_ELEVATED -> view.context.obtainStyledAttributes(
                intArrayOf(R.attr.formButtonUnelevatedStyle)
            ).use { it.getResourceId(0, R.style.Form_Button_UnelevatedButton) }
        }
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
        view.elevation = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.elevation)
        ).use { it.getDimension(0, 0f) }

        val stateListId = wrap.obtainStyledAttributes(
            style, intArrayOf(android.R.attr.stateListAnimator)
        ).use { it.getResourceId(0, 0) }
        view.stateListAnimator = if (stateListId != 0) {
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
}