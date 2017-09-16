package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者     李文东
 * 创建时间   2017/9/4 14:52
 * 描述	      自定义view之绘图篇 region
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class FourView extends View{
  public FourView(Context context) {
    super(context);
  }

  public FourView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public FourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    /**
     * 1 基本构造函数
     * public Region//创建一个空的区域
     * public region(Region region)//拷贝一个region范围
     * public region(rect r) //创建一个矩形区域
     * public region(int left,int top, int right,int bottom)//创建一个矩形区域
     *
     * 2间接构造
     *
     * //从某种意义上来讲,空置也是一个构造函数,即将原来的一个区域变量变成一个空变量,要在利用其他的set方法重新构造区域
     * public void setEmpty()
     * //利用新的区域值来替换原来的区域
     * public boolean set(region region)
     * //利用矩形所代表的区域来替换原来的区域
     * public boolean set(rect r)
     * //同样,根据矩形的两个点构造出矩形区域来替换原来的区域值
     * public boolean set(int left,int top,int right,int bottom)
     * //根据路径的区域与某区域的交集,构造出新的区域
     * public boolean setPath(Path path,Region clip)
     */

    /**
     * first set
     */

    Paint firstPaint = new Paint();
    firstPaint.setStrokeWidth(3);
    firstPaint.setAntiAlias(true);
    firstPaint.setStyle(Paint.Style.FILL);
    firstPaint.setColor(Color.RED);

    Region region = new Region(10,10,100,100);
    region.set(100,100,200,200);
    drawRegion(canvas,region,firstPaint);
  }

  private void drawRegion(Canvas canvas, Region region, Paint firstPaint) {
    RegionIterator iter = new RegionIterator(region);
    Rect r = new Rect();
    while (iter.next(r)){
      canvas.drawRect(r,firstPaint);
    }
  }
}
