package com.example.propertyanimation.expendlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import com.example.propertyanimation.R;

public class ExpandListViewActivity extends AppCompatActivity {


  ExpandableListView mExpandableListView;
  ExpandableListViewAdapter mExpandableListViewAdapter;
  private static final String TAG = "Main";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_expand_list_view);
    mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
    mExpandableListViewAdapter = new ExpandableListViewAdapter(this);
    mExpandableListView.setAdapter(mExpandableListViewAdapter);
    mExpandableListView.expandGroup(0);
    mExpandableListView
        .setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
          @Override
          public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            if (groupPosition==1){
              //及其在全部数据
            }else if (groupPosition==2){
              //加载全部数据
            }else if (groupPosition==2){

            }else if (groupPosition==2){

            }
            return false;
          }
        });
    mExpandableListView
        .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
          @Override
          public boolean onChildClick(ExpandableListView parent,
              View v, int groupPosition, int childPosition,
              long id) {
            Log.e(TAG, "groupPosition=" + groupPosition
                + ",childPosition=" + childPosition);
            return false;
          }
        });

    mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
      @Override public void onGroupExpand(int i) {
        if (i==1){
          mExpandableListView.collapseGroup(i);
        }else if (i==2){
          mExpandableListView.collapseGroup(i);
        }
      }
    });
  }
}
