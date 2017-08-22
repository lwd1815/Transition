package com.example.propertyanimation.ui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import com.example.propertyanimation.R;
import com.example.propertyanimation.custom.MainTab;
import com.example.propertyanimation.custom.MyFragmentTabHost;

public class MainsActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, View.OnClickListener,View.OnTouchListener{
  private MyFragmentTabHost myFragmentTabHost;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mains);
   myFragmentTabHost=findViewById(R.id.tabhost)
    myFragmentTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
    if (Build.VERSION.SDK_INT>10){
      myFragmentTabHost.getTabWidget().setShowDividers(0);
    }
    initTabs();
    myFragmentTabHost.setCurrentTab(0);
    myFragmentTabHost.setOnTouchListener(this);
  }

  private void initTabs() {
    MainTab[] tabs = MainTab.values();
    int size = tabs.length;
    for (int i = 0; i < size; i++) {
      MainTab mainTab = tabs[i];
      TabHost.TabSpec tab = myFragmentTabHost.newTabSpec(getString(mainTab.getResName()));

      View view = View.inflate(getApplicationContext(),R.layout.tab_indicator,null);
      TextView title = (TextView) view.findViewById(R.id.tab_title);
      Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
      title.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
      if (i == 2){
        view.setVisibility(View.INVISIBLE);
        myFragmentTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
      }
      title.setText(getString(mainTab.getResName()));
      tab.setIndicator(view);
      tab.setContent(new TabHost.TabContentFactory() {
        @Override
        public View createTabContent(String tag) {
          return new View(MainsActivity.this);
        }
      });
      myFragmentTabHost.addTab(tab,mainTab.getClz(),null);
    }
  }

  @Override
  public void onTabChanged(String tabId) {
    int size = myFragmentTabHost.getTabWidget().getTabCount();
    for (int i = 0; i < size; i++) {
      View view = myFragmentTabHost.getTabWidget().getChildAt(i);
      if (i == myFragmentTabHost.getCurrentTab()){
        view.setSelected(true);
      }else{
        view.setSelected(false);
      }
    }
  }

  @Override public void onClick(View view) {

  }

  @Override public boolean onTouch(View view, MotionEvent motionEvent) {
    return false;
  }
}
