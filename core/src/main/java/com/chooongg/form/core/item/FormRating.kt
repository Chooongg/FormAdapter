package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.FormColorStateListBlock
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.R
import com.chooongg.form.core.provider.FormRatingProvider

class FormRating : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    /**
     * 星星数量
     */
    @androidx.annotation.IntRange(from = 1)
    var numStars: Int = 5

    /**
     * 步长
     */
    @FloatRange(from = 0.0)
    var stepSize: Float = 1f

    /**
     * 最小值
     */
    var needToChoose: Boolean = true

    /**
     * 色调
     */
    var tint: FormColorStateListBlock? = null

    override fun getProvider(adapter: FormAdapter) = FormRatingProvider::class

    override fun initContentValue(value: Any?) {
        if (value == null) return
        if (value is Float) return
        content = value.toString().toFloatOrNull()
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        val rating = content as? Float ?: return FormUtils.getText(context, content)
        return context.getString(R.string.formRatingFormat, rating)
    }
}