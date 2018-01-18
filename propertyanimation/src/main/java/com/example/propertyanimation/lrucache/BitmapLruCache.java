package com.example.propertyanimation.lrucache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 创建者     李文东
 * 创建时间   2018/1/18 10:50
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class BitmapLruCache extends LruCache<String,Bitmap> {
  //设置缓存大小，建议当前应用可用最大内存的八分之一 即(int) (Runtime.getRuntime().maxMemory() / 1024 / 8)
  public BitmapLruCache(int maxSize) {
    super(maxSize);
  }
  //计算当前节点的内存大小,这个方法需要重写不然返货1

  @Override protected int sizeOf(String key, Bitmap value) {
    //return super.sizeOf(key, value);
    return value.getByteCount()/1024;
  }

  //当节点移除时该方法会回调,可根据需要求来决定对否重新该方法

  @Override
  protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
    super.entryRemoved(evicted, key, oldValue, newValue);
  }
}
