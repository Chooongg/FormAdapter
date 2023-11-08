package com.chooongg.form.core.data

class FormPartData : FormGroupData(),BaseFormPartData {

    override var isEnablePart: Boolean = true

    override var partField: String? = null

    override var partName: Any? = null

}