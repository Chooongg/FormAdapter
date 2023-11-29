package com.chooongg.form.core.item

import android.content.Context
import android.icu.text.DateFormat
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.enum.FormTimeMode
import com.chooongg.form.core.provider.FormTextProvider
import com.chooongg.form.core.provider.FormTimeProvider
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DayViewDecorator
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Locale

class FormTime : BaseForm {

    companion object {
        const val INPUT_MODE_SELECT = 0
        const val INPUT_MODE_TEXT = 1
    }

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    @IntDef(INPUT_MODE_SELECT, INPUT_MODE_TEXT)
    annotation class InputMode

    override fun getProvider(adapter: FormAdapter) =
        if (adapter.isEnabled) FormTimeProvider::class else FormTextProvider::class

    var timeMode: FormTimeMode = FormTimeMode.DATE_TIME

    @InputMode
    var inputMode: Int = INPUT_MODE_SELECT

    var showFormatPattern: String? = null

    var calendarConstraints: CalendarConstraints? = null

    var dayViewDecorator: DayViewDecorator? = null

    @TimeFormat
    var timeFormatMode: Int = TimeFormat.CLOCK_24H

    var inputFormatPattern: String? = null

    var outputFormatPattern: String? = null

    override fun initContentValue(value: Any?) {
        if (value == null) return
        content = if (inputFormatPattern != null) {
            try {
                SimpleDateFormat(showFormatPattern!!, Locale.getDefault())
                    .parse(value.toString())?.time
            } catch (e: Exception) {
                null
            }
        } else if (value is Long) {
            value
        } else value.toString().toLongOrNull()
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        val millis = content as? Long ?: return FormUtils.getText(context, content)
        if (showFormatPattern != null) {
            return try {
                SimpleDateFormat(showFormatPattern!!, Locale.getDefault()).format(millis)
            } catch (e: Exception) {
                FormUtils.getText(context, content)
            }
        }
        return when (timeMode) {
            FormTimeMode.TIME -> DateFormat.getTimeInstance(
                DateFormat.SHORT, Locale.getDefault()
            )

            FormTimeMode.DATE -> DateFormat.getDateInstance(
                DateFormat.DEFAULT, Locale.getDefault()
            )

            FormTimeMode.DATE_TIME -> DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.SHORT, Locale.getDefault()
            )
        }.format(millis)
    }
}