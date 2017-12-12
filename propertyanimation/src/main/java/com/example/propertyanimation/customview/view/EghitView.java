package com.example.propertyanimation.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
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

    /**
     * DashPathEffect----虚线效果
     * public DashPathEffect(float intervals[],float phase)
     *
     * intervals[]:表示组成虚线的各个线段的长度,整条虚线就是由intervals[]中这些基本线段循环组成的,比如,我们定义new float[]{20,10}
     * 那这个虚线段就是由两段线段组成的,第一个可见的线段长为20,每个线段不可见长度为10
     *
     * 对于intervals[]数组的有两个限定：

     长度必须大于等于2；因为必须有一个实线段和一个空线段来组成虚线。
     个数必须为偶数，如果是基数，最后一个数字将被忽略；这个很好理解，因为一组虚线的组成必然是一个实线和一个空线成对组成的。

     phase:开始绘制的偏移值
     */
    Path path1= new Path();
    path1.moveTo(100,600);
    path1.lineTo(400,100);
    path1.lineTo(700,900);
    canvas.drawPath(path1,paint);

    paint.setColor(Color.BLACK);

    //使用dashpatheffect画线段
    paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},0));
    canvas.translate(0,100);
    canvas.drawPath(path1,paint);

    //画同一条线段,偏移量为15
    paint.setPathEffect(new DashPathEffect(new float[]{20,10,50,100},15));
    paint.setColor(Color.BLUE);
    canvas.translate(1,100);
    canvas.drawPath(path1,paint);

    /**
     * DiscretePathEffect----离散路径效果
     * DiscretePathEffect就是将原来路径分隔成定长的线段，然后将每条线段随机偏移一段位置，我们可以用它来模拟一种类似生锈铁丝的效果；
     * public DiscretePathEffect(float segmentLength, float deviation)
     *
     * 第一个参数segmentLength：表示将原来的路径切成多长的线段。如果值为2，那么这个路径就会被切成一段段由长度为2的小线段。所以这个值越小，
     * 所切成的小线段越多；这个值越大，所切成的小线段越少。
     第二参数deviation：表示被切成的每个小线段的可偏移距离。值越大，就表示每个线段的可偏移距离就越大，就显得越凌乱，值越小，每个线段的可偏移原位置的距离就越小。
     */
    paint.setColor(Color.CYAN);
    Path path2 = getPath();

    //第一条原生path
    canvas.drawPath(path2,paint);
    //第二条线ptah
    canvas.translate(0,200);
    paint.setPathEffect(new DiscretePathEffect(2,5));
    canvas.drawPath(path2,paint);
    //第三条path
    canvas.translate(0,200);
    paint.setPathEffect(new DiscretePathEffect(6,5));
    canvas.drawPath(path2,paint);

    //第四条直线
    canvas.translate(0,200);
    paint.setPathEffect(new DiscretePathEffect(6,15));
    canvas.drawPath(path2,paint);

    /**
     * PathDashPathEffect---印章路径效果
     *
     * public PathDashPathEffect(Path shape, float advance, float phase,Style style)
     *
     *     Path shape:表示印章路径，比如我们下面示例中的三角形加右上角一个点；
     float advance：表示两个印章路径间的距离,很容易理解，印章间距离越大，间距就越大。
     float phase：路径绘制偏移距离，与上面DashPathEffect中的float phase参数意义相同
     Style style：表示在遇到转角时，如何操作印章以使转角平滑过渡，
     取值有：Style.ROTATE，Style.MORPH，Style.TRANSLATE;Style.ROTATE表示通过旋转印章来过渡转角；
     Style.MORPH表示通过变形印章来过渡转角；Style.TRANSLATE表示通过位移来过渡转角。这三个效果的具体意义，上面会通过具体示例来分别讲解。
     */

    paint.setColor(Color.RED);
    Path path3 = new Path();
    path3.moveTo(100,600);
    path3.lineTo(400,100);
    path3.lineTo(700,900);

    canvas.drawPath(path3,paint);

    //构建印章路径
    Path stampPath=new Path();
    stampPath.moveTo(0,20);
    stampPath.lineTo(10,0);
    stampPath.lineTo(20,20);
    stampPath.close();
    stampPath.addCircle(0,0,3,Path.Direction.CCW);

    //使用印章路径效果
    canvas.translate(0,200);
    paint.setPathEffect(new PathDashPathEffect(stampPath,35,0,PathDashPathEffect.Style.TRANSLATE));

    /**
     * 、ComposePathEffect与SumPathEffect
     * 这两个都是用来合并两个特效的。但它们之间是有区别的：
     * ComposePathEffect合并两个特效是有先后顺序的，它会先将第二个参数的PathEffect innerpe的特效作用于路径上，然后再在此加了特效的路径上作用第二个特效。
     public SumPathEffect(PathEffect first, PathEffect second)
     * 而SumPathEffect是分别对原始路径分别作用第一个特效和第二个特效。然后再将这两条路径合并，做为最终结果。
     */

    //画原始路径
    paint.setColor(Color.GREEN);
    Path path4 = getPath();
    canvas.drawPath(path4,paint);

    //仅应用圆角特效的路径
    canvas.translate(0,200);
    CornerPathEffect cornerPathEffect = new CornerPathEffect(100);
    paint.setPathEffect(cornerPathEffect);
    canvas.drawPath(path4,paint);

    //仅应用虚线特效的路径
    canvas.translate(0,200);
    DashPathEffect dashPathEffect = new DashPathEffect(new float[]{2,5,10,10},0);
    paint.setPathEffect(dashPathEffect);
    canvas.drawPath(path4,paint);

    //利用ComposePathEffect先应用圆角特效,再应用虚线特效
    canvas.translate(0,200);
    ComposePathEffect composePathEffect = new ComposePathEffect(dashPathEffect,cornerPathEffect);
    paint.setPathEffect(composePathEffect);
    canvas.drawPath(path4,paint);

    //利用SumPathEffect,分别将圆角特效应用于原始路径,然后将生成的两条特效路径合并
    canvas.translate(0,200);
    paint.setStyle(Paint.Style.STROKE);
    SumPathEffect sumPathEffect = new SumPathEffect(cornerPathEffect,dashPathEffect);
    paint.setPathEffect(sumPathEffect);
    canvas.drawPath(path,paint);

    /**
     * 字体大小
     * setTextSize(float textSize)
     设置文字大小
     setFakeBoldText(boolean fakeBoldText)
     设置是否为粗体文字
     setStrikeThruText(boolean strikeThruText)
     设置带有删除线效果
     setUnderlineText(boolean underlineText)
     设置下划线
     setTextAlign(Paint.Align align)
     设置开始绘图点位置
     setTextScaleX(float scaleX)
     水平拉伸设置
     setTextSkewX(float skewX)
     设置字体水平倾斜度，普通斜体字是-0.25，可见往右斜
     setTypeface(Typeface typeface)
     字体样式


     1,setLinearText(boolean linearText)

     设置是否打开线性文本标识；由于文本想要快速绘制出来，必然是需要提前缓存在显存中的，一般而言每个文字需要一个字节的大小来存储它（当然具体需要多少字节与编码方式有关），
     那如果是长篇文章，可见所需的大小可想而知。我们可以通过setLinearText (true)告诉Android我们不需要这样的文本缓存。但如果我们不用文本缓存，虽然能够省去一些内存空间，
     但这是以显示速度为代价的。
     由于这个是API 1的函数，由于当时的android手机的内存大小还是很小的，所以尽量减少内存使用是每个应用的头等大事，在当时的的环境下这个函数还是很有用的。
     但在今天，内存动不动就是4G以上了，文本缓存的所占的那点内存就微不足道了，没有哪个APP会牺牲性能来减少这点这内存占用了，所以这个函数基本没用了。
     *
     * 2、setSubpixelText(boolean subpixelText)
     * 表示是否打开亚像素设置来绘制文本。亚像素的概念比较难理解，首先，我们都知道像素，比如一个android手机的分辨率是1280*720，那就是指它的屏幕在垂直方向有1280个像素点，
     * 水平方向上有720个像素点。我们知道每个像素点都是一个独立显示一个颜色的个体。所以如果一副图片，在一个屏幕上用了300*100个相素点，而在另一个屏幕上却用了450*150个像素来显示。
     * 那么，请问在哪个屏幕上这张图片显示的更清晰？当然是第二个屏幕，因为它使用的像素点更多，所显示的细节更精细。
     那么问题来了，android设置在出厂时，设定的像素显示都是固定的几个范围：320*480，480*800，720*1280，1080*1920等等；那么如何在同样的分辨率的显示器中增强显示清晰度呢？
     亚像素的概念就油然而生了，亚像素就是把两个相邻的两个像素之间的距离再细分，再插入一些像素，这些通过程序加入的像素就是亚像素。在两个像素间插入的像素个数是通过程序计算出来的，
     一般是插入两个、三个或四个。所以打开亚像素显示，是可以在增强文本显示清晰度的，但由于插入亚像素是通过程序计算而来的，所以会耗费一定的计算机性能。注意：
     亚像素是通过程序计算出来模拟插入的，在没有改变硬件构造的情况下，来改善屏幕分辨率大小。
     亚像素显示，是仅在液晶显示器上使用的一种增强字体清晰度的技术。但这种技术有时会出现问题，用投影仪投射到白色墙壁上，会出出字体显示不正常的情况，而且对于老式的CRT显示器是根本不支持的。
     在android还没有出现时，windows已经能够支持亚像素显示了，在windows机器中，这个功能叫做ClearType，在以前讲述windows的GDI绘图时，也曾经讲过ClearType的应用效果。
     */
    Paint paint2 = new Paint();
    paint2.setColor(Color.GREEN);
    String text = "乌龟&梦想";
    paint2.setTextSize(200);

    paint.setSubpixelText(false);
    canvas.drawText(text,0,200,paint);

    canvas.translate(0,300);
    paint.setSubpixelText(true);
    canvas.drawText(text,0,200,paint);

    /**
     * 图片处理
     * setShader(Shader shader)
     setShadowLayer(float radius, float dx, float dy, int shadowColor)
     setDither(boolean dither)
     setColorFilter(ColorFilter filter)
     setXfermode(Xfermode xfermode)
     setFilterBitmap(boolean filter)
     clearShadowLayer()
     */
    /**
     * Measure测量相关
     * breakText(char[] text, int index, int count, float maxWidth, float[] measuredWidth)
     measureText(String text)
     */

  }

  private Path getPath(){
    Path path = new Path();
    // 定义路径的起点
    path.moveTo(0, 0);

    // 定义路径的各个点
    for (int i = 0; i <= 40; i++) {
      path.lineTo(i*35, (float) (Math.random() * 150));
    }
    return path;
  }
}
