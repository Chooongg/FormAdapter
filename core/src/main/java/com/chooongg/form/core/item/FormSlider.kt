package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormSliderFormatter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.provider.FormSliderProvider

class FormSlider : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    var valueFrom: Float = 0f

    var valueTo: Float = 100f

    @FloatRange(from = 0.0)
    var stepSize: Float = 0f

    var showTick: Boolean = true

    var showActiveTick: Boolean = true

    internal var formatter: FormSliderFormatter? = null

    override fun getProvider(adapter: FormAdapter) = FormSliderProvider::class

    fun formatter(block: FormSliderFormatter?) {
        formatter = block
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        val value = content as? Float ?: return FormUtils.getText(context, content)
        return formatter?.invoke(value) ?: value.toString()
    }
}