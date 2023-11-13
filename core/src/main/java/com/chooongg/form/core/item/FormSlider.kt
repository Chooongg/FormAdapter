package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.FloatRange
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormSliderFormatter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.provider.FormSliderProvider
import com.google.android.material.slider.LabelFormatter

class FormSlider : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    @IntDef(
        LabelFormatter.LABEL_FLOATING,
        LabelFormatter.LABEL_GONE,
        LabelFormatter.LABEL_VISIBLE,
        LabelFormatter.LABEL_WITHIN_BOUNDS
    )
    annotation class LabelBehavior

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

    override fun getProvider(adapter: FormAdapter) = FormSliderProvider::class

    fun formatter(block: FormSliderFormatter?) {
        formatter = block
    }

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is Float) return
        content = value.toString().toFloatOrNull()
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        val value = content as? Float ?: return FormUtils.getText(context, content)
        return formatter?.invoke(value) ?: value.toString()
    }
}