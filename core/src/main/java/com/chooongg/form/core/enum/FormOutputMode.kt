package com.chooongg.form.core.enum

/**
 * 输出模式
 */
enum class FormOutputMode {
    /**
     * 总是输出
     */
    ALWAYS,

    /**
     * 可见时输出
     */
    VISIBLE,

    /**
     * 可见和启用时输出
     */
    VISIBLE_AND_ENABLED,

    /**
     * 启用时输出 (忽略可见性)
     */
    ENABLED
}