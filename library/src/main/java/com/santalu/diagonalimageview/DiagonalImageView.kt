package com.santalu.diagonalimageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class DiagonalImageView : AppCompatImageView {

  private val clipPath by lazy { Path() }
  private val borderPath by lazy { Path() }
  private val borderPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
  private val clickRegion by lazy { Region() }
  private val clickRect by lazy { RectF() }

  var start = NONE
  var end = NONE
  var distance = 0f

  var borderEnabled = false
  var borderSize = 0f
  var borderColor = Color.BLACK

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    init(context, attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
      super(context, attrs, defStyleAttr) {
    init(context, attrs)
  }

  private fun init(context: Context, attrs: AttributeSet?) {
    attrs?.let {
      val a = context.obtainStyledAttributes(it, R.styleable.DiagonalImageView)

      with(a) {
        start = getInt(R.styleable.DiagonalImageView_di_start, NONE)
        end = getInt(R.styleable.DiagonalImageView_di_end, NONE)
        distance = getDimensionPixelSize(R.styleable.DiagonalImageView_di_distance, 0).toFloat()
        borderEnabled = getBoolean(R.styleable.DiagonalImageView_di_borderEnabled, false)
        borderSize = getDimensionPixelSize(R.styleable.DiagonalImageView_di_borderSize, 0).toFloat()
        borderColor = getColor(R.styleable.DiagonalImageView_di_borderColor, Color.BLACK)
        recycle()
      }

      borderPaint.apply {
        style = Style.STROKE
        color = borderColor
        strokeWidth = borderSize
      }

      // refer this https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
      val layerType =
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) LAYER_TYPE_HARDWARE else LAYER_TYPE_SOFTWARE
      setLayerType(layerType, null)
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent?): Boolean {
    event
      ?.takeUnless { clickRegion.isEmpty }
      ?.actionMasked
      ?.takeIf {
        it == MotionEvent.ACTION_DOWN &&
            !clickRegion.contains(event.x.toInt(), event.y.toInt())
      }
      ?.run {
        return false
      }
    return super.onTouchEvent(event)
  }

  override fun dispatchDraw(canvas: Canvas?) {
    canvas
      ?.takeUnless { clipPath.isEmpty }
      ?.run {
        clipPath(clipPath)
      }
    super.dispatchDraw(canvas)
  }

  override fun onDraw(canvas: Canvas?) {
    if (clipPath.isEmpty) {
      super.onDraw(canvas)
      return
    }
    canvas?.apply {
      val lastSave = save()
      clipPath(clipPath)
      super.onDraw(this)
      // draw border
      borderPath.takeUnless { it.isEmpty }
        ?.run {
          drawPath(this, borderPaint)
        }
      restoreToCount(lastSave)
    }
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    if (changed) {
      setClipPath()
    }
  }

  override fun invalidate() {
    super.invalidate()
    setClipPath()
  }

  private fun setClipPath() {
    val width = measuredWidth.toFloat()
    val height = measuredHeight.toFloat()

    if (width <= 0 || height <= 0) {
      return
    }

    clipPath.reset()
    borderPath.reset()

    when (start) {
      TOP -> {
        if (end == TOP || end == LEFT) {
          clipPath.apply {
            moveTo(0f, 0f)
            lineTo(width, distance)
            lineTo(width, height)
            lineTo(0f, height)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(0f, 0f)
              lineTo(width, distance)
            }
        } else {
          clipPath.apply {
            moveTo(0f, distance)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(0f, distance)
              lineTo(width, 0f)
            }
        }
      }
      BOTTOM -> {
        if (end == TOP || end == LEFT) {
          clipPath.apply {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height - distance)
            lineTo(0f, height)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(0f, height)
              lineTo(width, height - distance)
            }
        } else {
          clipPath.apply {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height - distance)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(0f, height - distance)
              lineTo(width, height)
            }
        }
      }
      LEFT -> {
        if (end == TOP || end == LEFT) {
          clipPath.apply {
            moveTo(distance, 0f)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(distance, 0f)
              lineTo(0f, height)
            }
        } else {
          clipPath.apply {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(distance, height)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(0f, 0f)
              lineTo(distance, height)
            }
        }
      }
      RIGHT -> {
        if (end == TOP || end == LEFT) {
          clipPath.apply {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width - distance, height)
            lineTo(0f, height)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(width, 0f)
              lineTo(width - distance, height)
            }
        } else {
          clipPath.apply {
            moveTo(0f, 0f)
            lineTo(width - distance, 0f)
            lineTo(width, height)
            lineTo(0f, height)
          }

          borderPath.takeIf { borderEnabled }
            ?.apply {
              moveTo(width - distance, 0f)
              lineTo(width, height)
            }
        }
      }
      else -> return
    }

    clipPath.close()
    borderPath.close()

    clipPath.computeBounds(clickRect, true)
    val region = Region(
      clickRect.left.toInt(),
      clickRect.top.toInt(),
      clickRect.right.toInt(),
      clickRect.bottom.toInt()
    )
    clickRegion.setPath(clipPath, region)
  }

  companion object {
    const val NONE = 0
    const val LEFT = 1
    const val TOP = 2
    const val RIGHT = 3
    const val BOTTOM = 4
  }
}