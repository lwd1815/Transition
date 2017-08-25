package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者     李文东
 * 创建时间   2017/8/25 14:18
 * 描述	      自定义之Paint与Canvas
 *            paint 相当于笔,Canvas 相当于纸
 * paint的设置:
 * paint.setAnitiAilas(true) 抗锯齿
 * paint.setColor(Color.RED) 设置画笔颜色
 * paint.setStyle(Style.fill) 设置填充样式
 * paint.setStrokeVidth(30)  设置画笔宽度
 * paint.setShandowlayer(10,15,color.Red) 设置阴影
 *
 * 设置样式填充
 *
 * Paint.Style.FILL :填充内部
 * Paint.Style.FILL_AND_STROKE:填充内部和描边
 * Paint.Style.STROKE:仅描边
 *
 * setShadowLayer(float radius,float dx,float dy,int color)添加阴影
 * 参数:radius:阴影的倾斜度    dy:垂直位移   dx:水平位移
 *
 *
 * Canvas的基本设置
 *
 * 画布背景的设置:
 * canvas.drawColor(Color.BLUE)
 * canvas.drawRGB(255,255,0) 这两个功能一样,都是用来设置背景颜色的
 *
 *
 * 有关画笔的设置都是Paint ,而有关画图的设置都在Canvas类中
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class SecondView extends View{

  Context mcontext;
  public SecondView(Context context) {
    super(context);
    mcontext=context;
  }

  public SecondView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SecondView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  //重写ondraw()函数,在每次重绘时自主实现绘图

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    //设置画笔的基本属性
    Paint mpaint = new Paint();
    mpaint.setAntiAlias(true);
    mpaint.setColor(Color.RED);
    //只描边
    mpaint.setStyle(Paint.Style.STROKE);
    mpaint.setStrokeWidth(5);
    mpaint.setShadowLayer(10,15,15,Color.GREEN);


    //设置画布背景
    canvas.drawRGB(255,255,255);
    /**
     * 1 画圆
     */
    canvas.drawCircle(200,200,100,mpaint);

    /**
     * 2 画直线
     *
     * drawLine(float startX,float startY,float stopX,float stopY,Paint paint)
     */

    Paint linePaint = new Paint();
    linePaint.setColor(Color.GREEN);
    linePaint.setStyle(Paint.Style.FILL);
    linePaint.setStrokeWidth(6);

    canvas.drawLine(100,100,400,400,linePaint);

    /**
     * 3 画多条直线
     * drawLines (float[] pts,Paint paint)
     * drawLines(float [] pts,int offset,int count, Paint paint)
     *
     * pts 点的集合,每两个点成一条线
     */

    Paint linesPaint = new Paint();
    linesPaint.setColor(Color.LTGRAY);
    linesPaint.setStrokeWidth(8);
    linesPaint.setStyle(Paint.Style.FILL);
    float[] pts ={100,100,300,300,300,500,500,700};
    canvas.drawLines(pts,linesPaint);

    /**
     * 4 画点
     * drawPoint(float x,float y,Paint paint)
     * float x:点的x坐标
     * float y:点的y坐标
     */

    Paint pointPaint = new Paint();
    pointPaint.setColor(Color.YELLOW);
    pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    pointPaint.setStrokeWidth(10);
    canvas.drawPoint(400,400,pointPaint);

    /**
     * 5 画多个点
     * drawPoints(float[] pts,Paint paint)
     * drawPoints(float[] pts,int offset,int count,Paint paint)
     *
     * float[] pts 点的集合
     * int offset 集合中跳过的数值个数,注意不是点的个数,一个点是两个数值
     * count 参与绘制的数值的个数,值pts[]里数值的个数,而不是点的个数.因为两个数值确定一个点
     *
     * for example
     *
     * 跳过第一个点,画出后面的两个点,第四个点不画
     */

    Paint pointsPaint = new Paint();
    pointsPaint.setColor(Color.RED);
    pointsPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    pointsPaint.setStrokeWidth(20);
    pointsPaint.setAntiAlias(true);
    //画出了600和400的点
    float[] ptss={500,500,600,600,300,300,400,400};
    canvas.drawPoints(ptss,2,4,pointsPaint);

    /**
     * 6 矩形工具Rect和RectF
     *
     * RectF 构造函数有四个,
     *
     * RectF()
     * RectF(float left,float top,float right,float bottom)
     * RectF(RectF r)
     * RectF(Rect r)
     *
     *
     * Rect 构造函数如下
     *
     * Rect()
     * Rect(int left,int top,int right,int bottom)
     * Rect(Rect r)
     *
     * 矩形
     *
     * //直接传入四个点画出矩形
     * drawRect(float left,float top,float right,float bottom,Paint paint)
     * //传入RectF或者Rect矩形变量来指定所画的矩形
     * drawRect(RectF rect,Paint paint)
     * drawRect(Rect r,Paint paint)
     */
    Paint rectPaint = new Paint();
    rectPaint.setColor(Color.GREEN);
    rectPaint.setStrokeWidth(3);
    rectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    rectPaint.setAntiAlias(true);

    canvas.drawRect(400,200,500,300,rectPaint);

    RectF rectF =new RectF(400,400,500,500);
    canvas.drawRect(rectF,rectPaint);

    /**
     * 7 圆角矩形
     * drawRoundRect(RectF rect,float rx,float ry,Paint paint)
     *
     * RectF rect:要画的矩形
     *
     * float rx:生成圆角的椭圆的x轴半径
     * float ry:生成圆角的椭圆的y轴半径
     */

    Paint roundRectPaint = new Paint();
    roundRectPaint.setColor(Color.YELLOW);
    roundRectPaint.setStyle(Paint.Style.STROKE);
    roundRectPaint.setAntiAlias(true);
    roundRectPaint.setStrokeWidth(5);

    RectF rectF1 = new RectF(200,500,300,600);
    canvas.drawRoundRect(rectF1,20,10,roundRectPaint);

    /**
     * 8 椭圆
     *  椭圆是根据矩形生成的,以矩形的长为椭圆的x轴,矩形的宽为椭圆的y轴,建立的椭圆图形
     *  drawOval(RectF oval,Paint )
     */
    Paint ovalPaint = new Paint();
    ovalPaint.setColor(Color.GREEN);
    ovalPaint.setStyle(Paint.Style.STROKE);
    ovalPaint.setStrokeWidth(3);
    ovalPaint.setAntiAlias(true);

    RectF rectF2 = new RectF(300,700,400,900);
    canvas.drawOval(rectF2,ovalPaint);
    ovalPaint.setColor(Color.RED);
    canvas.drawRect(rectF2,ovalPaint);

    /**
     * 9 弧
     * 弧是椭圆的一部分,而椭圆是根据矩形来生成的,所以弧当然也是根据矩形来生成的
     * drawArc(RectF oval,float startAngle,float sweepAngle,boolean useCenter,Paint paint)
     *
     *RectF oval :生成椭圆的矩形
     * float startAngle :弧开始的角度,以x轴正方向为0度
     * float sweepAngle :弧持续的角度
     * boolean useCenter:是否有弧的两边,ture,还两边,false 只有一条弧
     */

    Paint arcPaint = new Paint();
    arcPaint.setColor(Color.YELLOW);
    arcPaint.setAntiAlias(true);
    arcPaint.setStyle(Paint.Style.STROKE);
    arcPaint.setStrokeWidth(5);

    RectF rectF3 = new RectF(50,500,100,600);
    canvas.drawArc(rectF3,10,90,false,arcPaint);

    RectF rectF4 = new RectF(50,800,100,900);
    canvas.drawArc(rectF4,20,120,true,arcPaint);

    arcPaint.setColor(Color.RED);
    arcPaint.setStyle(Paint.Style.FILL);
    canvas.drawArc(rectF3,10,90,false,arcPaint);
    canvas.drawArc(rectF4,20,120,true,arcPaint);

  }
}
