package com.chooongg.form.inputMode

import android.text.InputFilter
import android.text.method.TransformationMethod

/**
 * 输入模式
 */
interface InputMode {
    /**
     * 获取输入类型
     */
    fun getInputType(): Int

    /**
     * 是具有长度筛选器
     */
    fun isHasLengthFilter(): Boolean = false

    /**
     * 是否需要密码切换
     */
    fun isNeedPasswordToggle(): Boolean = false

    /**
     * 获取转换方法实例
     */
    fun getTransformationMethod(): TransformationMethod? = null

    /**
     * 获取筛选器
     */
    fun getFilters(): ArrayList<InputFilter>
}