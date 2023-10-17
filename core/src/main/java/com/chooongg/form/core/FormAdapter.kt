package com.chooongg.form.core

class FormAdapter(isEnabled: Boolean) : FormConcatAdapter() {

    var isEnabled: Boolean = isEnabled
        set(value) {
            field = value
//            updateForm()
        }
}