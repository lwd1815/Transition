package com.example.propertyanimation.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.propertyanimation.R;

/**
 * 创建者     李文东
 * 创建时间   2017/12/27 11:36
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class CustomButton extends View{
  private String[] letterArr = {"A", "B", "C", "D", "E", "F", "G", "H",
      "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
      "V", "W", "X", "Y", "Z"};
  Paint paint;
  int ColorDefault= Color.WHITE;
  int ColorPressed=Color.BLACK;
  public CustomButton(Context context) {
    this(context,null);
  }

  public CustomButton(Context context, @Nullable AttributeSet attrs) {
    this(context,attrs,0);
  }

  public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(ColorDefault);

    int size=getResources().getDimensionPixelSize(R.dimen.paint_size);
    paint.setTextSize(size);

    //文字绘制的起点默认是左下角,设置起点为文字底边的中心,baseline基准线
    paint.setTextAlign(Paint.Align.CENTER);
  }
  //一个格子的高
  float cellHeight;
  float x;

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    cellHeight=getMeasuredHeight()*1f/letterArr.length;
    x=getMeasuredWidth()/2;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    //遍历26个字母,对每个字母进行绘制
    for (int i = 0; i < letterArr.length; i++) {
      String text=letterArr[i];
      //算法:格子高的一半+文字高的一半+i*格子的高
      float y=cellHeight/2+getTextHeight(text)/2+i*cellHeight;
      //更改颜色
      paint.setColor(i== index?ColorPressed:ColorDefault);
      canvas.drawText(text,x,y,paint);
    }
  }
  int index=-1;
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
        int tempIndex = (int) (event.getY() / cellHeight);
        if(tempIndex!=index){
          index = tempIndex;

          //对index进行合法性的判断
          if(index>=0 && index<letterArr.length){
            String letter = letterArr[index];

            if(listener!=null){
              listener.onLetterChange(letter);
            }
          }
        }
        break;
      case MotionEvent.ACTION_UP:
        //重置为-1
        index = -1;
        if(listener!=null){
          listener.onRelease();
        }

        break;
    }

    //重绘
    invalidate();

    return true;
  }
  /**
   * 获取文字的高度
   *
   * @param text
   * @return
   */
  private int getTextHeight(String text) {
    Rect bounds = new Rect();
    //当下面的方法执行完，bounds就有值了
    paint.getTextBounds(text, 0, text.length(), bounds);
    return bounds.height();
  }

  private OnLetterChangeListener listener;
  public void setOnLetterChangeListener(OnLetterChangeListener listener){
    this.listener = listener;
  }

  public interface OnLetterChangeListener{
    void onLetterChange(String letter);

    /**
     * 抬起的时候执行
     */
    void onRelease();
  }
}
