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
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class DiagonalImageView : AppCompatImageView {

  private val clipPath by lazy { Path() }
  private val borderPath by lazy { Path() }
  private val borderPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
  private val clickRegion by lazy { Region() }
  private val clickRect by lazy { RectF() }

  var position = NONE
  var direction = NONE
  var overlap = 0f

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
      position = a.getInt(R.styleable.DiagonalImageView_di_position, NONE)
      direction = a.getInt(R.styleable.DiagonalImageView_di_direction, NONE)
      overlap = a.getDimensionPixelSize(R.styleable.DiagonalImageView_di_overlap, 0).toFloat()
      borderEnabled = a.getBoolean(R.styleable.DiagonalImageView_di_borderEnabled, false)
      borderSize = a.getDimensionPixelSize(R.styleable.DiagonalImageView_di_borderSize, 0).toFloat()
      borderColor = a.getColor(R.styleable.DiagonalImageView_di_borderColor, Color.BLACK)

      borderPaint.style = Style.STROKE
      borderPaint.color = borderColor
      borderPaint.strokeWidth = borderSize

      // refer this https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
      setLayerType(
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) LAYER_TYPE_HARDWARE else LAYER_TYPE_SOFTWARE,
        null
      )

      a?.recycle()
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent?): Boolean {
    if (!clickRegion.isEmpty) {
      event?.let {
        if (it.action == MotionEvent.ACTION_DOWN) {
          if (!clickRegion.contains(it.x.toInt(), it.y.toInt())) {
            return false
          }
        }
      }
    }
    return super.onTouchEvent(event)
  }

  override fun dispatchDraw(canvas: Canvas?) {
    if (!clipPath.isEmpty) {
      canvas?.clipPath(clipPath)
    }
    super.dispatchDraw(canvas)
  }

  override fun onDraw(canvas: Canvas?) {
    if (clipPath.isEmpty) {
      super.onDraw(canvas)
      return
    }
    canvas?.let {
      val saveCount = it.save()
      it.clipPath(clipPath)
      super.onDraw(it)
      if (!borderPath.isEmpty) {
        it.drawPath(borderPath, borderPaint)
      }
      it.restoreToCount(saveCount)
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
    when (position) {
      TOP -> {
        if (direction == LEFT) {
          clipPath.moveTo(0f, 0f)
          clipPath.lineTo(width, overlap)
          clipPath.lineTo(width, height)
          clipPath.lineTo(0f, height)

          if (borderEnabled) {
            borderPath.moveTo(0f, 0f)
            borderPath.lineTo(width, overlap)
          }
        } else {
          clipPath.moveTo(0f, overlap)
          clipPath.lineTo(width, 0f)
          clipPath.lineTo(width, height)
          clipPath.lineTo(0f, height)

          if (borderEnabled) {
            borderPath.moveTo(0f, overlap)
            borderPath.lineTo(width, 0f)
          }
        }
      }
      BOTTOM -> {
        if (direction == LEFT) {
          clipPath.moveTo(0f, 0f)
          clipPath.lineTo(width, 0f)
          clipPath.lineTo(width, height - overlap)
          clipPath.lineTo(0f, height)

          if (borderEnabled) {
            borderPath.moveTo(0f, height)
            borderPath.lineTo(width, height - overlap)
          }
        } else {
          clipPath.moveTo(0f, 0f)
          clipPath.lineTo(width, 0f)
          clipPath.lineTo(width, height)
          clipPath.lineTo(0f, height - overlap)

          if (borderEnabled) {
            borderPath.moveTo(0f, height - overlap)
            borderPath.lineTo(width, height)
          }
        }
      }
      LEFT -> {
        if (direction == TOP) {
          clipPath.moveTo(0f, 0f)
          clipPath.lineTo(width, 0f)
          clipPath.lineTo(width, height)
          clipPath.lineTo(overlap, height)

          if (borderEnabled) {
            borderPath.moveTo(0f, 0f)
            borderPath.lineTo(overlap, height)
          }
        } else {
          clipPath.moveTo(overlap, 0f)
          clipPath.lineTo(width, 0f)
          clipPath.lineTo(width, height)
          clipPath.lineTo(0f, height)

          if (borderEnabled) {
            borderPath.moveTo(overlap, 0f)
            borderPath.lineTo(0f, height)
          }
        }
      }
      RIGHT -> {
        if (direction == TOP) {
          clipPath.moveTo(0f, 0f)
          clipPath.lineTo(width, 0f)
          clipPath.lineTo(width - overlap, height)
          clipPath.lineTo(0f, height)

          if (borderEnabled) {
            borderPath.moveTo(width, 0f)
            borderPath.lineTo(width - overlap, height)
          }
        } else {
          clipPath.moveTo(0f, 0f)
          clipPath.lineTo(width - overlap, 0f)
          clipPath.lineTo(width, height)
          clipPath.lineTo(0f, height)

          if (borderEnabled) {
            borderPath.moveTo(width - overlap, 0f)
            borderPath.lineTo(width, height)
          }
        }
      }
      else -> return
    }
    clipPath.close()
    borderPath.close()
    setClickRegion()
  }

  private fun setClickRegion() {
    clipPath.computeBounds(clickRect, true)
    clickRegion.setPath(
      clipPath,
      Region(
        clickRect.left.toInt(),
        clickRect.top.toInt(),
        clickRect.right.toInt(),
        clickRect.bottom.toInt()
      )
    )
  }

  companion object {

    const val NONE = 0
    const val TOP = 1
    const val BOTTOM = 2
    const val LEFT = 3
    const val RIGHT = 4
  }
}