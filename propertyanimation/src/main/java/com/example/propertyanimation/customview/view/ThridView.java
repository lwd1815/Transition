package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者     李文东
 * 创建时间   2017/8/25 18:21
 * 描述	      自定义控件之路径和文字
 *
 * Canvas中绘制路径利用drawPath(Path path,Paint paint)
 *
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class ThridView extends View {
  public ThridView(Context context) {
    super(context);
  }

  public ThridView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ThridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  //绘制都是在ondraw中进行的
  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    /**
     * 1 直线路径
     *
     * moveTo(float x1,float y1):直线的开始点
     * lineTo(float x2,float y2):直线的结束点
     * close():如果连续画几条直线,但没有形成闭环,调用close()会将路径首尾连接起来,形成闭环
     */

    Paint linePaint = new Paint();
    linePaint.setColor(Color.GREEN);
    linePaint.setStyle(Paint.Style.STROKE);
    linePaint.setStrokeWidth(3);
    linePaint.setAntiAlias(true);

    Path linepath = new Path();
    linepath.moveTo(10,10);
    linepath.lineTo(10,100);
    linepath.lineTo(30,100);
    //linepath.lineTo(30,10);
    //linepath.lineTo(10,10);
    linepath.close();
    canvas.drawPath(linepath,linePaint);

    /**
     * 矩形路径
     *
     * addRect(float left,float top,float right,float bottom,Path.direCtion dir)
     * addRect(RectF rect,Path.Direction dir)
     *
     * Path.Direction有两个值
     * Path.Direction.CCW :是counter-clockwise缩写,指创建逆时针方向的矩形路径
     * Path.Direction.CW:是clockwise的缩写,指创建顺时针方向的矩形路径
     */

    Path ccwRectpath = new Path();
    RectF rectF = new RectF(50,50,480,200);
    ccwRectpath.addRect(rectF, Path.Direction.CCW);

    Path cwRectPath = new Path();
    RectF rectF1 = new RectF(290,50,480,200);
    cwRectPath.addRect(rectF1,Path.Direction.CW);
    canvas.drawPath(ccwRectpath,linePaint);
    linePaint.setColor(Color.RED);
    canvas.drawPath(cwRectPath,linePaint);

    //依据路径写文字

    String text = "杨柳依依";
  }
}
