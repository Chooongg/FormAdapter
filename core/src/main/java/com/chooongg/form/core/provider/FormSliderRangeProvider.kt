package com.chooongg.form.core.provider

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormSliderRange
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormSliderRangeProvider : BaseFormProvider() {
    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        RangeSlider(parent.context).apply {
            id = R.id.formInternalContentView
            val textHeight = with(MaterialTextView(context)) {
                setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
                measure(0, 0)
                measuredHeight
            }
            setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
            val height = textHeight + style.insideInfo.middleTop + style.insideInfo.middleBottom
            try {
                val widgetHeight = javaClass.superclass.getDeclaredField("widgetHeight")
                widgetHeight.isAccessible = true
                widgetHeight.setInt(this, height)
            } catch (_: Exception) {
            }
            haloRadius = height / 2
        }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        val itemSlider = item as? FormSliderRange
        with(view as RangeSlider) {
            clearOnChangeListeners()
            clearOnSliderTouchListeners()
            isEnabled = enabled
            valueFrom = itemSlider?.valueFrom ?: 0f
            valueTo = itemSlider?.valueTo ?: 100f
            stepSize = itemSlider?.stepSize ?: 0f
            values = try {
                if (item.content is List<*>) item.content as List<Float>
                else listOf(valueFrom, valueTo)
            } catch (e: Exception) {
                listOf(valueFrom, valueTo)
            }
            tickInactiveTintList = if (itemSlider?.showInactiveTick == true) {
                trackActiveTintList
            } else ColorStateList.valueOf(Color.TRANSPARENT)
            tickActiveTintList = if (itemSlider?.showActiveTick == true) {
                trackInactiveTintList
            } else ColorStateList.valueOf(Color.TRANSPARENT)
            setLabelFormatter(itemSlider?.formatter)
            addOnChangeListener { _, _, fromUser ->
                if (fromUser) changeContentAndNotifyLinkage(holder, item, values)
            }
            addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                override fun onStopTrackingTouch(slider: RangeSlider) = Unit
                override fun onStartTrackingTouch(slider: RangeSlider) {
                    FormUtils.hideIme(holder.itemView)
                }
            })
        }
    }
}