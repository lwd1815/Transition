package com.example.propertyanimation;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * 创建者     李文东
 * 创建时间   2017/8/18 16:17
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class BaseApplication extends Application {

  private RefWatcher mRefWatcher;

  public static RefWatcher getRefWatcher(Context context){
    BaseApplication baseApplication = (BaseApplication) context.getApplicationContext();
    return baseApplication.mRefWatcher;
  }
  @Override
  public void onCreate() {
    super.onCreate();
   //setupLeakCanary();
    mRefWatcher=LeakCanary.install(this);
  }

  protected RefWatcher setupLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)){
      return RefWatcher.DISABLED;
    }
    return LeakCanary.install(this);
  }
}
