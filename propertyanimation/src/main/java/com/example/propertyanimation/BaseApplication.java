package com.example.propertyanimation;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import cn.bmob.v3.Bmob;
import com.example.propertyanimation.chat.ChatActivity;
import com.example.propertyanimation.chat.LoginActivity;
import com.example.propertyanimation.chat.utils.ThreadUtil;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * 创建者     李文东
 * 创建时间   2017/8/18 16:17
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class BaseApplication extends Application implements EMMessageListener,
    EMConnectionListener {
  // 记录是否已经初始化
  private boolean isInit = false;
  private RefWatcher mRefWatcher;
  public static Context mContext;


  private SoundPool soundPool;
  private int duanId;
  private int yuluId;
  private List<Activity> activities = new ArrayList<>();
  public void addActivity(Activity activity){
    activities.add(activity);
  }
  public static RefWatcher getRefWatcher(Context context){
    BaseApplication baseApplication = (BaseApplication) context.getApplicationContext();
    return baseApplication.mRefWatcher;
  }
  @Override
  public void onCreate() {
    super.onCreate();
   //setupLeakCanary();
    mRefWatcher=LeakCanary.install(this);
    this.mContext=getApplicationContext();

    // 初始化环信sdk
    EMOptions options = new EMOptions();
    // 默认添加好友时，true是不需要验证的，false需要验证
    options.setAcceptInvitationAlways(true);
    //初始化
    EMClient.getInstance().init(this, options);
    //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
    EMClient.getInstance().setDebugMode(true);

    // 初始化bmob云数据库
    Bmob.initialize(this, "ea6b31969fdd7f04e5f3eeb81a55c4c1");

    // 监听消息
    EMClient.getInstance().chatManager().addMessageListener(this);

    // 参数1 同时支持流数量
    // 参数2 流的类型
    // 参数3 质量
    soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
    // 加载声音
    duanId = soundPool.load(this, R.raw.duan, 1);
    yuluId = soundPool.load(this, R.raw.yulu, 1);

    //注册一个监听连接状态的listener
    EMClient.getInstance().addConnectionListener(this);
  }

  /**
   * 根据Pid获取当前进程的名字，一般就是当前app的包名
   *
   * @param pid 进程的id
   * @return 返回进程的名字
   */
  private String getAppName(int pid) {
    String processName = null;
    ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
    List list = activityManager.getRunningAppProcesses();
    Iterator i = list.iterator();
    while (i.hasNext()) {
      ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
      try {
        if (info.pid == pid) {
          // 根据进程的信息获取当前进程的名字
          processName = info.processName;
          // 返回当前进程名
          return processName;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // 没有匹配的项，返回为null
    return null;
  }
  protected RefWatcher setupLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)){
      return RefWatcher.DISABLED;
    }
    return LeakCanary.install(this);
  }

  public static Context getContext(){
    return mContext;
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  @Override
  public void onMessageReceived(List<EMMessage> messages) {
    //收到消息
    EMMessage emMessage = messages.get(0);
    EventBus.getDefault().post(emMessage);

    // 播放提示音
    if(isRunningBack()){
      soundPool.play(yuluId,1,1,0,0,1);
      // 发送通知
      // 需要先打开主界面，再打开聊天界面
      Intent mainIntent = new Intent(this, MainActivity.class);
      mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      Intent chatIntent = new Intent(this, ChatActivity.class);
      chatIntent.putExtra("username",emMessage.getUserName());

      Intent[] intents = new Intent[]{mainIntent,chatIntent};
      PendingIntent pendingIntent = PendingIntent.getActivities(this,0,intents,PendingIntent.FLAG_UPDATE_CURRENT);

      EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
      Notification.Builder builder = new Notification.Builder(this);

      Notification notification = builder.setSmallIcon(R.drawable.conversation_selected_2)
          .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.avatar3))
          .setContentTitle("您有一条新消息")
          .setContentText(body.getMessage())
          .setContentInfo("来自" + emMessage.getUserName())
          .setContentIntent(pendingIntent)
          .setAutoCancel(true)
          .build();

      NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      notificationManager.notify(1,notification);

    }else{
      soundPool.play(duanId,1,1,0,0,1);
    }
  }

  @Override public void onCmdMessageReceived(List<EMMessage> list) {

  }

  private boolean isRunningBack() {
    // 判断当前应用是否运行在后台
    ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
    return !runningTasks.get(0).topActivity.getPackageName().equals(getPackageName());
  }

  @Override
  public void onMessageChanged(EMMessage message, Object change) {
    //消息状态变动
  }

  @Override
  public void onConnected() {

  }

  @Override
  public void onDisconnected(final int error) {
    ThreadUtil.executeMainThread(new Runnable() {
      @Override
      public void run() {
        if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
          // 显示帐号在其他设备登录
          // 关闭所有界面，打开登陆界面
          for (int i = 0; i < activities.size(); i++) {
            activities.get(i).finish();
          }
          activities.clear();

          Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        }
      }
    });
  }

  public void removeActivity(Activity activity) {
    activities.remove(activity);
  }


  @Override public void onMessageRead(List<EMMessage> list) {

  }

  @Override public void onMessageDelivered(List<EMMessage> list) {

  }

  @Override public void onMessageRecalled(List<EMMessage> list) {

  }
}
