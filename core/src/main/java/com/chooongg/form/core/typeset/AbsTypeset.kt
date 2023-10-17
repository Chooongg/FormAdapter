package com.chooongg.form.core.typeset

abstract class AbsTypeset {

    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}