package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.InternalFormNoneProvider
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.EmptyTypeset

class InternalFormNone internal constructor(spanIndex: Int, spanSize: Int) : BaseForm(null, null) {

    override val id: String = ""

    override var typeset: BaseTypeset? = EmptyTypeset()

    init {
        this.spanIndex = spanIndex
        this.spanSize = spanSize
    }

    override fun getProvider(adapter: FormAdapter) = InternalFormNoneProvider::class
}