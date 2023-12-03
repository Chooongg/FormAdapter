package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormSliderFormatter
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormSliderProvider
import kotlin.reflect.KClass

class FormSlider : BaseForm {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    /**
     * 起始值
     */
    var valueFrom: Float = 0f

    /**
     * 结束值
     */
    var valueTo: Float = 100f

    /**
     * 步长
     */
    @FloatRange(from = 0.0)
    var stepSize: Float = 0f

    /**
     * 显示活动刻度
     */
    var showActiveTick: Boolean = true

    /**
     * 显示非活动刻度
     */
    var showInactiveTick: Boolean = true

    /**
     * 显示刻度
     */
    var showTick: Boolean
        get() = showActiveTick && showInactiveTick
        set(value) {
            showActiveTick = value
            showInactiveTick = value
        }

    internal var formatter: FormSliderFormatter? = null

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormSliderProvider::class

    /**
     * 格式化工具
     */
    fun formatter(block: FormSliderFormatter?) {
        formatter = block
    }

    override fun initContentValue(value: Any?) {
        if (value is Float) return
        if (value == null) {
            content = valueFrom
            return
        }
        content = value.toString().toFloatOrNull()
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        val value = content as? Float ?: return FormUtils.getText(context, content)
        return formatter?.invoke(value) ?: value.toString()
    }
}