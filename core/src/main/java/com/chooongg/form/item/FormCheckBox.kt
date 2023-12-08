package com.chooongg.form.item

import androidx.annotation.IntRange
import com.chooongg.form.FormAdapter
import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.option.IOption
import com.chooongg.form.option.Option
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormCheckBoxProvider
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KClass

open class FormCheckBox(name: Any?, field: String?) : BaseOptionForm<IOption>(name, field) {

    /**
     * 最少选择数量
     */
    @IntRange(from = 0)
    var minSelectCount: Int = 0

    /**
     * 最多选择数量
     */
    @IntRange(from = 1)
    var maxSelectCount: Int = Int.MAX_VALUE

    override fun hasOpenOperation() = false

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormCheckBoxProvider::class

    override fun initValue(value: Any?) {
        if (value == null) return
        content = when (value) {
            is List<*> -> {
                if (value.isNotEmpty() && value[0] is IOption) return
                val temp = ArrayList<IOption>()
                value.forEach { temp.add(Option(null, it?.toString())) }
                value
            }

            is IOption -> listOf(value)
            is Number, is CharSequence -> listOf(Option(null, value.toString()))
            else -> null
        }
    }

    override fun dataVerification() {
        if (required) {
            if (content == null) {
                throw FormDataVerificationException(
                    this, FormDataVerificationException.ErrorType.Empty
                )
            } else if (content is List<*>) {
                val list = content as List<*>
                if (list.isEmpty()) {
                    throw FormDataVerificationException(
                        this, FormDataVerificationException.ErrorType.Empty
                    )
                } else if (minSelectCount > 0 && minSelectCount == maxSelectCount) {
                    throw FormDataVerificationException(
                        this, FormDataVerificationException.ErrorType.Count(minSelectCount)
                    )
                } else if (list.size < minSelectCount) {
                    throw FormDataVerificationException(
                        this, FormDataVerificationException.ErrorType.MinCount(minSelectCount)
                    )
                } else if (list.size > maxSelectCount) {
                    throw FormDataVerificationException(
                        this, FormDataVerificationException.ErrorType.MaxCount(maxSelectCount)
                    )
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun outputData(json: JSONObject) {
        if (field != null && content != null) {
            if (content is List<*>) {
                val value = content as List<*>
                if (value.isEmpty() || value[0] !is IOption) return
                val tempValue = value as List<IOption>
                json.put(field!!, JSONArray().apply {
                    tempValue.forEach {
                        put(it.getValue())
                    }
                })
            } else json.put(field!!, content)
        }
    }
}