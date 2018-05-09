package com.santalu.diagonalimageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by santalu on 7/4/17.
 *
 * Note: if position set NONE mask won't be applied
 *
 * POSITION    DIRECTION
 *
 * TOP         LEFT |  RIGHT
 * BOTTOM      LEFT |  RIGHT
 * LEFT        TOP  |  BOTTOM
 * RIGHT       TOP  |  BOTTOM
 */

public class DiagonalImageView extends AppCompatImageView {

  public static final int NONE = 0;
  public static final int TOP = 1;
  public static final int RIGHT = 2;
  public static final int BOTTOM = 4;
  public static final int LEFT = 8;

  private final Path clipPath = new Path();
  private final Path borderPath = new Path();
  private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Region clickRegion = new Region();
  private RectF clickRect = new RectF();

  private int position;
  private int direction;
  private int overlap;
  private int borderColor;
  private int borderSize;
  private boolean borderEnabled;

  public DiagonalImageView(Context context) {
    super(context);
    init(context, null);
  }

  public DiagonalImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (attrs == null) {
      return;
    }

    // refer to this https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
    if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) {
      setLayerType(LAYER_TYPE_HARDWARE, null);
    } else {
      setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiagonalImageView);
    try {
      position = a.getInteger(R.styleable.DiagonalImageView_di_position, NONE);
      direction = a.getInteger(R.styleable.DiagonalImageView_di_direction, RIGHT);
      overlap = a.getDimensionPixelSize(R.styleable.DiagonalImageView_di_overlap, 0);
      borderSize = a.getDimensionPixelSize(R.styleable.DiagonalImageView_di_borderSize, 0);
      borderColor = a.getColor(R.styleable.DiagonalImageView_di_borderColor, Color.BLACK);
      borderEnabled = a.getBoolean(R.styleable.DiagonalImageView_di_borderEnabled, false);

      borderPaint.setColor(borderColor);
      borderPaint.setStyle(Style.STROKE);
      borderPaint.setStrokeWidth(borderSize);
    } finally {
      a.recycle();
    }
  }

  public void set(@Position int position, @Direction int direction) {
    if (this.position != position || this.direction != direction) {
      clipPath.reset();
      borderPath.reset();
    }
    this.position = position;
    this.direction = direction;
    postInvalidate();
  }

  @Position
  public int getPosition() {
    return position;
  }

  public void setPosition(@Position int position) {
    if (this.position != position) {
      clipPath.reset();
      borderPath.reset();
    }
    this.position = position;
    postInvalidate();
  }

  @Direction
  public int getDirection() {
    return direction;
  }

  public void setDirection(@Direction int direction) {
    if (this.direction != direction) {
      clipPath.reset();
      borderPath.reset();
    }
    this.direction = direction;
    postInvalidate();
  }

  public boolean isBorderEnabled() {
    return borderEnabled;
  }

  public void setBorderEnabled(boolean enabled) {
    borderEnabled = enabled;
    postInvalidate();
  }

  public void setOverlap(int overlap) {
    if (this.overlap != overlap) {
      clipPath.reset();
      borderPath.reset();
    }
    this.overlap = overlap;
    postInvalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (clipPath.isEmpty()) {
      super.onDraw(canvas);
      return;
    }

    int saveCount = canvas.save();
    canvas.clipPath(clipPath);
    super.onDraw(canvas);
    if (!borderPath.isEmpty()) {
      canvas.drawPath(borderPath, borderPaint);
    }
    canvas.restoreToCount(saveCount);
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    if (!clipPath.isEmpty()) {
      canvas.clipPath(clipPath);
    }
    super.dispatchDraw(canvas);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    if (!changed) {
      return;
    }

    if (clipPath.isEmpty()) {
      int width = getMeasuredWidth();
      int height = getMeasuredHeight();

      if (width <= 0 || height <= 0) {
        return;
      }

      setClipPath(width, height);
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (!clickRegion.isEmpty()) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          Point point = new Point();
          point.x = (int) event.getX();
          point.y = (int) event.getY();
          if (!clickRegion.contains(point.x, point.y)) {
            return false;
          }
      }
    }
    return super.onTouchEvent(event);
  }

  private void setClipPath(final int width, final int height) {
    clipPath.reset();
    borderPath.reset();

    switch (position) {
      case TOP:
        if (direction == LEFT) {
          clipPath.moveTo(0, 0);
          clipPath.lineTo(width, overlap);
          clipPath.lineTo(width, height);
          clipPath.lineTo(0, height);

          if (borderEnabled) {
            borderPath.moveTo(0, 0);
            borderPath.lineTo(width, overlap);
          }
        } else {
          clipPath.moveTo(0, overlap);
          clipPath.lineTo(width, 0);
          clipPath.lineTo(width, height);
          clipPath.lineTo(0, height);

          if (borderEnabled) {
            borderPath.moveTo(0, overlap);
            borderPath.lineTo(width, 0);
          }
        }
        break;
      case RIGHT:
        if (direction == TOP) {
          clipPath.moveTo(0, 0);
          clipPath.lineTo(width, 0);
          clipPath.lineTo(width - overlap, height);
          clipPath.lineTo(0, height);

          if (borderEnabled) {
            borderPath.moveTo(width, 0);
            borderPath.lineTo(width - overlap, height);
          }
        } else {
          clipPath.moveTo(0, 0);
          clipPath.lineTo(width - overlap, 0);
          clipPath.lineTo(width, height);
          clipPath.lineTo(0, height);

          if (borderEnabled) {
            borderPath.moveTo(width - overlap, 0);
            borderPath.lineTo(width, height);
          }
        }
        break;
      case BOTTOM:
        if (direction == LEFT) {
          clipPath.moveTo(0, 0);
          clipPath.lineTo(width, 0);
          clipPath.lineTo(width, height - overlap);
          clipPath.lineTo(0, height);

          if (borderEnabled) {
            borderPath.moveTo(0, height);
            borderPath.lineTo(width, height - overlap);
          }
        } else {
          clipPath.moveTo(0, 0);
          clipPath.lineTo(width, 0);
          clipPath.lineTo(width, height);
          clipPath.lineTo(0, height - overlap);

          if (borderEnabled) {
            borderPath.moveTo(0, height - overlap);
            borderPath.lineTo(width, height);
          }
        }
        break;
      case LEFT:
        if (direction == TOP) {
          clipPath.moveTo(0, 0);
          clipPath.lineTo(width, 0);
          clipPath.lineTo(width, height);
          clipPath.lineTo(overlap, height);

          if (borderEnabled) {
            borderPath.moveTo(0, 0);
            borderPath.lineTo(overlap, height);
          }
        } else {
          clipPath.moveTo(overlap, 0);
          clipPath.lineTo(width, 0);
          clipPath.lineTo(width, height);
          clipPath.lineTo(0, height);

          if (borderEnabled) {
            borderPath.moveTo(overlap, 0);
            borderPath.lineTo(0, height);
          }
        }
        break;
    }

    clipPath.close();
    clipPath.computeBounds(clickRect, true);
    clickRegion.setPath(clipPath,
        new Region((int) clickRect.left,
            (int) clickRect.top,
            (int) clickRect.right,
            (int) clickRect.bottom));
    borderPath.close();
  }

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({ NONE, LEFT, RIGHT, TOP, BOTTOM })
  public @interface Position {
  }

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({ LEFT, RIGHT, TOP, BOTTOM })
  public @interface Direction {
  }
}