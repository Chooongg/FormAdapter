package com.chooongg.form.core.typeset

abstract class BaseTypeset {

    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}