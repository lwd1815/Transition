package com.example.propertyanimation.airplaneview.logic;

import android.graphics.Bitmap;

/**
 * 创建者     李文东
 * 创建时间   2017/12/15 12:34
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

class Sprite {
  private boolean visible=true;
  private float x=0;
  private float y=0;
  //撞击偏移量
  private float collideOffset=0;
  private Bitmap bitmap=null;
  //子弹销毁
  private boolean destroyed=false;
  //绘制次数
  public void destory() {
  }

  //让战斗机移动到最下边中心位置
  public void centerTo(float touchX, float touchY) {
    float w=getWidth();
    float h=getHeight();

  }

  public float getWidth() {
   if (bitmap!=null){
     return bitmap.getWidth();
   }
   return 0;
  }

  public float getHeight() {
    if (bitmap!=null){
      return bitmap.getHeight();
    }
    return 0;
  }
}
