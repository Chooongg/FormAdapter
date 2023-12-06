package com.chooongg.form.typeset

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.chooongg.form.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.enum.FormEmsMode
import com.chooongg.form.item.BaseForm
import com.chooongg.form.menu.FormMenuView
import com.chooongg.form.part.BaseFormPartAdapter
import com.chooongg.form.style.BaseStyle

class NoneTypeset : BaseTypeset() {

    override var emsMode = FormEmsMode.NONE
    override var multiColumnEmsMode: FormEmsMode = FormEmsMode.NONE

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): ViewGroup {
        return FrameLayout(parent.context).also {
            it.addView(FormMenuView(it.context, style).apply {
                id = R.id.formInternalMenuView
            }, FrameLayout.LayoutParams(-2, -2).apply {
                gravity = Gravity.CENTER_VERTICAL or Gravity.END
            })
        }
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup,
        item: BaseForm,
        enabled: Boolean
    ) {
        layout.findViewById<FormMenuView>(R.id.formInternalMenuView).apply {
            if (item.isRealMenuVisible(enabled) && item.menu != null) {
                visibility = View.VISIBLE
                inflateMenu(
                    item.menu!!,
                    item.isRealMenuEnable(enabled),
                    item.getMenuCreateOptionCallback(),
                    {
                        val isIntercept = item.getMenuClickListener()?.invoke(context, it, item)
                        if (isIntercept != true) {
                            (holder.bindingAdapter as? BaseFormPartAdapter)?.formAdapter
                                ?.getOnMenuClickListener()?.invoke(context, it, item)
                        }
                        true
                    },
                    item.menuShowTitle
                )
            } else {
                visibility = View.GONE
                clearMenu()
            }
        }
    }

    override fun addView(style: BaseStyle, parentView: ViewGroup, child: View) {
        parentView.addView(child, 0)
    }
}