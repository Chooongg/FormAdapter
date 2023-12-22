package com.chooongg.form.item

import com.chooongg.form.FormAdapter
import com.chooongg.form.provider.InternalFormGroupChildNameProvider

class InternalFormGroupChildName internal constructor() : InternalFormGroupName() {

    override fun getProvider(adapter: FormAdapter) = InternalFormGroupChildNameProvider::class
}