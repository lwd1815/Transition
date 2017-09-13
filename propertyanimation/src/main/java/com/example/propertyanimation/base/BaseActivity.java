package com.example.propertyanimation.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by lwd on 2017/9/13
 */

public class BaseActivity extends SupportActivity{

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    //receiver = new NetBroadcastReceiver();
    //this.registerReceiver(receiver,filter);
    //½ûÖ¹ËùÓÐactivityµÄºáÊúÆÁÇÐ»»
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    ActivityManager.getInstance().addActivity(this);

  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ActivityManager.getInstance().finishActivity(this);
  }
}
