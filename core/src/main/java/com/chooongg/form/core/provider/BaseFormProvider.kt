package com.chooongg.form.core.provider

/**
 * 子类必须实现空参构造方法
 */
abstract class BaseFormProvider {

    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}