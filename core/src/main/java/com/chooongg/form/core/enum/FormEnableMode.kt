package com.chooongg.form.core.enum

/**
 * 可用模式
 */
enum class FormEnableMode {
    /**
     * 总是可用
     */
    ALWAYS,

    /**
     * 仅编辑时可用
     */
    ENABLED,

    /**
     * 仅查看时可用
     */
    DISABLED,

    /**
     * 总不可用
     */
    NEVER
}