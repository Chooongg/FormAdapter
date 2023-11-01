package com.chooongg.form.core.option

data class Option(
    val _name: String?,
    val _secondaryName: String?,
    val _value: String?
) : IOption {

    constructor(name: String?) : this(name, null, name)
    constructor(name: String?, value: String?) : this(name, value, null)

    override fun getOptionName() = _name
    override fun getOptionSecondaryName() = _secondaryName
    override fun getValue() = _value

}
