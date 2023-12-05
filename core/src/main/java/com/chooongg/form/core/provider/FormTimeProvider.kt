package com.chooongg.form.core.provider

import android.icu.text.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.enum.FormTimeMode
import com.chooongg.form.core.formTextAppearance
import com.chooongg.form.core.item.BaseForm
import com.chooongg.form.core.item.FormTime
import com.chooongg.form.core.style.BaseStyle
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import kotlinx.coroutines.CoroutineScope
import java.util.Calendar
import java.util.Locale

class FormTimeProvider : BaseFormProvider() {

    private var isShow: Boolean = false

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View =
        MaterialButton(
            parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
        ).apply {
            id = R.id.formInternalContentView
            minimumHeight = 0
            minimumWidth = 0
            minHeight = 0
            minWidth = 0
            insetBottom = 0
            insetTop = 0
            setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
            iconTint = hintTextColors
            iconGravity = MaterialButton.ICON_GRAVITY_END
            iconSize = FormUtils.getFontHeight(this)
            iconPadding =
                context.resources.getDimensionPixelSize(R.dimen.formIconPadding)
            setIconResource(R.drawable.ic_form_arrow_down)
            setPaddingRelative(
                style.insideInfo.middleStart, style.insideInfo.middleTop,
                style.insideInfo.middleEnd, style.insideInfo.middleBottom
            )
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        with(view as MaterialButton) {
            isEnabled = enabled
            text = item.getContentText(context, enabled)
            hint = FormUtils.getText(context, item.hint)
                ?: context.getString(R.string.formDefaultHintSelect)
            gravity = holder.typeset.obtainContentGravity(holder, item)
            if (item is FormTime) {
                setOnClickListener { onClickButton(holder, this, item) }
            } else setOnClickListener(null)
        }
    }

    private fun onClickButton(
        holder: FormViewHolder,
        view: View,
        item: BaseForm
    ) {
        if (isShow) return
        if (item !is FormTime) return
        val anchor = view as MaterialButton
        val activity = FormUtils.getActivity(view.context) as? AppCompatActivity
        if (activity == null) {
            Toast.makeText(view.context, R.string.formPickerOpenError, Toast.LENGTH_SHORT).show()
            return
        }
        isShow = true
        val calendar = Calendar.getInstance()
        val content = item.content as? Long
        if (content != null) {
            calendar.timeInMillis = content
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
        }
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        when (item.timeMode) {
            FormTimeMode.TIME -> MaterialTimePicker.Builder()
                .setTitleText(FormUtils.getText(view.context, item.name))
                .setInputMode(
                    when (item.inputMode) {
                        FormTime.INPUT_MODE_TEXT -> 1
                        else -> 0
                    }
                ).setTimeFormat(item.timeFormatMode)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build().apply {
                    addOnDismissListener { isShow = false }
                    addOnPositiveButtonClickListener {
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)
                        changeContentAndNotifyLinkage(holder, item, calendar.timeInMillis)
                        anchor.text = item.getContentText(view.context, true)
                    }
                }.show(activity.supportFragmentManager, "FormTimePicker")

            FormTimeMode.DATE -> MaterialDatePicker.Builder.datePicker()
                .setTitleText(FormUtils.getText(view.context, item.name))
                .setInputMode(
                    when (item.inputMode) {
                        FormTime.INPUT_MODE_TEXT -> 1
                        else -> 0
                    }
                ).setSelection(calendar.timeInMillis + calendar.timeZone.rawOffset)
                .setCalendarConstraints(item.calendarConstraints)
                .setDayViewDecorator(item.dayViewDecorator)
                .build().apply {
                    addOnDismissListener { isShow = false }
                    addOnPositiveButtonClickListener {
                        calendar.timeInMillis = it - calendar.timeZone.rawOffset
                        changeContentAndNotifyLinkage(holder, item, calendar.timeInMillis)
                        anchor.text = item.getContentText(view.context, true)
                    }
                }.show(activity.supportFragmentManager, "FormDatePicker")

            FormTimeMode.DATE_TIME -> {
                val newCalendar = Calendar.getInstance()
                newCalendar.timeInMillis = calendar.timeInMillis
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(FormUtils.getText(view.context, item.name))
                    .setInputMode(
                        when (item.inputMode) {
                            FormTime.INPUT_MODE_TEXT -> 1
                            else -> 0
                        }
                    ).setSelection(newCalendar.timeInMillis + newCalendar.timeZone.rawOffset)
                    .setCalendarConstraints(item.calendarConstraints)
                    .setDayViewDecorator(item.dayViewDecorator)
                    .build().apply {
                        addOnDismissListener { isShow = false }
                        addOnPositiveButtonClickListener {
                            newCalendar.timeInMillis = it - newCalendar.timeZone.rawOffset

                            val isChangeDay = when {
                                newCalendar.get(Calendar.YEAR) != calendar.get(Calendar.YEAR) -> true
                                newCalendar.get(Calendar.MONTH) != calendar.get(Calendar.MONTH) -> true
                                newCalendar.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH) -> true
                                else -> false
                            }
                            if (isChangeDay) {
                                newCalendar.set(Calendar.HOUR_OF_DAY, 0)
                                newCalendar.set(Calendar.MINUTE, 0)
                            } else {
                                newCalendar.set(
                                    Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
                                )
                                newCalendar.set(
                                    Calendar.MINUTE, calendar.get(Calendar.MINUTE)
                                )
                            }
                            MaterialTimePicker.Builder()
                                .setTitleText(
                                    DateFormat.getDateInstance(
                                        DateFormat.DEFAULT, Locale.getDefault()
                                    ).format(newCalendar.timeInMillis)
                                )
                                .setInputMode(
                                    when (item.inputMode) {
                                        FormTime.INPUT_MODE_TEXT -> 1
                                        else -> 0
                                    }
                                ).setTimeFormat(item.timeFormatMode)
                                .setHour(newCalendar.get(Calendar.HOUR_OF_DAY))
                                .setMinute(newCalendar.get(Calendar.MINUTE))
                                .build().apply {
                                    addOnPositiveButtonClickListener {
                                        newCalendar.set(Calendar.HOUR_OF_DAY, hour)
                                        newCalendar.set(Calendar.MINUTE, minute)
                                        calendar.timeInMillis = newCalendar.timeInMillis
                                        changeContentAndNotifyLinkage(
                                            holder, item, calendar.timeInMillis
                                        )
                                        anchor.text = item.getContentText(view.context, true)
                                    }
                                }.show(activity.supportFragmentManager, "FormTimePicker")
                        }
                    }.show(activity.supportFragmentManager, "FormDatePicker")
            }
        }
    }
}