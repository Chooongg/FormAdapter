package com.chooongg.form.item

import android.view.Gravity
import androidx.annotation.StringRes
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormColorStateListBlock
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormLabelProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.NoneTypeset
import kotlin.reflect.KClass

open class FormLabel : BaseForm {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    /**
     * 文字颜色
     */
    var color: FormColorStateListBlock? = null

    override var loneLine: Boolean = true

    override var typeset: BaseTypeset? = NoneTypeset()

    override var gravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormLabelProvider::class

}