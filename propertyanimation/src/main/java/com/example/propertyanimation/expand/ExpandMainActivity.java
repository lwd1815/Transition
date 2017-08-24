package com.example.propertyanimation.expand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.propertyanimation.R;

public class ExpandMainActivity extends AppCompatActivity {

  private TextView mTextMessage;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          if (itemId == item.getItemId()){
            Toast.makeText(ExpandMainActivity.this,itemId + " re select", Toast.LENGTH_SHORT).show();
          }else {
            itemId = item.getItemId();
            switch (item.getItemId()) {
              case R.id.navigation_home:
                mTextMessage.setText(R.string.title_home);
                startActivity(new Intent(ExpandMainActivity.this,Main2Activity.class));
                return true;
              case R.id.navigation_dashboard:
                mTextMessage.setText(R.string.title_dashboard);
                startActivity(new Intent(ExpandMainActivity.this,BottomTabActivity.class));
                return true;
              case R.id.navigation_notifications:
                mTextMessage.setText(R.string.title_notifications);
                return true;
            }
          }
          return false;
        }
      };
  private int itemId;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_expend);

    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    itemId = navigation.getSelectedItemId();

    findViewById(R.id.tv_in).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(ExpandMainActivity.this,ItemListActivity.class));
      }
    });
    findViewById(R.id.tv_out).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(ExpandMainActivity.this,ScrollingActivity.class));
      }
    });
  }
}
