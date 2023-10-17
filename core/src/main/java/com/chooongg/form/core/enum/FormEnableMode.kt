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
    ONLY_EDIT,

    /**
     * 仅查看时可用
     */
    ONLY_SEE,

    /**
     * 总不可用
     */
    NEVER
}