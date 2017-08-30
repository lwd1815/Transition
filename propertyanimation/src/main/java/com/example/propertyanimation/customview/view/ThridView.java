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
     * 2 矩形路径
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

    Paint textPaint = new Paint();
    String text = "1 2 3 4";
    textPaint.setColor(Color.BLACK);
    textPaint.setStrokeWidth(2);
    textPaint.setTextSize(35);
    textPaint.setAntiAlias(true);
    canvas.drawTextOnPath(text,cwRectPath,20,10,linePaint);
    canvas.drawTextOnPath(text,ccwRectpath,20,10,linePaint);

    /**
     * 3 圆角矩形路径
     * addRoundRect(RectF rect,float[]radii,Path.Direction dir)
     * addRoundRect(RectF rect,flaot rx,float ry,Path.Direction dir)
     *
     * 第一个构造函数:可以定制每个角的圆角大小
     *
     * float[]radii :必须传入8个数值,分四组,分别对应每个角所使用的的椭圆的横轴半径和纵轴半径,如
     * {X1,y1,x2,y2,x3,y3,x4,y4},其中x1,y1对应第一个角的(左上角)用来产生圆角的椭圆的横轴半径和纵轴半径
     *
     * 第二个构造函数:只能构建统一圆角大小
     *
     * float rx 所产生的圆角的椭圆的横轴半径
     * float ry 所产生的圆角的椭圆的纵轴半径
     *
     */

    Path roundPath = new Path();
    RectF rectF2 = new RectF(200,200,400,400);
    roundPath.addRoundRect(rectF2,10,15, Path.Direction.CCW);

    RectF rectF3 = new RectF(500,500,600,600);
    float radii[]={10,20,15,30,20,10,20,30};
    roundPath.addRoundRect(rectF3,radii, Path.Direction.CW);

    canvas.drawPath(roundPath,linePaint);

    /**
     * 4 圆形路径
     * addCircle(float x,float y,float radius,Path.Direction dir)
     *
     * float x 圆形x轴坐标
     * float y 圆形y轴坐标
     * float radius 圆半径
     */

    Path circlePath =new Path();
    circlePath.addCircle(400,500,100, Path.Direction.CCW);
    canvas.drawPath(circlePath,linePaint);
    /**
     * 5 椭圆路径
     * addoval(RectF rect ,Path Direction dir)
     * RectF rect 生成椭圆所对应的矩形
     * path.direction:生成方式,与矩形一样,分为顺时针和逆时针
     */

    linePaint.setColor(Color.GREEN);
    Path ovalPath = new Path();
    RectF rectF4 = new RectF(50,300,200,500);
    ovalPath.addOval(rectF4, Path.Direction.CCW);
    canvas.drawPath(ovalPath,linePaint);

    /**
     * 6 弧形路径
     * addArc(RectF rect,float startAngle,float swwepAngle)
     *
     * RectF oval :弧是椭圆的一部分,这个参数就是生成椭圆的矩形
     * float startAngle :开始的角度,x轴正方向为0度
     * float sweepAngle 持续的度数
     */

    linePaint.setColor(Color.GRAY);
    Path arcPath = new Path();
    RectF rectF5 = new RectF(500,200,600,300);
    arcPath.addArc(rectF5,0,120);
    canvas.drawPath(arcPath,linePaint);

    /**
     * 分割线
     */
    Paint linesPaint = new Paint();
    linesPaint.setColor(Color.GRAY);
    linesPaint.setStrokeWidth(2);
    linesPaint.setAntiAlias(true);
    float [] pts = {0,600,800,600};
    canvas.drawLines(pts,linesPaint);

    /**
     * 文字
     */
    //普通设置
    Paint textpaint = new Paint();
    textpaint.setColor(Color.RED);
    textpaint.setStrokeWidth(3);
    textpaint.setAntiAlias(true);
    textpaint.setStyle(Paint.Style.FILL);
    //样式设置
    //设置字体是否为粗体
    textpaint.setFakeBoldText(true);
    //设置下划线
    textpaint.setUnderlineText(true);
    //设置字体水平倾斜度,普通斜度是-0.25
    textpaint.setTextSkewX((float) -0.25);
    //设置是否带有删除线效果
    textpaint.setStrikeThruText(true);

    //其他设置
    //只会将水平方向拉伸,高度不变
    textpaint.setTextScaleX(2);
  }
}