package com.chooongg.form.core.data

interface BaseFormPartData {

    /**
     * 是否启用片段
     */
    var isEnablePart: Boolean

    /**
     * 片段识别字段
     */
    var partField: String?

    /**
     * 片段名称
     */
    var partName: Any?

}