package com.chooongg.form.core.data

abstract class BaseFormPartData {

    /**
     * 是否启用片段
     */
    var isEnablePart: Boolean = true

    /**
     *
     */
    var partField: String? = null

    var partName: CharSequence? = null

}