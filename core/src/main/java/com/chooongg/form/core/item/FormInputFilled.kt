package com.chooongg.form.core.item

import android.view.Gravity
import androidx.annotation.GravityInt
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormInputFilledProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset

class FormInputFilled : FormInput {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    override var typeset: BaseTypeset? = NoneTypeset()

    @GravityInt
    override var contentGravity: Int? = Gravity.NO_GRAVITY

    @GravityInt
    override var multiColumnContentGravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter) = FormInputFilledProvider::class

}