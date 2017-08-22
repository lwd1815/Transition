package com.example.propertyanimation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by EtherealPatrick on 2017/2/23.
 */

public class BaseActivity extends SupportActivity{

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    //receiver = new NetBroadcastReceiver();
    //this.registerReceiver(receiver,filter);

    ActivityManager.getInstance().addActivity(this);

  }

  @Override protected void onDestroy() {
    super.onDestroy();
    //this.unregisterReceiver(receiver);
    ActivityManager.getInstance().finishActivity(this);
  }
}
