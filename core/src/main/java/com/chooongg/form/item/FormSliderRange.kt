package com.chooongg.form.item

import android.content.Context
import androidx.annotation.FloatRange
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormSliderFormatter
import com.chooongg.form.FormUtils
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormSliderRangeProvider
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KClass

class FormSliderRange(name: Any?, field: String?) : BaseForm(name, field) {

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
        FormSliderRangeProvider::class

    /**
     * 格式化工具
     */
    fun formatter(block: FormSliderFormatter?) {
        formatter = block
    }

    override fun initValue(value: Any?) {
        if (value is List<*>) {
            if (value.size < 2) {
                content = null
            } else {
                if (value.size > 2) content = MutableList(2) { value[it] }
                if (value[0] !is Float || value[1] !is Float) {
                    val from = value[0].toString().toFloatOrNull()
                    val to = value[1].toString().toFloatOrNull()
                    content = if (from == null || to == null) {
                        null
                    } else mutableListOf<Float>().apply {
                        add(from)
                        add(to)
                    }
                }
            }
        }
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

    override fun outputData(json: JSONObject) {
        if (field != null && content != null) {
            if (content is List<*>) {
                val jsonArray = JSONArray()
                (content as List<*>).forEach {
                    jsonArray.put(it)
                }
                json.put(field!!, jsonArray)
            } else json.put(field!!, content)
        }
    }
}