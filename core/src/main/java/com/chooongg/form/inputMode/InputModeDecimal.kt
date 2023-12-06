package com.chooongg.form.inputMode

import android.text.InputFilter
import android.text.InputType
import android.text.Spanned

class InputModeDecimal(
    private val min: Double? = null,
    private val max: Double? = null
) : InputMode {
    override fun getInputType(): Int =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

    override fun isHasLengthFilter(): Boolean = true
    override fun getFilters(): ArrayList<InputFilter> = arrayListOf(DecimalInputFilter(min, max))

    private class DecimalInputFilter(private val min: Double?, private val max: Double?) :
        InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            return null
        }
    }
}