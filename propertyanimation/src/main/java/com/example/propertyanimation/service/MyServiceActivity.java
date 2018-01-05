package com.example.propertyanimation.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.propertyanimation.R;

public class MyServiceActivity extends AppCompatActivity implements View.OnClickListener{
  private Button startService;

  private Button stopService;
  private Button bindService;

  private Button unbindService;
  private MyService.MyBinder myBinder;

  private ServiceConnection connection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      myBinder= (MyService.MyBinder) iBinder;
      myBinder.startDownload();
    }

    @Override public void onServiceDisconnected(ComponentName componentName) {

    }
  };
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_service);
    startService = (Button) findViewById(R.id.start_service);
    stopService = (Button) findViewById(R.id.stop_service);
    bindService = (Button) findViewById(R.id.bind_service);
    unbindService = (Button) findViewById(R.id.unbind_service);
    startService.setOnClickListener(this);
    stopService.setOnClickListener(this);
    bindService.setOnClickListener(this);
    unbindService.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.start_service:
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
        break;
      case R.id.stop_service:
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);
        break;
      case R.id.bind_service:
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
        break;
      case R.id.unbind_service:
        unbindService(connection);
        break;
      default:
        break;
    }
  }
}
