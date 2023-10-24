package com.chooongg.form.core.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.FormTextProvider

class FormText : BaseForm {

    constructor(name: CharSequence?) : super(name)
    constructor(@StringRes nameRes: Int?) : super(nameRes)

    override fun getProvider(adapter: FormAdapter) = FormTextProvider::class

    override fun getContentString(context: Context): CharSequence? {
        return when (content) {
            is Int -> try {
                context.getString(content as Int)
            } catch (e: Exception) {
                content?.toString()
            }

            else -> content?.toString()
        }
    }
}