package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
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
    /**
     * 使用setPath()构造不规则区域
     * path 用来构造的区域路径
     * Region clip 与前面的path所构造的路径取交集,并将两交集设置为最终的区域
     */
    Paint seconPaint =new Paint();
    seconPaint.setColor(Color.GREEN);
    seconPaint.setAntiAlias(true);
    seconPaint.setStrokeWidth(3);
    seconPaint.setStyle(Paint.Style.STROKE);

    //椭圆区域
    Path ovalPath = new Path();
    RectF rectF = new RectF(50,50,200,500);
    ovalPath.addOval(rectF,Path.Direction.CCW);
    //setPath 时,传入一个比椭圆区域小的矩形区域,让去交集
    Region ren = new Region();
    ren.setPath(ovalPath,new Region(50,50,200,200));
    //画出路径
    //drawRegions(canvas,ren,seconPaint);

    /**
     * 矩形集枚举区域-RegionIterator类
     * 对于特定的区域，我们都可以使用多个矩形来表示其大致形状。事
     * 实上，如果矩形足够小，一定数量的矩形就能够精确表示区域的形状，
     * 也就是说，一定数量的矩形所合成的形状，也可以代表区域的形状。
     * RegionIterator类，实现了获取组成区域的矩形集的功能，
     * 其实RegionIterator类非常简单，总共就两个函数，一个构造函数和一个获取下一个矩形的函数；
     *
     * RegionIterator(Region region)//根据区域构建对应的矩形集
     * boolean next(Rect r)//获取下一个矩形,结果保存在参数Rect r中
     *
     * 由于在Canvas中没有直接绘制Region的函数,我们想要绘制一个区域,就只能通过利用RegionIterator构造矩形集来逼近的显示区域
     *
     * 区域的合并,交叉等操作
     * 无论是区域还是矩形，都会涉及到与另一个区域的一些操作，比如取交集、取并集等，涉及到的函数有：
     * public final boolean union(Rect r)
       public boolean op(Rect r, Op op) {
       public boolean op(int left, int top, int right, int bottom, Op op)
       public boolean op(Region region, Op op)
       public boolean op(Rect rect, Region region, Op op)
     */


    Rect rect1=new Rect(100,100,400,200);
    Rect rect2=new Rect(200,0,300,300);
    //构造一个画笔,画出矩形轮廓
    Paint paint = new Paint();
    paint.setColor(Color.RED);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(2);
    canvas.drawRect(rect1,paint);
    canvas.drawRect(rect2,paint);

    //构造两个Region
    Region region1=new Region(rect1);
    Region region2=new Region(rect2);
    //取两个区域的交集
    region1.op(region2, Region.Op.REPLACE);

    //在构造一个画笔,填充region操作结果
    Paint paint_fill=new Paint();
    paint_fill.setColor(Color.GREEN);
    paint_fill.setStyle(Paint.Style.FILL);
    drawRegion(canvas,region,paint_fill);
  }

  private void drawRegions(Canvas canvas, Region ren, Paint seconPaint) {
    RegionIterator iter = new RegionIterator(ren);
    Rect rect = new Rect();
    while (iter.next(rect)){
      canvas.drawRect(rect,seconPaint);
    }
  }

  private void drawRegion(Canvas canvas, Region region, Paint firstPaint) {
    RegionIterator iter = new RegionIterator(region);
    Rect r = new Rect();
    while (iter.next(r)){
      canvas.drawRect(r,firstPaint);
    }
  }
  /**
   * 无论是矩形还是区域,都会涉及到与另一个区域的一些操作,比如取交集,取并集等,涉及到的函数有:
   * public final boolean union(rect r)指定合并
   * public boolean op(Rect r,Op p)
   * public boolean op(Region region,Op op)
   * public boolean op(Rect rect,Region region ,Op op)
   * public boolean op(int left,int top,int right,int bottom,Op op)
   * 除了union(Rect rect)是指定合并操作以外,其他四个op()构造函数,都是指定与另一个区域的操作,其中最重要的指定Op的参数,
   */

  public enum Op{
    DIFFERENCE(),//最终区域为region1与region2不同的区域
    INIERSECT(),//最终区域为region1与region2相交的区域
    UNION(),//最终区域为region1与region2组合在一起的区域
    XOR(),//最终区域为region1与region2相交之外的区域
    REVERSE_DIFFERENCE(),//最终区域为greion1与region2不同的区域
    REPLACE();//最终区域为region2的区域
  }


}
