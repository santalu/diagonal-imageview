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
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
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

      with(a) {
        position = getInt(R.styleable.DiagonalImageView_di_position, NONE)
        direction = getInt(R.styleable.DiagonalImageView_di_direction, NONE)
        overlap = getDimensionPixelSize(R.styleable.DiagonalImageView_di_overlap, 0).toFloat()
        borderEnabled = getBoolean(R.styleable.DiagonalImageView_di_borderEnabled, false)
        borderSize = getDimensionPixelSize(R.styleable.DiagonalImageView_di_borderSize, 0).toFloat()
        borderColor = getColor(R.styleable.DiagonalImageView_di_borderColor, Color.BLACK)
        recycle()
      }

      with(borderPaint) {
        style = Style.STROKE
        color = borderColor
        strokeWidth = borderSize
      }

      // refer this https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
      setLayerType(
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) LAYER_TYPE_HARDWARE else LAYER_TYPE_SOFTWARE,
        null
      )
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
          with(clipPath) {
            moveTo(0f, 0f)
            lineTo(width, overlap)
            lineTo(width, height)
            lineTo(0f, height)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(0f, 0f)
              lineTo(width, overlap)
            }
          }
        } else {
          with(clipPath) {
            moveTo(0f, overlap)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(0f, overlap)
              lineTo(width, 0f)
            }
          }
        }
      }
      BOTTOM -> {
        if (direction == LEFT) {
          with(clipPath) {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height - overlap)
            lineTo(0f, height)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(0f, height)
              lineTo(width, height - overlap)
            }
          }
        } else {
          with(clipPath) {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height - overlap)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(0f, height - overlap)
              lineTo(width, height)
            }
          }
        }
      }
      LEFT -> {
        if (direction == TOP) {
          with(clipPath) {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(overlap, height)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(0f, 0f)
              lineTo(overlap, height)
            }
          }
        } else {
          with(clipPath) {
            moveTo(overlap, 0f)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(overlap, 0f)
              lineTo(0f, height)
            }
          }
        }
      }
      RIGHT -> {
        if (direction == TOP) {
          with(clipPath) {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width - overlap, height)
            lineTo(0f, height)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(width, 0f)
              lineTo(width - overlap, height)
            }
          }
        } else {
          with(clipPath) {
            moveTo(0f, 0f)
            lineTo(width - overlap, 0f)
            lineTo(width, height)
            lineTo(0f, height)
          }

          if (borderEnabled) {
            with(borderPath) {
              moveTo(width - overlap, 0f)
              lineTo(width, height)
            }
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