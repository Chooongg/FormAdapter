package com.chooongg.form.core.item

import android.view.Gravity
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormColorStateListBlock
import com.chooongg.form.core.provider.FormLabelProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset

class FormLabel : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    /**
     * 文字颜色
     */
    var color: FormColorStateListBlock? = null

    override var loneLine: Boolean = true

    override var typeset: BaseTypeset? = NoneTypeset()

    override var gravity: Int? = Gravity.NO_GRAVITY

    override fun getProvider(adapter: FormAdapter) = FormLabelProvider::class

}