package com.chooongg.form.core.data

abstract class BaseFormPartData {

    /**
     * 是否启用片段
     */
    var isEnablePart: Boolean = true

    /**
     * 片段识别字段
     */
    var partField: String? = null

    /**
     * 片段名称
     */
    var partName: Any? = null

}