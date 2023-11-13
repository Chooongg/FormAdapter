package com.chooongg.form.core.view

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Region
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.chooongg.form.core.boundary.Boundary
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class OutlinedCutoutDrawable(
    shapeAppearanceModel: ShapeAppearanceModel,
    private val boundary: Boundary,
    private val isRtl: Boolean
) : MaterialShapeDrawable(shapeAppearanceModel) {

    init {
        paintStyle = Paint.Style.STROKE
    }

    @Suppress("DEPRECATION")
    @SuppressLint("RestrictedApi")
    override fun drawStrokeShape(canvas: Canvas) {
        val topLeftSize = topLeftCornerResolvedSize
        val topRightSize = topRightCornerResolvedSize
        val bottomLeftSize = bottomLeftCornerResolvedSize
        val bottomRightSize = bottomRightCornerResolvedSize
        if (topLeftSize != 0f && topRightSize != 0f && bottomLeftSize != 0f && bottomRightSize != 0f) {
            super.drawStrokeShape(canvas)
        } else {
            val bound = boundsAsRectF
            // Saves the canvas so we can restore the clip after drawing the stroke.
            canvas.save()
            // angle
            if (isRtl) {
                if (boundary.start <= 0 && boundary.top <= 0) {
                    val f = RectF(
                        bound.right - strokeWidth,
                        0f,
                        bound.right,
                        strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.top <= 0 && boundary.end <= 0) {
                    val f = RectF(
                        0f,
                        0f,
                        strokeWidth,
                        strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.end <= 0 && boundary.bottom <= 0) {
                    val f = RectF(
                        0f,
                        bound.bottom - strokeWidth,
                        strokeWidth,
                        bound.bottom
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.bottom <= 0 && boundary.start <= 0) {
                    val f = RectF(
                        bound.right - strokeWidth,
                        bound.bottom - strokeWidth,
                        bound.right,
                        bound.bottom
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
            } else {
                if (boundary.start <= 0 && boundary.top <= 0) {
                    val f = RectF(
                        0f,
                        0f,
                        strokeWidth,
                        strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.top <= 0 && boundary.end <= 0) {
                    val f = RectF(
                        bound.right - strokeWidth,
                        0f,
                        bound.right,
                        strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.end <= 0 && boundary.bottom <= 0) {
                    val f = RectF(
                        bound.right - strokeWidth,
                        bound.bottom - strokeWidth,
                        bound.right,
                        bound.bottom
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.bottom <= 0 && boundary.start <= 0) {
                    val f = RectF(
                        0f,
                        bound.bottom - strokeWidth,
                        strokeWidth,
                        bound.bottom
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
            }
            // Line
            if (boundary.top <= 0) {
                val f = RectF(
                    strokeWidth,
                    0f,
                    bound.right - strokeWidth,
                    strokeWidth
                )
                if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                else canvas.clipRect(f, Region.Op.DIFFERENCE)
            }
            if (boundary.bottom <= 0) {
                val f = RectF(
                    strokeWidth,
                    bound.bottom - strokeWidth,
                    bound.right - strokeWidth,
                    bound.bottom
                )
                if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                else canvas.clipRect(f, Region.Op.DIFFERENCE)
            }
            if (isRtl) {
                if (boundary.start <= 0) {
                    val f = RectF(
                        bound.right - strokeWidth,
                        strokeWidth,
                        bound.right,
                        bound.bottom - strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.end <= 0) {
                    val f = RectF(
                        0f,
                        strokeWidth,
                        strokeWidth,
                        bound.bottom - strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
            } else {
                if (boundary.start <= 0) {
                    val f = RectF(
                        0f,
                        strokeWidth,
                        strokeWidth,
                        bound.bottom - strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
                if (boundary.end <= 0) {
                    val f = RectF(
                        bound.right - strokeWidth,
                        strokeWidth,
                        bound.right,
                        bound.bottom - strokeWidth
                    )
                    if (VERSION.SDK_INT >= VERSION_CODES.O) canvas.clipOutRect(f)
                    else canvas.clipRect(f, Region.Op.DIFFERENCE)
                }
            }
            super.drawStrokeShape(canvas)
            canvas.restore()
        }
    }

}