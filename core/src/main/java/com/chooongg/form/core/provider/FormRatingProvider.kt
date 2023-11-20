package com.chooongg.form.core.provider

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormRating
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormRatingProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        FrameLayout(parent.context).also {
            it.id = R.id.formInternalContentView
            it.clipChildren = false
            it.clipToPadding = false
            it.setPaddingRelative(
                style.insideInfo.middleStart, 0,
                style.insideInfo.middleEnd, 0
            )
            it.addView(
                AppCompatRatingBar(
                    it.context, null, com.google.android.material.R.attr.ratingBarStyleIndicator
                ).apply {
                    id = R.id.formInternalContentChildView
                }, FrameLayout.LayoutParams(-2, -2)
            )
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemRating = item as? FormRating
        if (view.layoutParams.height == -2) {
            val textView = MaterialTextView(view.context).apply {
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
                measure(0, 0)
            }
            view.updateLayoutParams<MarginLayoutParams> {
                height =
                    textView.measuredHeight + holder.style.insideInfo.middleTop + holder.style.insideInfo.middleBottom
            }
        }
        with(view.findViewById<AppCompatRatingBar>(R.id.formInternalContentChildView)) {
            setIsIndicator(!enabled)
            onRatingBarChangeListener = null
            numStars = itemRating?.numStars ?: 5
            stepSize = itemRating?.stepSize ?: 1f
            rating = (item.content as? Float) ?: 0f
            progressTintList = itemRating?.tint?.invoke(context) ?: ColorStateList.valueOf(
                context.obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorPrimary))
                    .use { it.getColor(0, Color.GRAY) }
            )
            setOnRatingBarChangeListener { _, value, _ ->
                if (itemRating?.needToChoose == true && value == 0f) {
                    changeContentAndNotifyLinkage(holder, item, null)
                } else changeContentAndNotifyLinkage(holder, item, value)
            }
            updateLayoutParams<FrameLayout.LayoutParams> {
                width = minimumHeight * numStars
                gravity =
                    holder.typeset.obtainContentGravity(holder, item) or Gravity.CENTER_VERTICAL
            }
        }
    }
}