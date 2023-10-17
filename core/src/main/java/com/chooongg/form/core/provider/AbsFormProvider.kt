package com.chooongg.form.core.provider

abstract class AbsFormProvider {
    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}