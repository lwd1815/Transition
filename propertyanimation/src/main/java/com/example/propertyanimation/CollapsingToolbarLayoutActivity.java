package com.example.propertyanimation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.example.propertyanimation.adapter.MyAdapter;
public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

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
  @InjectView(R.id.title_china_calendar) TextView titleChinaCalendar;
  @InjectView(R.id.toolbar) Toolbar toolbar;
  @InjectView(R.id.toolbar_layout) CollapsingToolbarLayout toolbarLayout;
  @InjectView(R.id.app_bar) AppBarLayout appBar;
  @InjectView(R.id.rv) RecyclerView rv;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_collapsing_toolbar_layout);
    ButterKnife.inject(this);
    initRv();
    initToolbar();
    toolbarLayout.setTitle("那人却在灯火阑珊处");
    toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    toolbarLayout.setCollapsedTitleGravity(View.FOCUSABLE_AUTO);
    toolbarLayout.setExpandedTitleColor(Color.WHITE);

  }

  private void initRv() {
    LinearLayoutManager line = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
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

  private void initToolbar() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }
}
