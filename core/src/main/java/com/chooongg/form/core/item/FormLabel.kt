package com.chooongg.form.core.item

import android.view.Gravity
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormColorStateListBlock
import com.chooongg.form.core.provider.BaseFormProvider
import com.chooongg.form.core.provider.FormLabelProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset
import kotlin.reflect.KClass

open class FormLabel : BaseForm {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    /**
     * 文字颜色 (如果不想使用默认色调, 请实现Block返回null)
     */
    var color: FormColorStateListBlock? = null

    override var loneLine: Boolean = true

    override var typeset: BaseTypeset? = NoneTypeset()

    override var gravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormLabelProvider::class

}