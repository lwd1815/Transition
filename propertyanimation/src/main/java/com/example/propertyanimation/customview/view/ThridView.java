package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.propertyanimation.BaseApplication;

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
    //设置尺寸大小
    textpaint.setTextSize(80);
    //其他设置
    //只会将水平方向拉伸,高度不变
    textpaint.setTextScaleX(2);

    canvas.drawText("杨柳依依",10,610,textpaint);

    textpaint.setColor(Color.GREEN);
    textpaint.setStyle(Paint.Style.FILL_AND_STROKE);
    canvas.drawText("杨柳依依",10,700,textpaint);

    Paint textpaints=new Paint();
    textpaints.setStrokeWidth(3);
    textpaints.setAntiAlias(true);
    textpaints.setTextScaleX(2);
    textpaints.setTextSize(80);
    textpaints.setColor(Color.RED);
    textpaints.setStyle(Paint.Style.STROKE);
    textpaint.setTextSkewX((float) 0.25);
    textpaint.setColor(Color.GRAY);
    textpaint.setStyle(Paint.Style.STROKE);
    canvas.drawText("杨柳依依",10,800,textpaint);

    canvas.drawText("我就不信",10,900,textpaint);

    //画个画布,将以前的都遮住
    //canvas.drawColor(Color.GRAY);
    textpaint.setColor(Color.YELLOW);
    canvas.drawText("我就是我",200,300,textPaint);

    /**
     * 指定文字位置
     *
     * drawPosText(char[]text,int index,int count,float[]pos,Paint paint)
     * drawPosText(String text,float[]pos,Paint paint)
     *
     * 第一个构造函数:实现截取一分部分文字绘制
     *
     * 参数说明
     * char[]text:要绘制的文字的数组
     * int Index 第一个要绘制的文字的索引
     * int count 要绘制的文字的个数,用来算最后一个文字的`位置,要第一个绘制的文字开始算起
     * float[]pos 每个字体的位置,同样两两一组
     */

    Paint spaPaint = new Paint();
    spaPaint.setColor(Color.RED);

    spaPaint.setAntiAlias(true);
    spaPaint.setStrokeWidth(3);
    spaPaint.setStyle(Paint.Style.FILL);
    spaPaint.setTextSize(30);
    float[]pos = new float[]{
        80,100,80,200,80,300,80,400
    };

    canvas.drawPosText("你是傻逼",pos,spaPaint);

    /**(
     * 沿路径绘制
     * drawTextOnpath(String text,Path path,float hoffset,float vOffset,Paint paint)
     * drawTextOnPath(char[]text,int index,int count,Path path,float hOffset,float vOffset,Paint paint)
     *
     * 有关截取部分字体绘制相关参数(index,count),
     *
     * float hOffset :与路径起始点的水平偏移距离
     * float vOffset :与路径中心的垂直偏移量
     */

    Paint sPaint = new Paint();
    sPaint.setColor(Color.GREEN);

    sPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    sPaint.setStrokeWidth(5);
    sPaint.setAntiAlias(true);
    sPaint.setTextSize(50);

    String string = "风萧萧兮易水寒";

    //先创建两个相同的圆形路径,并先画出两个路径的原图

    Path circlesPath = new Path();
    circlesPath.addCircle(220,180,100, Path.Direction.CCW);
    canvas.drawPath(circlesPath,sPaint);//绘制出路径原形

    Path circles2Path = new Path();
    circles2Path.addCircle(500,500,100, Path.Direction.CCW);
    canvas.drawPath(circles2Path,sPaint);//绘制出路径原形

    sPaint.setColor(Color.RED);
    canvas.drawTextOnPath(string,circlesPath,0,0,sPaint);

    sPaint.setColor(Color.GRAY);
    canvas.drawTextOnPath(string,circles2Path,80,80,sPaint);

    /**
     * 字体样式设置
     * setTypeFace(typeface)
     *
     * 概述:typeface是专门用来设置字体样式的,通过paint.settypeface()来指定,可以指定系统中的字体样式
     * 也可以指定自定义的样式文件获取,要构建tpeface时,可以指定所用样式的正常体,斜体,粗体等,如果指定样式
     * 中,没有相关文字的样式,就会用系统默认的样式来显示,一般的默认样式是宋体
     *
     * //创建typeface
     *
     * typeface creat(String familyName,int style)//直接通过指定字体名来加载系统中自带的文字样式
     * typeface creat(typeface family,int style)//通过其他的typeface变量来构建文字样式
     * typeface creatFromAsset(AssetManager mgr,String path)//通过从Asset中获取外部字体来显示字体样式
     * typeface creatFromFile(File path)//从外部路径来创建文字样式
     * typeface defaultFromStyle(int style)//创建默认字体
     *
     *
     * Style 是一个变量,style的枚举如下
     *
     *Typeface.NORMAL //正常体
     * Typeface.BOLD //粗体
     * typeface.ITALIC//斜体
     * typeface.BOLD_ITALIC//粗斜体
     *
     *
     * //使用系统自带的文字样式有两种方法
     *
     * Typeface defaultFromStyle(int style)//创建默认字体
     * Typeface creat(String familyName,int style)//直接通过指定字体名来加载系统中自带的文字样式
     *
     * //其中第一个仅仅是使用系统默认的样式来绘制字体,基本没有可指定性,第二个可以指定样式
     */

    //使用系统自带字体绘制

    Paint textspaint = new Paint();
    textspaint.setColor(Color.YELLOW);
    textspaint.setStyle(Paint.Style.FILL_AND_STROKE);
    textspaint.setAntiAlias(true);
    textspaint.setStrokeWidth(3);

    textspaint.setTextSize(30);
    String familyName="宋体";
    Typeface font = Typeface.create(familyName, Typeface.NORMAL);
    textspaint.setTypeface(font);
    canvas.drawText("楚留香",500,100,textspaint);

    String s = "黑体";
    textspaint.setColor(Color.BLACK);
    Typeface fo = Typeface.create(s,Typeface.NORMAL);
    textspaint.setTypeface(fo);
    canvas.drawText("赵子龙",500,200,textspaint);

    /**
     * 自定义字体
     * 自定义字体需要从外部字体文件加载我们需要的字体,从外部文件加载字形所使用的typeface构造函数
     * typeFace createFromAsset(AssetManager mgr,String path)//通过从Asset中获取外部字体来显示字体样式
     * Typeface createFromFile(String path) //直接从路径创建
     * Typeface createFromFile(File path) //从外部路径来创建样式
     *
     * 一般用到`第一个
     * 首先在Asset下新建一个文件夹,命名为fonts,然后将字体文件jian_luobo.ttf放入其中
     */
    //自定义字体 迷你

    Paint customPaint = new Paint();
    customPaint.setColor(Color.RED);
    customPaint.setStrokeWidth(3);
    customPaint.setAntiAlias(true);
    customPaint.setStyle(Paint.Style.FILL);

    customPaint.setTextSize(60);
    AssetManager mgr = BaseApplication.mContext.getAssets();
    Typeface fromAsset = Typeface.createFromAsset(mgr, "fonts/jian_luobo.ttf");
    customPaint.setTypeface(fromAsset);
    canvas.drawText("hello WORLD",10,700, customPaint);
  }
}
