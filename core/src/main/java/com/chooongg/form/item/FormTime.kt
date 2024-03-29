package com.chooongg.form.item

import android.content.Context
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import androidx.annotation.IntDef
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormUtils
import com.chooongg.form.enum.FormTimeMode
import com.chooongg.form.provider.FormTimeProvider
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DayViewDecorator
import com.google.android.material.timepicker.TimeFormat
import org.json.JSONObject
import java.util.Locale

class FormTime(name: Any?, field: String?) : BaseForm(name, field) {

    companion object {
        const val INPUT_MODE_SELECT = 0
        const val INPUT_MODE_TEXT = 1
    }

    @IntDef(INPUT_MODE_SELECT, INPUT_MODE_TEXT)
    annotation class InputMode

    override fun getProvider(adapter: FormAdapter) = FormTimeProvider::class

    /**
     * 时间模式
     */
    var timeMode: FormTimeMode = FormTimeMode.DATE_TIME

    /**
     * 输入模式
     */
    @InputMode
    var inputMode: Int = INPUT_MODE_SELECT

    /**
     * 展示的格式化模型
     */
    var showFormatPattern: String? = null
        set(value) {
            field = value
            showFormat = if (value != null) SimpleDateFormat(value, Locale.getDefault()) else null
        }

    /**
     * 展示的格式化工具
     */
    var showFormat: SimpleDateFormat? = null

    /**
     * 日历限制
     */
    var calendarConstraints: CalendarConstraints? = null

    /**
     * 日历视图装饰
     */
    var dayViewDecorator: DayViewDecorator? = null

    /**
     * 时间格式类型
     */
    @TimeFormat
    var timeFormatMode: Int = TimeFormat.CLOCK_24H

    /**
     * 数据格式化模型
     */
    var formatPattern: String? = null
        set(value) {
            field = value
            if (value != null) {
                inputFormat = SimpleDateFormat(value, Locale.getDefault())
                outputFormat = SimpleDateFormat(value, Locale.getDefault())
            } else {
                inputFormat = null
                outputFormat = null
            }
        }

    /**
     * 输入格式化工具
     */
    var inputFormat: SimpleDateFormat? = null

    /**
     * 输出格式化工具
     */
    var outputFormat: SimpleDateFormat? = null

    override fun initValue(value: Any?) {
        if (value == null) return
        content = if (inputFormat != null) {
            try {
                inputFormat!!.parse(value.toString())?.time
            } catch (e: Exception) {
                null
            }
        } else if (value is Long) {
            value
        } else value.toString().toLongOrNull()
    }

    override fun outputData(json: JSONObject) {
        if (field != null && content != null) {
            if (outputFormat != null) {
                json.put(field!!, outputFormat!!.format(content!!))
            } else {
                json.put(field!!, content)
            }
        }
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        val millis = content as? Long ?: return FormUtils.getText(context, content)
        if (showFormat != null) {
            return try {
                showFormat!!.format(millis)
            } catch (e: Exception) {
                FormUtils.getText(context, content)
            }
        }
        val formatter = when (timeMode) {
            FormTimeMode.TIME -> DateFormat.getTimeInstance(
                DateFormat.SHORT, Locale.getDefault()
            )

            FormTimeMode.DATE -> DateFormat.getDateInstance(
                DateFormat.DEFAULT, Locale.getDefault()
            )

            FormTimeMode.DATE_TIME -> DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.SHORT, Locale.getDefault()
            )
        }
        if (formatter is SimpleDateFormat) {
            formatter.applyPattern(
                formatter.toPattern()
                    .replace('h', 'H')
                    .replace('k', 'K')
                    .replace("a", "")
            )
        }
        return formatter.format(millis)
    }
}