package com.example.propertyanimation.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 创建者     李文东
 * 创建时间   2017/12/9 11:59
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class SevenView extends View {

  private Path mpath = new Path();
  private int mItemWavelength=1000;
  public SevenView(Context context) {
    super(context);
  }

  public SevenView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SevenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    /**
     * 二级贝塞尔曲线
     * public void quadTo(float x1,float y1,float x2,float y2)
     * public void rQuadTo(float x1,float y1,float x2,float y2)
     * 三阶贝塞尔曲线
     * public void cubicto(float x1,float y1,float x2,float y2)
     * public void rCubicTo(float x1,float y1,float x2,float y2)
     *
     * 贝塞尔曲线的来源:
     * 在数学的数值分析领域中，贝赛尔曲线（Bézier曲线）是电脑图形学中相当重要的参数曲线。更高维度的广泛化贝塞尔曲线就称作贝塞尔曲面，其中贝塞尔三角是一种特殊的实例。
     贝塞尔曲线于1962年，由法国工程师皮埃尔·贝塞尔（Pierre Bézier）所广泛发表，他运用贝塞尔曲线来为汽车的主体进行设计。
     贝塞尔曲线最初由Paul de Casteljau于1959年运用de Casteljau算法开发，以稳定数值的方法求出贝塞尔曲线。

     贝塞尔曲线公式

     其中(x1,y1)是控制点坐标,(x2,y2)是终点坐标`
     */
    Paint paint = new Paint();
    paint.setColor(Color.GREEN);
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);
    paint.setStrokeWidth(5);

    Path path = new Path();
    path.moveTo(100, 100);
    path.quadTo(200, 200, 300, 300);
    path.quadTo(400, 400, 500, 500);

    canvas.drawPath(path, paint);

    /**
     * 手指轨迹
     *
     *
     * 1实现方式1:Path.lineTo(x,y)
     *
     *缺点
     * 利用Path绘图，是不可能出现马赛克的，因为除了Bitmap以外的任何canvas绘图全部都是矢量图，
     * 也就是利用数学公式来作出来的图，无论放在多大屏幕上，都不可能会出现马赛克！这里利用Path绘图，
     * 在S顶部之所以看起来像是马赛克是因为这个S是由各个不同点之间连线写出来的，而之间并没有平滑过渡，所以当坐标变化比较剧烈时，线与线之间的转折就显得特别明显了。
     所以要想优化这种效果，就得实现线与线之间的平滑过渡，很显然，二阶贝赛尔曲线就是干这个事的。下面我们就利用我们新学的Path.quadTo函数来重新实现下移动轨迹效果。
     *
     */

    paint.setColor(Color.RED);
    paint.setStyle(Paint.Style.STROKE);
    canvas.drawPath(mpath, paint);

    /**
     * 2实现方式2:path.quadto()函数实现过度
     *
     * 因为path.lineto()最大问题是线段转折处不够平滑,path.quadto()实现可以平滑过度,但使用path.quadTo()的最大问题是
     * 如何找到起始点和结束点
     *
     */

    paint.setColor(Color.GREEN);
    canvas.drawPath(mpath,paint);

    /**
     * Path.rQuadTo() 画水波效果
     *
     * public void rQuadTo(float dx1,float dy1,float dx2,float dy2)
     *
     * dx1:控制点x的坐标,表示点对与上一个终点x坐标的位移坐标,可为负值,正值表示相加,负值表示相减
     *
     * dy1:控制点y坐标,相对于上一个终点y坐标的位移坐标,同样可为负值,正值表示相加,负值表示相减
     *
     * dx2:终点x坐标,同样是一个相对坐标,相对于上一个终点x坐标的位移值,可为负值,正值表示相加,负值表示相减
     *
     * dy2:终点y坐标,同样是一个相对值,相对于上一个终点y坐标的位移值,可为负值,正值表示相加,负值表示相减
     *
     * 这四个参数都是传递的都是相对值，相对上一个终点的位移值。
     比如，我们上一个终点坐标是(300,400)那么利用rQuadTo(100,-100,200,100)；
     得到的控制点坐标是（300+100,400-100）即(500,300)
     同样，得到的终点坐标是(300+200,400+100)即(500,500)
     所以下面这两段代码是等价的：
     利用quadTo定义绝对坐标
     */

    paint.setColor(Color.YELLOW);
    paint.setStyle(Paint.Style.STROKE);

    Path path1=new Path();
    path1.moveTo(100,300);
    path1.rQuadTo(100,-100,200,0);

    path1.rQuadTo(100,100,200,0);
    canvas.drawPath(path1,paint);

    /**
     * 实现波浪效果
     */

    paint.setColor(Color.RED);
    paint.setStyle(Paint.Style.FILL);
    mpath.reset();
    int originY=300;
    int halfWavele=mItemWavelength/2;
    mpath.moveTo(-mItemWavelength+dx,originY+dx-400);

    for (int i = -mItemWavelength; i <getWidth()+mItemWavelength ; i+=mItemWavelength) {
        mpath.rQuadTo(halfWavele/2,-100,halfWavele,0);
        mpath.rQuadTo(halfWavele/2,100,halfWavele,0);
    }

    mpath.lineTo(getWidth(),getHeight());
    System.out.println("宽度==="+getWidth()+"====长度==="+getHeight());
    mpath.lineTo(0,getHeight());
    mpath.close();

    canvas.drawPath(mpath,paint);
  }

  public void reset() {
    mpath.reset();
    invalidate();
  }
  /**
   * 第一种方式
   */
  //@Override public boolean onTouchEvent(MotionEvent event) {
  //
  //  switch (event.getAction()) {
  //    case MotionEvent.ACTION_DOWN:
  //      mpath.moveTo(event.getX(), event.getY());
  //      /**
  //       * return true表示当前控件已经消费了下按动作,之后move,up动作也会继续传递到当前控件中,如果我们在down时返回false,那么后续的move
  //       * 和up动作就不会再传到这个控件中老大
  //       */
  //      return true;
  //    case MotionEvent.ACTION_MOVE:
  //      mpath.lineTo(event.getX(), event.getY());
  //      /**
  //       * 重绘使用的是postinvalidata(),而我们以前使用的是invalidata()函数
  //       * 这两个函数的作用都是用来重绘控件的但区别是invalidata()一定要在ui线程中执行,如果不在ui线程中执行就会报错
  //       * 而postinvalidata()则没有那么多讲究,他可以在任何线程中执行,而不必一定在主线程中,其实在postvalidata()就是利用handler
  //       * 给主线程发送刷新消息来实现的,所以它是可以在任何线程中执行的而不会报错,而正是因为他是通过发消息来实现的,所以他的界面
  //       * 刷新可能没有直接调用invalidata()刷新name快
  //       */
  //      postInvalidate();
  //      bringToFront();
  //    default:
  //      break;
  //  }
  //  return super.onTouchEvent(event);
  //}

  /**
   * 第二种方式
   */
  private float mprex,mprey;
  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()){
      case MotionEvent.ACTION_DOWN:
        mpath.moveTo(event.getX(),event.getY());
        mprex=event.getX();
        mprey=event.getY();
        return true;
      case MotionEvent.ACTION_MOVE: {
        float endX = (mprex + event.getX()) / 2;
        float endy = (mprey + event.getY()) / 2;

        mpath.quadTo(mprex, mprey, endX, endy);
        mprex = event.getX();
        mprey = event.getY();
        invalidate();
      }
      break;
      default:
        break;
    }
    return super.onTouchEvent(event);
  }

  /**
   * 实现移动动画
   * 让波纹动起来其实挺简单，利用调用在path.moveTo的时候，将起始点向右移动即可实现移动，而且只要我们移动一个波长的长度，波纹就会重合，就可以实现无限循环了。
   */
  private int dx;
  public void startAnim(){
    final ValueAnimator animator = ValueAnimator.ofInt(0,mItemWavelength);
    animator.setDuration(2000);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setInterpolator(new LinearInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
        dx= (int) animator.getAnimatedValue();
        postInvalidate();
      }
    });
    animator.start();
  }
}
