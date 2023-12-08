package com.chooongg.form.item

import android.view.Gravity
import androidx.annotation.GravityInt
import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormInputFilledProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.NoneTypeset
import kotlin.reflect.KClass

open class FormInputFilled(name: Any?, field: String?) : FormInput(name, field) {

    override var typeset: BaseTypeset? = NoneTypeset()

    @GravityInt
    override var contentGravity: Int? = Gravity.NO_GRAVITY

    @GravityInt
    override var multiColumnContentGravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormInputFilledProvider::class

}