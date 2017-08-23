package com.example.propertyanimation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.example.propertyanimation.adapter.MyAdapter;
import com.example.propertyanimation.custom.MyCollapsingToolbarLayout;

public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

  @InjectView(R.id.rv) RecyclerView rv;
  @InjectView(R.id.lunar1_ccheader) TextView lunar1Ccheader;
  @InjectView(R.id.date1_ccheader) TextView date1Ccheader;
  @InjectView(R.id.chinayear1_ccheader) TextView chinayear1Ccheader;
  @InjectView(R.id.week1_ccheader) TextView week1Ccheader;
  @InjectView(R.id.lunar2_ccheader) TextView lunar2Ccheader;
  @InjectView(R.id.chinayear2_ccheader) TextView chinayear2Ccheader;
  @InjectView(R.id.date2_ccheader) TextView date2Ccheader;
  @InjectView(R.id.animals1_ccheader) TextView animals1Ccheader;
  @InjectView(R.id.animals2_ccheader) TextView animals2Ccheader;
  @InjectView(R.id.week2_ccheader) TextView week2Ccheader;
  @InjectView(R.id.back_myOrder) ImageView backMyOrder;
  @InjectView(R.id.toolbar_myOrders) TextView toolbarMyOrders;
  @InjectView(R.id.title_china_calendar) TextView titleChinaCalendar;
  @InjectView(R.id.toolbar_myOrder) RelativeLayout toolbarMyOrder;
  @InjectView(R.id.toolbar_layout) MyCollapsingToolbarLayout toolbarLayout;
  @InjectView(R.id.app_bar) AppBarLayout appBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_collapsing_toolbar_layout);
    ButterKnife.inject(this);
    initRv();
    //initToolbar();
    toolbarLayout.setTitle("  ");
    toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    //toolbarLayout.setCollapsedTitleGravity(View.FOCUSABLE_AUTO);
    //扩展时文字颜色
    toolbarLayout.setExpandedTitleColor(Color.WHITE);
    toolbarLayout.setCollapsedTitleTextColor(Color.GREEN);
    toolbarLayout.setDrawingCacheBackgroundColor(Color.GREEN);
    //<!--
    //        scroll 想滚动就必须设置这个
    //    enterAlaways 实现quick return 效果,当向下移动时,就立即显示view(如toolbar)
    //    exitUntilCollapsed 向上滚动时收缩view ,但可以固定Toolbar一直在上面
    //    enterAlwaysCollapsed 当你的view已经设置minHeight 属性又使用此标志是,你的view只能以最小
    //    高度进入,只有当滚动视图到达顶部时才扩大到完整高度
    //
    //    contentScrim 设置当完全Collasing ToolbarLayout 折叠(收缩)后的背景颜色
    //    expandedTitleMarginStart 设置扩张时候title向左填充的距离
    //
    //
    //    -->
    //			<!--<ImageView
    //    android:layout_width="match_parent"
    //    android:layout_height="match_parent"
    //    android:scaleType="centerCrop"
    //    android:src="@drawable/drawer_shadow"
    //    app:layout_collapseMode="parallax"
    //    app:layout_collapseParallaxMultiplier="0.7"
    //        />-->
  }

  private void initRv() {
    LinearLayoutManager line = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    rv.setLayoutManager(line);
    MyAdapter adapter = new MyAdapter();
    rv.setAdapter(adapter);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }

  //private void initToolbar() {
  //  setSupportActionBar(toolbar);
  //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //}
}
