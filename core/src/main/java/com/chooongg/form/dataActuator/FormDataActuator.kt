package com.chooongg.form.dataActuator

import com.chooongg.form.error.FormDataVerificationException
import com.chooongg.form.item.BaseForm
import org.json.JSONObject

/**
 * 数据执行器, 用来覆盖原有的数据验证和输出
 */
interface FormDataActuator {
    /**
     * 目标类
     */
    fun getTargetClass(): Class<out BaseForm>

    /**
     * 是否覆盖数据验证
     */
    fun coverDataVerification(): Boolean = false

    /**
     * 数据验证
     */
    @Throws(FormDataVerificationException::class)
    fun dataVerification(item: BaseForm) = Unit

    /**
     * 输出
     */
    fun output(item: BaseForm, json: JSONObject)

    /**
     * 是否覆盖输出扩展数据
     */
    fun coverOutputExtensionData(): Boolean = false

    /**
     * 输出扩展数据
     */
    fun outputExtensionData(item: BaseForm, extension: HashMap<String, Any?>?, json: JSONObject) =
        Unit
}