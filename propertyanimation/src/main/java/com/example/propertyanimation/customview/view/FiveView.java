package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者     李文东
 * 创建时间   2017/12/6 18:56
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class FiveView extends View {
  public FiveView(Context context) {
    this(context,null);
  }

  public FiveView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public FiveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    Paint paint=new Paint();
    paint.setColor(Color.RED);
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);
    paint.setStrokeWidth(5);
    /**
     * translate(float dx,float dy)
     * translate函数其实实现的相当于平移坐标系,即平移坐标的原点的位置,translate()函数的原型如上
     * float dx:水平方向平移的距离,
     * float dy:垂直方向平移的距离
     */
    canvas.translate(20,20);
    Rect rect = new Rect(0,0,200,200);
    canvas.drawRect(rect,paint);

    /**
     * 屏幕显示与Canvas的关系
     */
    //构造两个画笔，一个红色，一个绿色
    Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3);
    Paint paint_red   = generatePaint(Color.RED, Paint.Style.STROKE, 3);

    //构造一个矩形
    Rect rect1 = new Rect(0,0,400,220);

    //在平移画布前用绿色画下边框
    canvas.drawRect(rect1, paint_green);

    //平移画布后,再用红色边框重新画下这个矩形
    canvas.translate(100, 100);
    canvas.drawRect(rect1, paint_red);
    /**
     * 从上面的运行效果可以看出
     * 绿色框并没有移动
     * 这是因为屏幕显示与Canvas根本不是一个概念,Canvas是一个很虚幻的概念,相当于一个透明的图层,
     * 每次Canvas画图时,都会产生一个透明图层,然后在这个图层上画画,画完之后覆盖在屏幕上显示
     * 综上所述,每次画图时的canvas都是不同给的,属于一一对应关系
     *
     * 1、每次调用canvas.drawXXXX系列函数来绘图进，都会产生一个全新的Canvas画布。
     2、如果在DrawXXX前，调用平移、旋转等函数来对Canvas进行了操作，那么这个操作是不可逆的！每次产生的画布的最新位置都是这些操作后的位置。（关于Save()、Restore()的画布可逆问题的后面再讲）
     3、在Canvas与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
     */

    /**
     * 旋转Rotate  li5783553777
     */

  }
  private Paint generatePaint(int color,Paint.Style style,int width)
  {
    Paint paint = new Paint();
    paint.setColor(color);
    paint.setStyle(style);
    paint.setStrokeWidth(width);
    return paint;
  }

}
