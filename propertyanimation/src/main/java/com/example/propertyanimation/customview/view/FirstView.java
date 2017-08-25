package com.example.propertyanimation.customview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.propertyanimation.customview.util.MeasureUtil;

/**
 * 创建者     李文东
 * 创建时间   2017/8/25 9:30
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

/**
 * draw或layout的过程有可能是一个频繁重复执行的过程，我们知道new是需要分配内存空间的，
 * 如果在一个频繁重复的过程中去大量地new对象内存爆不爆我不知道，但是浪费内存那是肯定的！
 * 所以Android不建议我们在这两个过程中去实例化对象
 */
public class FirstView extends View implements Runnable{
  //画笔
  private Paint mpaint;
  //圆环半径
  private int radiu;
  public FirstView(Context context) {
    super(context);
  }

  public FirstView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initPaint();
  }

  public FirstView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void initPaint() {

     /*
     * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
     *
     * 画笔样式分三种：
     * 1.Paint.Style.STROKE：描边
     * 2.Paint.Style.FILL_AND_STROKE：描边并填充
     * 3.Paint.Style.FILL：填充
     */

    //画笔
    mpaint = new Paint();
    //抗锯齿
    mpaint.setAntiAlias(true);
    mpaint.setStyle(Paint.Style.STROKE);
    //设置画笔颜色
    mpaint.setColor(Color.LTGRAY);
    /**
     *  设置描边的粗细,单位:像素px
     *  注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
     */

    mpaint.setStrokeWidth(5);
  }



  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //绘制圆环
    canvas.drawCircle(MeasureUtil.getScreenSize((Activity) getContext())[0]/2,MeasureUtil.getScreenSize(
        (Activity) getContext())[1]/2,radiu,mpaint);

  }

  public synchronized void setRadiu(int radiu){
    this.radiu=radiu;
    //重绘
    invalidate();
  }

  //减轻fragment中逻辑处理
  @Override public void run() {
    //确保线程不断的刷新
    while (true) {
      try {
        if (radiu <= 200) {
          radiu += 10;

         //刷新view
          //invalidate();
          /**
           * 我们在非UI线程中更新了UI！
           * 而在Android中非UI线程是不能直接更新UI的，怎么办？
           * 用Handler？NO！Android给我们提供了一个更便捷的方法：postInvalidate()；
           * 用它替代我们原来的invalidate()即可：
           */

          postInvalidate();
        }else {
          radiu=0;
        }
        //每执行一次暂停40毫秒
        Thread.sleep(40);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }

}
