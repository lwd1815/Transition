package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者     李文东
 * 创建时间   2017/12/13 10:44
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class NightView extends View {
  private int offset=0;
  private boolean isShowBorder=false;
  public NightView(Context context) {
    super(context);
  }

  public NightView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public NightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Paint mBackgroundPaint = new Paint();
    int xxW = 15;
    mBackgroundPaint.setColor(Color.argb(119, 0, 0, 0));
    //mBackgroundPaint.setColor(Color.RED);
    canvas.drawRect(0, 0, getWidth(), (getHeight() - getWidth()) / 2 - offset, mBackgroundPaint);
    canvas.drawRect(0, (getWidth() + getHeight()) / 2 - offset, getWidth(), getHeight(),
        mBackgroundPaint);
    if (isShowBorder){
      canvas.drawRect(0, (getHeight() - getWidth()) / 2 - offset, xxW,
          (getWidth() + getHeight()) / 2 - offset, mBackgroundPaint);
      canvas.drawRect(getWidth() - xxW, (getHeight() - getWidth()) / 2 - offset, getWidth(),
          (getWidth() + getHeight()) / 2 - offset, mBackgroundPaint);
    }
  }
}
