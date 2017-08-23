package com.example.propertyanimation.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.example.propertyanimation.custom.MainTab;
import com.example.propertyanimation.custom.MyFragmentTabHost;
import com.example.propertyanimation.drawable.DrawableFragment;

public class MainsActivity extends BaseDeepActivity
    implements TabHost.OnTabChangeListener, View.OnClickListener, View.OnTouchListener,DrawableFragment.NavigationDrawerCallbacks {
  private MyFragmentTabHost myFragmentTabHost;
  private Toolbar toolbar;
  private CharSequence mTitle;
  private DrawableFragment drawableFragment;
  private MaterialMenuDrawable materialMenu;

  // TODO: 2017/8/22 单纯继承supportactivity就没有getSupportFragmentManager
  //toolbar 左上角小箭头参考 githun material-menu
  @Override protected void initActivity() {
    setContentView(R.layout.activity_mains);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

      }
    });
    materialMenu = new MaterialMenuDrawable(this, Color.WHITE,
        MaterialMenuDrawable.Stroke.THIN);
    toolbar.setNavigationIcon(materialMenu);
    initView();
  }

  private void initView() {



    drawableFragment =
        (DrawableFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    mTitle=getTitle();
    drawableFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout),toolbar);

    myFragmentTabHost = (MyFragmentTabHost) findViewById(R.id.tabhost);
    myFragmentTabHost.setup(MainsActivity.this, getSupportFragmentManager(), R.id.realtabcontent);
    if (Build.VERSION.SDK_INT > 10) {
      myFragmentTabHost.getTabWidget().setShowDividers(0);
    }

    initTabs();
    myFragmentTabHost.setCurrentTab(0);
    myFragmentTabHost.setOnTouchListener(this);
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
  }

  private void initTabs() {
    MainTab[] tabs = MainTab.values();
    int size = tabs.length;
    for (int i = 0; i < size; i++) {
      MainTab mainTab = tabs[i];
      TabHost.TabSpec tab = myFragmentTabHost.newTabSpec(getString(mainTab.getResName()));

      View view = View.inflate(getApplicationContext(), R.layout.tab_indicator, null);
      TextView title = (TextView) view.findViewById(R.id.tab_title);
      ImageView imageView =(ImageView)view.findViewById(R.id.iv);

      //如果中间的想要自定义可以用下面的
      Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
      //title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
      //if (i == 2) {
      //  view.setVisibility(View.INVISIBLE);
      //  myFragmentTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
      //}
      title.setText(getString(mainTab.getResName()));
      imageView.setImageResource(mainTab.getResIcon());
      tab.setIndicator(view);
      tab.setContent(new TabHost.TabContentFactory() {
        @Override public View createTabContent(String tag) {
          return new View(MainsActivity.this);
        }
      });
      myFragmentTabHost.addTab(tab, mainTab.getClz(), null);
    }
  }

  @Override public void onTabChanged(String tabId) {
    int size = myFragmentTabHost.getTabWidget().getTabCount();
    for (int i = 0; i < size; i++) {
      View view = myFragmentTabHost.getTabWidget().getChildAt(i);
      if (i == myFragmentTabHost.getCurrentTab()) {
        view.setSelected(true);
      } else {
        view.setSelected(false);
      }
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_activity_menu,menu);
    if (!drawableFragment.isDrawerOpen()){
      restoreActionBar();
      return true;
    }

    return super.onCreateOptionsMenu(menu);
  }

  public void restoreActionBar(){
    ActionBar actionBar = getSupportActionBar();
    //设置显示为标准模式, 还有NAVIGATION_MODE_LIST列表模式, NAVIGATION_MODE_TABS选项卡模式. 参见ApiDemos
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    //设置标题显示
    actionBar.setDisplayShowTitleEnabled(true);
    //设置标题
    actionBar.setTitle(mTitle);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id=item.getItemId();
    switch (id){
      case R.id.search:
        break;

      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onClick(View view) {

  }

  @Override public boolean onTouch(View view, MotionEvent motionEvent) {
    return false;
  }

  @Override public void onNavigationDrawerItemSelected(int position) {
    //update the main content by replacing fragment
  }
}
