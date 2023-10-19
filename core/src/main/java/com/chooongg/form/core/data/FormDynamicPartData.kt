package com.chooongg.form.core.data

import androidx.annotation.IntRange
import com.chooongg.form.core.FormGroupCreateBlock
import com.chooongg.form.core.FormPartNameFormatter

class FormDynamicPartData {

    private val _groups = mutableListOf<FormGroupData>()

    internal var dynamicGroupCreateBlock: FormGroupCreateBlock? = null

    internal var dynamicPartNameFormatter: FormPartNameFormatter? = null

    @IntRange(from = 1)
    var dunamicPartMinGroupCount: Int = 1

    @IntRange(from = 1)
    var dynamicPartMaxGroupCount: Int = 1
}