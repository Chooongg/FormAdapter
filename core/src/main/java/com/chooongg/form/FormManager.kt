package com.chooongg.form

import android.content.res.Resources
import android.view.Gravity
import androidx.annotation.GravityInt
import androidx.annotation.IntRange
import androidx.annotation.Px
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.dataActuator.FormDataActuator
import com.chooongg.form.error.ErrorFormatter
import com.chooongg.form.error.ErrorOutputTool
import com.chooongg.form.error.NormalErrorFormatter
import com.chooongg.form.error.NormalErrorOutputTool
import com.chooongg.form.format.BaseNameFormatter
import com.chooongg.form.format.NormalNameFormatter
import com.chooongg.form.groupNameProvider.BaseGroupNameProvider
import com.chooongg.form.groupNameProvider.DefaultGroupNameProvider
import com.chooongg.form.iconProvider.BaseIconProvider
import com.chooongg.form.iconProvider.NormalIconProvider
import com.chooongg.form.inputMode.InputMode
import com.chooongg.form.inputMode.InputModeText
import com.chooongg.form.item.BaseForm
import com.chooongg.form.typeset.BaseTypeset
import com.chooongg.form.typeset.HorizontalTypeset

object FormManager {

    private val formItemDataActuators = HashSet<FormDataActuator>()

    object Default {

        @Px
        var maxItemWidth: Int = (300 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

        @IntRange(from = 1)
        var emsSize: Int = 5

        @GravityInt
        var contentGravity: Int = Gravity.END

        @GravityInt
        var multiColumnContentGravity: Int = Gravity.NO_GRAVITY

        @Boundary.BoundaryInt
        var horizontalMiddleBoundary: Int = Boundary.MIDDLE

        var nameFormatter: BaseNameFormatter = NormalNameFormatter()

        var typeset: BaseTypeset = HorizontalTypeset()

        var groupNameProvider: BaseGroupNameProvider = DefaultGroupNameProvider()

        var iconProvider: BaseIconProvider = NormalIconProvider()

        var dataVerificationNotifyAllError: Boolean = true

        var errorFormatter: ErrorFormatter = NormalErrorFormatter()

        var errorOutputTool: ErrorOutputTool = NormalErrorOutputTool()

        var inputMode: InputMode = InputModeText()
    }

    fun defaultConfig(block: Default.() -> Unit) = apply {
        block(Default)
    }

    fun addItemDataActuator(actuator: FormDataActuator) {
        formItemDataActuators.add(actuator)
    }

    fun clearAddItemDataActuator() {
        formItemDataActuators.clear()
    }

    internal fun findItemDataActuator(clazz: Class<out BaseForm>): FormDataActuator? {
        return formItemDataActuators.find { it.getTargetClass() == clazz }
    }
}