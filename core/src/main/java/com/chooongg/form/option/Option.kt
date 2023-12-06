package com.chooongg.form.option

data class Option(
    private val _name: String?,
    private val _secondaryName: String?,
    private val _value: String?
) : IOption() {

    constructor(name: String?) : this(name, null, name)
    constructor(name: String?, value: String?) : this(name, null, value)

    override fun getOptionName() = _name
    override fun getOptionSecondaryName() = _secondaryName
    override fun getValue() = _value

}
