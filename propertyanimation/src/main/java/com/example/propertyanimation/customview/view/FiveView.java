package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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
     * 旋转Rotate
     * H画布的旋转是默认是围绕坐标原点旋转的,其实旋转的是画布canvas,以后在画布上画出来的东西显示出来的时候看起来都是旋转的,
     * VOID Rotate(float degrees)
     * void ratate(float degress ,float px,float py)
     * px,py旋转的中心坐标
     */

    Rect roat = new Rect(300,10,500,100);
    canvas.drawRect(roat,paint_green);
    canvas.rotate(30);
    canvas.drawRect(roat,paint_red);

    /**
     * 缩放scale
     * public void scale(float sx,float sy)
     * public final void scale(float sx,float sy,float px,float py)
     * sx 水平反向伸缩的比例,假设原坐标轴的比例为n,不变时为1,在变更的x轴密度为n*sx,所以,sx为小数为缩小,sx为整数位放大
     * float sy:垂直方向
     * 注意:这里x,y轴的密度改变,显示在图形上就会正好相同,比如x轴缩小,那么显示的图形也会缩小
     */

    Rect rect2=new Rect(10,10,200,100);
    canvas.drawRect(rect2,paint_green);
    canvas.scale(0.5f,1);
    canvas.drawRect(rect2,paint_red);

    /**]
     *扭曲skew
     *void skew(float sx,float sy)
     * sx 将画布在x方向上倾斜相应的角度,sx倾斜角度tan值
     * sy 将画布在y轴方向上倾斜相应的角度,sy为倾斜角度的tan值
     */

    Rect rect3=new Rect(200,200,400,400);
    canvas.drawRect(rect3,paint_green);
    canvas.skew(1.732f,0);
    canvas.drawRect(rect3,paint_red);

    /**
     * 裁剪画布
     * 裁剪画布时利用clip系列函数,通过与rect,path,region取交,并,差等集合运算来获得最新的画布形状,除了调用save,restore函数以外,
     * 这个操作时不可逆的,一旦canvas画布被裁剪,就不能在回复
     */
    canvas.drawColor(Color.RED);
    canvas.clipRect(new RectF(300,300,400,400));
    canvas.drawColor(Color.GREEN);

    /**
     * 画布的保存与恢复
     * int save()
     * void restore()
     * save():每次调用save()函数,都会吧当前的画布的状态进行保存,然后存放到特定的栈中
     * restroe()每当调用restore()函数,就会把栈中最顶层的画布状态取出来,并按照这个状态恢复当前的画布,并在这个画布上作画
     */

    canvas.drawColor(Color.BLUE);
    canvas.save();
    canvas.clipRect(new RectF(300,400,500,600));
    canvas.drawColor(Color.GREEN);
    canvas.restore();
    /**
     * 注意,保存几次就能restore几次,最多可以恢复到第一次保存的状态
     */
    //canvas.restore();
    //canvas.restore();


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
