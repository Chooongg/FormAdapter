package com.chooongg.form.core.style

abstract class BaseStyle {

    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}