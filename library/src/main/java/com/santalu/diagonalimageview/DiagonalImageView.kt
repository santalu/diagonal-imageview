package com.santalu.diagonalimageview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Path
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.santalu.diagonalimageview.Direction.BOTTOM
import com.santalu.diagonalimageview.Direction.LEFT
import com.santalu.diagonalimageview.Direction.NONE
import com.santalu.diagonalimageview.Direction.RIGHT
import com.santalu.diagonalimageview.Direction.TOP

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class DiagonalImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
): AppCompatImageView(context, attrs, defStyleAttr) {

  private val clipPath: Path = Path()
  private val borderPath: Path = Path()
  private val borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

  var start: Direction = NONE
  var end: Direction = NONE
  var distance: Float = 0f

  var borderEnabled: Boolean = false
  var borderSize: Float = 0f
  var borderColor: Int = Color.BLACK

  init {
    attrs?.let {
      context.obtainStyledAttributes(it, R.styleable.DiagonalImageView).apply {
        start = Direction.get(getInt(R.styleable.DiagonalImageView_di_start, 0))
        end = Direction.get(getInt(R.styleable.DiagonalImageView_di_end, 0))
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

      // https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
      val layerType = if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) LAYER_TYPE_HARDWARE else LAYER_TYPE_SOFTWARE
      setLayerType(layerType, null)
    }
  }

  override fun invalidate() {
    super.invalidate()
    setClipPath()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    setClipPath()
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

    canvas?.apply {
      save()
      clipPath(clipPath)
      super.onDraw(this)
      if (!borderPath.isEmpty) {
        drawPath(borderPath, borderPaint)
      }
      restore()
    }
  }

  private fun setClipPath() {
    val width = measuredWidth.toFloat()
    val height = measuredHeight.toFloat()
    if (width <= 0 || height <= 0) return

    clipPath.reset()
    borderPath.reset()

    when (start) {
      LEFT -> createLeftPath(width, height)
      TOP -> createTopPath(width, height)
      RIGHT -> createRightPath(width, height)
      BOTTOM -> createBottomPath(width, height)
      else -> return
    }

    clipPath.close()
    borderPath.close()
  }

  private fun isTopOrLeft(): Boolean = end == TOP || end == LEFT

  private fun createTopPath(width: Float, height: Float) {
    if (isTopOrLeft()) {
      clipPath.apply {
        moveTo(0f, 0f)
        lineTo(width, distance)
        lineTo(width, height)
        lineTo(0f, height)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(0f, 0f)
          lineTo(width, distance)
        }
      }
    } else {
      clipPath.apply {
        moveTo(0f, distance)
        lineTo(width, 0f)
        lineTo(width, height)
        lineTo(0f, height)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(0f, distance)
          lineTo(width, 0f)
        }
      }
    }
  }

  private fun createBottomPath(width: Float, height: Float) {
    if (isTopOrLeft()) {
      clipPath.apply {
        moveTo(0f, 0f)
        lineTo(width, 0f)
        lineTo(width, height - distance)
        lineTo(0f, height)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(0f, height)
          lineTo(width, height - distance)
        }
      }
    } else {
      clipPath.apply {
        moveTo(0f, 0f)
        lineTo(width, 0f)
        lineTo(width, height)
        lineTo(0f, height - distance)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(0f, height - distance)
          lineTo(width, height)
        }
      }
    }
  }

  private fun createLeftPath(width: Float, height: Float) {
    if (isTopOrLeft()) {
      clipPath.apply {
        moveTo(distance, 0f)
        lineTo(width, 0f)
        lineTo(width, height)
        lineTo(0f, height)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(distance, 0f)
          lineTo(0f, height)
        }
      }
    } else {
      clipPath.apply {
        moveTo(0f, 0f)
        lineTo(width, 0f)
        lineTo(width, height)
        lineTo(distance, height)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(0f, 0f)
          lineTo(distance, height)
        }
      }
    }
  }

  private fun createRightPath(width: Float, height: Float) {
    if (isTopOrLeft()) {
      clipPath.apply {
        moveTo(0f, 0f)
        lineTo(width, 0f)
        lineTo(width - distance, height)
        lineTo(0f, height)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(width, 0f)
          lineTo(width - distance, height)
        }
      }
    } else {
      clipPath.apply {
        moveTo(0f, 0f)
        lineTo(width - distance, 0f)
        lineTo(width, height)
        lineTo(0f, height)
      }

      if (borderEnabled) {
        borderPath.apply {
          moveTo(width - distance, 0f)
          lineTo(width, height)
        }
      }
    }
  }
}