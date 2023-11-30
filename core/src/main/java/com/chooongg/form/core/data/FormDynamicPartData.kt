package com.chooongg.form.core.data

import androidx.annotation.IntRange
import com.chooongg.form.core.FormGroupCreateBlock
import com.chooongg.form.core.FormGroupNameFormatter
import com.chooongg.form.core.R
import com.chooongg.form.core.item.FormButton
import com.google.android.material.button.MaterialButton

class FormDynamicPartData : BaseFormPartData {

    override var isEnablePart: Boolean = true

    override var partField: String? = null

    override var partName: Any? = R.string.formDefaultGroupName

    private val _groups = mutableListOf<FormGroupData>()

    var isHasDeleteConfirm: Boolean = true

    internal var dynamicGroupCreateBlock: FormGroupCreateBlock? = null

    internal var dynamicGroupNameFormatter: FormGroupNameFormatter? = { _, name, index, count ->
        if (count == 1) name else "${name}${index + 1}"
    }

    @MaterialButton.IconGravity
    var addIconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    var addButtonStyle: FormButton.ButtonStyle = FormButton.ButtonStyle.TONAL

    var addIcon: Any? = R.drawable.ic_form_add

    @IntRange(from = 0)
    var dynamicPartMinGroupCount: Int = 1

    @IntRange(from = 1)
    var dynamicPartMaxGroupCount: Int = 1

    fun addGroup(block: FormGroupData.() -> Unit) {
        _groups.add(FormGroupData().apply(block))
    }

    fun getGroups(): MutableList<FormGroupData> = _groups

    fun dynamicGroupCreator(block: FormGroupCreateBlock?) {
        dynamicGroupCreateBlock = block
    }

    fun dynamicPartNameFormatter(block: FormGroupNameFormatter?) {
        dynamicGroupNameFormatter = block
    }
}