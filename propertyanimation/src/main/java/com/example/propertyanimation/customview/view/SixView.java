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
 * 创建时间   2017/12/7 11:19
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class SixView extends View {

  public SixView(Context context) {
    super(context);
  }

  public SixView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    /**
     * drawText
     * canvas 再利用drawtext绘制文字时,也是有规则的,这个规则是基线
     * 基线就是四线三格中的第三条线,也就是说,只要基线的位置定了,那文字的位置也必然是定了的
     *
     * Canvas.drawText()与基线
     * text:要绘制的文字
     * x:绘制原点的x坐标
     * y:绘制原点的y坐标
     * paint:用来绘制的画笔
     * public void drawText(String text,float x,float y,Paint paint)
     *
     * 注意:x.y坐在的点时基线的开始坐标
     * 一般而言(x,y)代表的位置是所画图形对应的矩形的左上角点,但在drawText中是非常例外的,y代表的是基线的位置
     * 一定要清除的是,只要x坐标,基线位置,文字大小确定以后,文字的位置就是确定的
     */
    int baseLinex=0;
    int baseLineY=200;
    //画基线
    Paint paint = new Paint();
    paint.setColor(Color.RED);
    canvas.drawLine(baseLinex,baseLineY,3000,baseLineY,paint);

    //写文字
    paint.setColor(Color.GREEN);
    paint.setTextSize(120);//以px为单位
    /**
     * 设置不同的值
     * 下面的不同位置是相对于(0,200)点来讲的,left表示在文字左边与此点对齐,right表示文字右边与此点对齐,center表示文字的中心与此点对齐
     */
    paint.setTextAlign(Paint.Align.CENTER);
    canvas.drawText("harvic\'s blog",baseLinex,baseLineY,paint);

    /**
     * paint.setTextAlign(Paint.Align.xxx)
     * 我们知道，我们在drawText(text, x, y, paint)中传进去的源点坐标(x,y);其中，y表示的基线的位置。那x代表什么呢？从上面的例子运行结果来看，应当是文字开始绘制的地方。
     并不是！x代表所要绘制文字所在矩形的相对位置。相对位置就是指指定点（x,y）在在所要绘制矩形的位置。我们知道所绘制矩形的纵坐标是由Y值来确定的，而相对x坐标的位置，
     只有左、中、右三个位置了。也就是所绘制矩形可能是在x坐标的左侧绘制，也有可能在x坐标的中间，也有可能在x坐标的右侧。而定义在x坐标在所绘制矩形相对位置的函数是：
     */

    /**
     * drawText的四格线与fontmetrics
     *
     * 除了基线以外，如上图所示，另外还有四条线，分别是ascent,descent,top,bottom，他们的意义分别是：
     ascent: 系统建议的，绘制单个字符时，字符应当的最高高度所在线
     descent:系统建议的，绘制单个字符时，字符应当的最低高度所在线
     top: 可绘制的最高高度所在线
     bottom: 可绘制的最低高度所在线

     fontMetrics

     以上四条线的y坐标都可以用fontmetrics计算出来
     计算方法为:
     他们的意义与值的计算方法分别如下：

     ascent = ascent线的y坐标 - baseline线的y坐标；
     descent = descent线的y坐标 - baseline线的y坐标；
     top = top线的y坐标 - baseline线的y坐标；
     bottom = bottom线的y坐标 - baseline线的y坐标；
     so可以推出:
     ascent线Y坐标 = baseline线的y坐标 + fontMetric.ascent；
     descent线Y坐标 = baseline线的y坐标 + fontMetric.descent；
     top线Y坐标 = baseline线的y坐标 + fontMetric.top；
     bottom线Y坐标 = baseline线的y坐标 + fontMetric.bottom；

     (3) 获取fontMetrics对象是根据paint对象获取的
     */

    Paint paint1=new Paint();
    //获取的是float类型的
    Paint.FontMetrics fm=paint1.getFontMetrics();
    //获取的是Int类型的
    Paint.FontMetricsInt fmInt=paint1.getFontMetricsInt();

    //计算各线所在位置
    float ascent=baseLineY+fm.ascent;
    float dscent=baseLineY+fm.descent;
    float top=baseLineY+fm.top;
    float bottom=baseLineY+fm.bottom;

    //画ascent
    paint1.setColor(Color.BLUE);
    canvas.drawLine(baseLinex,ascent,3000,ascent,paint1);

    //画top
    paint1.setColor(Color.YELLOW);
    canvas.drawLine(baseLinex,top,3000,top,paint1);

    //画descent
    paint1.setColor(Color.GREEN);
    canvas.drawLine(baseLinex,dscent,3000,dscent,paint1);

    //画bottom
    paint1.setColor(Color.RED);
    canvas.drawLine(baseLinex,bottom,3000,bottom,paint1);

    /**
     * 所绘文字宽度,高度,和最小矩形获取
     *
     * 高度
     * 字符串所占高度很容易获取,直接用bottom线所在位置的y坐标减去top线坐在位置的y坐标就是字符串所占的高度
     */

    //所占高度
    float height=bottom-top;
    //所占宽度
    float width=paint1.measureText("xxxxx");
    //最小矩形
    /**
     *
     * 获取指定字符串所对应的最小矩形，以（0，0）点所在位置为基线
     * @param text  要测量最小矩形的字符串
     * @param start 要测量起始字符在字符串中的索引
     * @param end   所要测量的字符的长度
     * @param bounds 接收测量结果
     * public void getTextBounds(String text,int start ,int end,Rect bounds)
     */


    Paint paint2 = new Paint();
    paint2.setColor(Color.BLUE);
    paint2.setAntiAlias(true);
    paint2.setStyle(Paint.Style.FILL);
    paint2.setStrokeWidth(5);
    paint2.setTextSize(120);

    int baselinex=0;
    int baseliney=400;

    paint2.setTextAlign(Paint.Align.LEFT);

    String text = "xxxxxxxxxxxxxx";
    //paint.getTextBounds(text,0,text.length(),rect);
    //Log.e("qijian",rect.toShortString());

    //画出text所占区域
    Paint.FontMetricsInt fms = paint2.getFontMetricsInt();
    canvas.drawLine(baseLinex,baseliney,3000,baseliney,paint2);

    int tops=fms.top+baseliney;
    int bottoms=fms.bottom+baseliney;
    int widths= (int) paint2.measureText(text);
    Rect rect = new Rect(baselinex,tops,baselinex+widths,bottoms);

    paint2.setColor(Color.GREEN);
    canvas.drawRect(rect,paint2);

    //画出最小矩形
    Rect minrect = new Rect();
    paint2.getTextBounds(text,0,text.length(),minrect);
    minrect.top=baseliney+minrect.top;
    minrect.bottom=baseliney+minrect.bottom;
    paint2.setColor(Color.RED);
    canvas.drawRect(minrect,paint2);

    //写文字
    paint2.setColor(Color.BLUE);
    canvas.drawText(text,baselinex,baseliney,paint2);

    /**
     * 定点写字
     * 这部分,我们假定给出如要绘制矩形 的左上角定点作坐标,然后画出这个文字,
     */

    int topx=700;
    int baselines=0;

    //画top
    paint2.setColor(Color.CYAN);
    canvas.drawLine(baselines,top,3000,top,paint2);
    //计算出baseline位置
    Paint.FontMetricsInt fmss = paint2.getFontMetricsInt();
    int baselinesY= (int) (top-fmss.top);

    //画基线
    paint2.setColor(Color.RED);
    canvas.drawLine(baselines,baselinesY,3000,baselinesY,paint2);

    //写文字
    paint2.setColor(Color.BLACK);
    canvas.drawText(text,baselines,baselinesY,paint2);
  }
}
