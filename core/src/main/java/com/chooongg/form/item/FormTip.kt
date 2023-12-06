package com.chooongg.form.item

import android.view.Gravity
import androidx.annotation.StringRes
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormColorStateListBlock
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormTipProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.NoneTypeset
import kotlin.reflect.KClass

class FormTip : BaseForm {

    constructor(name: CharSequence?, field: String?) : super(name, field)
    constructor(@StringRes nameRes: Int?, field: String?) : super(nameRes, field)

    /**
     * 启用顶部填充
     */
    var enableTopPadding: Boolean = false

    /**
     * 启用底部填充
     */
    var enableBottomPadding: Boolean = false

    /**
     * 文字颜色
     */
    var color: FormColorStateListBlock? = null

    override var loneLine: Boolean = true

    override var typeset: BaseTypeset? = NoneTypeset()

    override var contentGravity: Int? = Gravity.CENTER

    override var multiColumnContentGravity: Int? = Gravity.CENTER

    override fun getProvider(adapter: FormAdapter): KClass<out BaseFormProvider> =
        FormTipProvider::class

}