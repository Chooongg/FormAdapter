package com.chooongg.form.core.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.VectorDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

class SvgRatingBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.ratingBarStyle
) :
    AppCompatRatingBar(context, attrs, defStyleAttr) {

    private var sampleTile: Bitmap? = null
    private val roundedCorners = floatArrayOf(5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f)
    private val roundRectShape = RoundRectShape(roundedCorners, null, null)

    init {
        progressDrawable = createTile(progressDrawable, false) as LayerDrawable
    }

    /**
     * Converts a drawable to a tiled version of itself. It will recursively
     * traverse layer and state list drawables.
     */
    private fun createTile(drawable: Drawable, clip: Boolean): Drawable =
        when (drawable) {
            is DrawableWrapper -> {
                @SuppressLint("RestrictedApi")
                var inner = drawable.drawable
                if (inner != null) {
                    inner = createTile(inner, clip)
                    @SuppressLint("RestrictedApi")
                    drawable.drawable = inner
                }
                drawable
            }

            is LayerDrawable -> {
                val n = drawable.numberOfLayers
                val outDrawables = arrayOfNulls<Drawable>(n)
                for (i in 0 until n) {
                    val id = drawable.getId(i)
                    outDrawables[i] = createTile(
                        drawable.getDrawable(i),
                        id == android.R.id.progress || id == android.R.id.secondaryProgress
                    )
                }
                val newBg = LayerDrawable(outDrawables)
                for (i in 0 until n) {
                    newBg.setId(i, drawable.getId(i))
                }
                newBg
            }

            is BitmapDrawable -> {
                val tileBitmap = drawable.bitmap
                if (sampleTile == null) {
                    sampleTile = tileBitmap
                }
                val bitmapShader = BitmapShader(
                    tileBitmap, Shader.TileMode.REPEAT,
                    Shader.TileMode.CLAMP
                )
                val shapeDrawable = ShapeDrawable(roundRectShape).apply {
                    paint.shader = bitmapShader
                    paint.colorFilter = drawable.paint.colorFilter
                }
                if (clip) ClipDrawable(shapeDrawable, Gravity.START, ClipDrawable.HORIZONTAL)
                else shapeDrawable
            }

            is VectorDrawable -> {
                createTile(getBitmapDrawableFromVectorDrawable(drawable), clip)
            }

            is VectorDrawableCompat -> {
                // Pre-Lollipop support.
                createTile(getBitmapDrawableFromVectorDrawable(drawable), clip)
            }

            else -> drawable
        }

    private fun getBitmapDrawableFromVectorDrawable(drawable: Drawable): BitmapDrawable {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (sampleTile != null) {
            val width = sampleTile!!.width * numStars
            setMeasuredDimension(
                resolveSizeAndState(width, widthMeasureSpec, 0),
                measuredHeight
            )
        }
    }
}