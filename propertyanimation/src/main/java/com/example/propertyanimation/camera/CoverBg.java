package com.example.propertyanimation.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 孙应恒 on 2017/11/27.
 * Description:
 */

public class CoverBg extends View {

  private int offset;
  private boolean isShowBorder = false;

  public CoverBg(Context context) {
    super(context);
  }

  public CoverBg(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public CoverBg(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Paint mBackgroundPaint = new Paint();
    int xxW = 15;
    mBackgroundPaint.setColor(Color.argb(119, 0, 0, 0));
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

  public void setOffset(int mOffset){
    this.offset = mOffset;
    invalidate();
  }

  public void setBorderVisibility(boolean isShow){
    this.isShowBorder = isShow;
    invalidate();
  }
}
