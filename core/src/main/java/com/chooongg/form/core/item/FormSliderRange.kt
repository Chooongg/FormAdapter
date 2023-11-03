package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormSliderFormatter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.provider.FormSliderRangeProvider

class FormSliderRange : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    var valueFrom: Float = 0f

    var valueTo: Float = 100f

    @FloatRange(from = 0.0)
    var stepSize: Float = 0f

    var showActiveTick: Boolean = true

    var showInactiveTick: Boolean = true

    var showTick: Boolean
        get() = showActiveTick && showInactiveTick
        set(value) {
            showActiveTick = value
            showInactiveTick = value
        }

    internal var formatter: FormSliderFormatter? = null

    override fun getProvider(adapter: FormAdapter) = FormSliderRangeProvider::class

    fun formatter(block: FormSliderFormatter?) {
        formatter = block
    }

    @Suppress("UNCHECKED_CAST")
    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        return try {
            if (content is List<*>) {
                val temp = content as List<Float>
                if (temp.isEmpty()) null
                else buildString {
                    temp.forEachIndexed { index, float ->
                        if (index != 0) append(" - ")
                        append(formatter?.invoke(float) ?: float)
                    }
                }
            } else FormUtils.getText(context, content)
        } catch (e: Exception) {
            FormUtils.getText(context, content)
        }
    }
}