package com.chooongg.form.provider

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.TooltipCompat
import androidx.core.app.ActivityOptionsCompat
import com.chooongg.form.FormUtils
import com.chooongg.form.FormViewHolder
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.core.R
import com.chooongg.form.enum.FormSelectorOpenMode
import com.chooongg.form.formTextAppearance
import com.chooongg.form.item.BaseForm
import com.chooongg.form.item.BaseOptionForm
import com.chooongg.form.item.FormSelector
import com.chooongg.form.option.FormArrayAdapter
import com.chooongg.form.option.FormSelectorPageActivity
import com.chooongg.form.option.IOption
import com.chooongg.form.option.Option
import com.chooongg.form.option.OptionLoadResult
import com.chooongg.form.style.BaseStyle
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.CoroutineScope

class FormSelectorProvider : BaseFormProvider() {

    override fun onCreateViewHolder(style: BaseStyle, parent: ViewGroup): View = MaterialButton(
        parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
    ).apply {
        id = R.id.formInternalContentView
        minimumHeight = 0
        minimumWidth = 0
        minHeight = 0
        minWidth = 0
        insetBottom = 0
        insetTop = 0
        setTextAppearance(formTextAppearance(this, R.attr.formTextAppearanceContent))
        iconTint = hintTextColors
        iconGravity = MaterialButton.ICON_GRAVITY_END
        iconSize = FormUtils.getFontHeight(this)
        iconPadding = context.resources.getDimensionPixelSize(R.dimen.formIconPadding)
        backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        setPaddingRelative(
            style.insideInfo.middleStart, style.insideInfo.middleTop,
            style.insideInfo.middleEnd, style.insideInfo.middleBottom
        )
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean
    ) {
        configOption(view, item, enabled)
        with(view as MaterialButton) {
            isEnabled = enabled
            text = item.getContentText(context, enabled)
            gravity = holder.typeset.obtainContentGravity(holder, item)
            if (enabled && item is FormSelector) {
                setOnClickListener { onClickButton(holder, this, item) }
            } else setOnClickListener(null)
        }
        loadOption(holder, item)
    }

    override fun onBindViewHolderOtherPayload(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm,
        enabled: Boolean,
        payload: Any
    ) {
        if (payload == BaseOptionForm.CHANGE_OPTION_PAYLOAD_FLAG) {
            configOption(view, item, enabled)
        }
    }


    private fun configOption(view: View, item: BaseForm, enabled: Boolean) {
        with(view as MaterialButton) {
            when (val result = (item as? BaseOptionForm<*>)?.optionLoadResult) {
                null -> {
                    TooltipCompat.setTooltipText(this, null)
                    hint = FormUtils.getText(context, item.hint)
                        ?: context.getString(R.string.fromDefaultHintNone)
                    icon = null
                }

                is OptionLoadResult.Wait, is OptionLoadResult.Success -> {
                    TooltipCompat.setTooltipText(this, null)
                    hint = if (enabled) {
                        FormUtils.getText(context, item.hint)
                            ?: context.getString(R.string.formDefaultHintSelect)
                    } else {
                        FormUtils.getText(context, item.disableHint)
                            ?: context.getString(R.string.fromDefaultHintNone)
                    }
                    if (enabled) setIconResource(R.drawable.ic_form_arrow_dropdown) else icon = null
                }

                is OptionLoadResult.Loading -> {
                    TooltipCompat.setTooltipText(this, null)
                    if (enabled) {
                        val fontHeight = FormUtils.getFontHeight(view)
                        hint = context.getString(R.string.formOptionsLoading)
                        val drawable = IndeterminateDrawable.createCircularDrawable(context,
                            CircularProgressIndicatorSpec(context, null).apply {
                                trackThickness = fontHeight / 10
                                indicatorSize = fontHeight / 2
                            })
                        icon = drawable
                        drawable.start()
                    } else icon = null
                }

                is OptionLoadResult.Error -> {
                    TooltipCompat.setTooltipText(this, result.e.message)
                    hint = context.getString(R.string.formOptionsError)
                    setIconResource(R.drawable.ic_form_error)
                }

                is OptionLoadResult.Empty -> {
                    TooltipCompat.setTooltipText(this, null)
                    hint = if (enabled) {
                        FormUtils.getText(context, item.hint)
                            ?: context.getString(R.string.formDefaultHintSelect)
                    } else {
                        FormUtils.getText(context, item.disableHint)
                            ?: context.getString(R.string.fromDefaultHintNone)
                    }
                    icon = null
                }
            }
        }
    }

