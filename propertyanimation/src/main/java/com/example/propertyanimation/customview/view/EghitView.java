package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者     李文东
 * 创建时间   2017/12/12 10:57
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class EghitView extends View {
  public EghitView(Context context) {
    super(context);
  }

  public EghitView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public EghitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    /**
     * reset()重置画笔
     * setColor()给画笔设置颜色
     * setARGB()给画笔设置颜色
     * setAlpha()设置透明度
     * setStyle()设置画笔样式
     * setStrokeWidth()设置画笔宽度
     * setAntiAlias()设置画笔是否抗锯齿
     * setStrokeCap()设置线冒样式 Cap.ROUND圆形线冒,paint.Cap.BUTT无线冒,Cap.SQUARE(方形线帽)
     * setStrokeMiter()设置画笔的倾斜度,
     * setPathEffect()设置路径样式,取值类型是所有派生自PathEffect的子类
     * setStrokeJoin() 设置结合处的角度Join.MITER结合处为锐角,Join.Round结合处为圆弧,Join.Bevel结合处为直线
     */

    Paint paint = new Paint();
    paint.setStrokeWidth(5);
    paint.setColor(Color.RED);
    paint.setStyle(Paint.Style.STROKE);

    Path path = new Path();
    path.moveTo(100,600);
    path.lineTo(400,100);
    path.lineTo(700,900);
    canvas.drawPath(path,paint);

    paint.setColor(Color.GREEN);
    paint.setPathEffect(new CornerPathEffect(100));
    canvas.drawPath(path,paint);

    paint.setColor(Color.YELLOW);
    paint.setPathEffect(new CornerPathEffect(200));
    canvas.drawPath(path,paint);
  }
}
