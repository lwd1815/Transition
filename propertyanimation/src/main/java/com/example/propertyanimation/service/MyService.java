package com.example.propertyanimation.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.propertyanimation.MainActivity;
import com.example.propertyanimation.R;

/**
 * 创建者     李文东
 * 创建时间   2018/1/2 15:46
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class MyService extends Service {
  public static final String TAG="MyService";
  private MyBinder myBinder=new MyBinder();
  @Override public void onCreate() {
    super.onCreate();
    System.out.println("onCreate");
    Log.d(TAG,"oncreat() executed");
    Notification notification = new Notification(R.drawable.ic_launcher,
        "有通知到来", System.currentTimeMillis());
    Intent notificationIntent = new Intent(this, MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
        notificationIntent, 0);
    //notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",
    //    pendingIntent);
    startForeground(1, notification);
    Log.d(TAG, "onCreate() executed");

  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG,"onStartCommand() executed");
    System.out.println("onStartCommand");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return myBinder;
  }

  class MyBinder extends Binder{
    public void startDownload(){
      System.out.println("startDownload");
      //执行下载任务
    }
  }
}