    private fun onClickButton(holder: FormViewHolder, view: MaterialButton, item: FormSelector) {
        if (item.optionLoadResult is OptionLoadResult.Loading) {
            Snackbar.make(view, R.string.formOptionsLoading, Snackbar.LENGTH_SHORT).show()
        } else if (item.options.isNullOrEmpty()) {
            loadOption(holder, item)
        } else show(holder, view, item)
    }

    private fun show(holder: FormViewHolder, view: MaterialButton, item: FormSelector) {
        (holder.itemView.parent as ViewGroup).focusedChild?.clearFocus()
        FormUtils.hideIme(holder.itemView)
        when (item.openMode) {
            FormSelectorOpenMode.POPUPMENU -> showPopupMenu(holder, view, item)
            FormSelectorOpenMode.PAGE -> showPage(holder, view, item)
            FormSelectorOpenMode.AUTO -> {
                if ((item.options?.size ?: 0) > 30) showPage(holder, view, item)
                else showPopupMenu(holder, view, item)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showPopupMenu(holder: FormViewHolder, view: MaterialButton, item: FormSelector) {
        val popupWindow = ListPopupWindow(view.context)
        popupWindow.anchorView = view
        popupWindow.setDropDownGravity(Gravity.END)
        popupWindow.setOverlapAnchor(true)
        val fontHeight = FormUtils.getFontHeight(view)
        val iconPadding = view.resources.getDimensionPixelSize(R.dimen.formIconPadding)
        val adapter = FormArrayAdapter<IOption>(
            Boundary(
                view.paddingStart, view.paddingTop,
                view.paddingEnd + iconPadding + fontHeight, view.paddingBottom
            )
        )
        adapter.hint = view.hint
        adapter.current = item.content
        val options = ArrayList<IOption>()
        if (!item.required) options.add(Option(null))
        item.options?.let { if (it.isNotEmpty()) options.addAll(it) }
        val gravity = holder.typeset.obtainContentGravity(holder, item)
        adapter.setNewData(options, gravity)
        popupWindow.setAdapter(adapter)
        popupWindow.setOnItemClickListener { _, _, position, _ ->
            val option = options[position]
            changeContentAndNotifyLinkage(holder, item, option)
            view.text = item.getContentText(view.context, true)
            popupWindow.dismiss()
        }
        popupWindow.show()
    }

    private fun showPage(holder: FormViewHolder, view: MaterialButton, item: FormSelector) {
        val activity = FormUtils.getActivity(view.context)
        val intent = Intent(view.context, FormSelectorPageActivity::class.java)
        intent.putExtra("name", FormUtils.getText(view.context, item.name))
        FormSelectorPageActivity.Controller.formSelector = item
        FormSelectorPageActivity.Controller.resultBlock = {
            changeContentAndNotifyLinkage(holder, item, it)
            view.text = item.getContentText(view.context, true)
        }
        if (activity != null) {
            activity.setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            activity.startActivity(
                intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, view, "FormSelectorPage"
                ).toBundle()
            )
        } else view.context.startActivity(intent)
    }

    override fun onViewRecycled(holder: FormViewHolder, view: View) {
        holder.job?.cancel()
        with(holder.view as? MaterialButton ?: return) {
            if (icon is IndeterminateDrawable<*>) {
                (icon as IndeterminateDrawable<*>).stop()
            }
        }
    }
}