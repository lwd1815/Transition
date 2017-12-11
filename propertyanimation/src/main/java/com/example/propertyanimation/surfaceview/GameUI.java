package com.example.propertyanimation.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 创建者     李文东
 * 创建时间   2017/12/11 16:29
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class GameUI extends SurfaceView implements SurfaceHolder.Callback {
  /**
   * surfaceview就是带有surface的view,它是一个view,是view的子类,所以和其他的view一样
   * 可以在屏幕上展示东西接收用户输入,具有view的生命周期回调函数,如OnMeasure,onlayout,ondraw,ontouchEvent等
   *
   * surfaceview带有独立的surface,这可以让子线程在独立的surface上绘制东西,进行surfaceview的界面绘制,这个子线程就叫做渲染线程
   * ,但是要让独立的surface上面的东西在view上面展示出来,需要post一个消息给主线程,目的是吧该surface中canvas上的东西绘制到view
   * 的真正的画布上面,这样就可以吧ui线程空闲出来处理用户的交互
   *
   *
   * surfaceview是视图view的继承类,这个视图里内嵌了一个专门用于绘制的surface,你可以控制这个surface的格式和尺寸,surfaceview控制这个surface的绘制位置
   *
   * surfaceview的使用步骤
   *
   * 1 获取到surfaceview对应的surfaceholder,给surfaceholder添加一个surfaceholder.callback对象
   * 2 创建渲染线程对象
   * 3在子线程中开始在surface上面绘制图形,因为surfaceview没有对我们暴露surfacce,而只是暴露了surface的包装器surfaceholder,所以用surfaceholder的lockcanvas()
   * 获取surface上面指定区域的canvas,在该canvas上绘制图形,绘制结束后,使用surfaceholder的unlockcanvasandpost()方法解锁canvas,并且让ui线程吧surface上面的东西绘制到
   * view的Canvas上面
   */

  private SurfaceHolder holder;
  private RenderThread renderThread;
  private boolean isDraw=false;//控制绘制的开关
  public GameUI(Context context) {
    super(context);
    holder=this.getHolder();
    holder.addCallback(this);
    renderThread=new RenderThread();
    //设置可以悬浮在其他view上面
    this.setZOrderOnTop(true);
    //设置悬浮为透明,否则会遮盖下面的view
    this.getHolder().setFormat(PixelFormat.TRANSPARENT);
  }

  public GameUI(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public GameUI(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void surfaceCreated(SurfaceHolder surfaceHolder) {
      isDraw=true;
      renderThread.start();
  }

  @Override public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
  }

  @Override public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    isDraw=false;
  }

  /**
   * 绘制界面的线程
   */

  private class RenderThread extends Thread{
    @Override public void run() {
      //不停绘制界面
      while(isDraw){
        drawUI();
      }
      super.run();
    }
  }

  /**
   * 界面绘制
   */
  private void drawUI() {

    Canvas canvas=holder.lockCanvas();
    try {
      drawCanvas(canvas);
    }catch (Exception e){
      e.printStackTrace();
    }finally {
      holder.unlockCanvasAndPost(canvas);
    }

  }

  private void drawCanvas(Canvas canvas) {
    //在canvas上绘制需要的图形
    Paint paint = new Paint();
    paint.setColor(Color.RED);
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);
    paint.setStrokeWidth(5);

    canvas.drawCircle(200,200,200,paint);
  }
}
