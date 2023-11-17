package com.chooongg.form.core.item

import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.provider.InternalFormNoneProvider
import com.chooongg.form.core.typeset.BaseTypeset
import com.chooongg.form.core.typeset.NoneTypeset

class InternalFormNone internal constructor(spanIndex: Int, spanSize: Int) : BaseForm(null) {

    override val id: String = ""

    override var typeset: BaseTypeset? = NoneTypeset()

    init {
        this.spanIndex = spanIndex
        this.spanSize = spanSize
    }

    override fun getProvider(adapter: FormAdapter) = InternalFormNoneProvider::class
}