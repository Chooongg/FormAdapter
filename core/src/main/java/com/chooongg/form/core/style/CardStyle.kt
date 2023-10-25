package com.chooongg.form.core.style

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.R
import com.chooongg.form.core.item.BaseForm
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.AbsoluteCornerSize
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class CardStyle : BaseStyle {

    constructor() : super()
    constructor(block: CardStyle.() -> Unit) : super() {
        block(this)
    }

    var elevation: Float? = null

    var shapeAppearanceResId: Int? = null

    private lateinit var shapeAppearanceModel: ShapeAppearanceModel

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? {
        if (!this::shapeAppearanceModel.isInitialized) {
            shapeAppearanceModel = ShapeAppearanceModel.builder(
                parent.context,
                shapeAppearanceResId
                    ?: getDefaultShapeAppearanceResId(parent.context),
                0
            ).build()
        }
        return null
    }

    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup?, item: BaseForm) {
        val context = holder.itemView.context
        holder.itemView.clipToOutline = true
        val shape = ShapeAppearanceModel.builder().apply {
            if (holder.itemView.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                setTopLeftCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.topLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setTopRightCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.topRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomLeftCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.bottomLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomRightCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.bottomRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
            } else {
                setTopLeftCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.topRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setTopRightCornerSize(
                    if (item.marginBoundary.top != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.topLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomLeftCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.start != 0) {
                        shapeAppearanceModel.bottomRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomRightCornerSize(
                    if (item.marginBoundary.bottom != 0 && item.marginBoundary.end != 0) {
                        shapeAppearanceModel.bottomLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
            }
        }.build()
        val shapeDrawable = if (holder.itemView.background is MaterialShapeDrawable) {
            (holder.itemView.background as MaterialShapeDrawable).apply {
                shapeAppearanceModel = shape
            }
        } else MaterialShapeDrawable(shape)
        shapeDrawable.fillColor = ColorStateList.valueOf(
            MaterialColors.getColor(
                context, com.google.android.material.R.attr.colorSurfaceContainer, Color.TRANSPARENT
            ),
        )
        holder.itemView.background = shapeDrawable
        holder.itemView.elevation =
            elevation ?: context.resources.getDimension(R.dimen.formMarginMiddleEnd)
    }

    override fun addView(parentView: ViewGroup, child: View) {
        parentView.addView(child)
    }

    private fun getDefaultShapeAppearanceResId(context: Context): Int {
        val themeShape = com.google.android.material.R.style.ShapeAppearance_Material3_Corner_Medium
        return context.obtainStyledAttributes(intArrayOf(R.attr.formShapeAppearanceCorner))
            .use { it.getResourceId(0, themeShape) }
    }
}