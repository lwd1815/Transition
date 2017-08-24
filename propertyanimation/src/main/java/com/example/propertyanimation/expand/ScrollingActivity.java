package com.example.propertyanimation.expand;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;
import com.example.propertyanimation.R;

public class ScrollingActivity extends AppCompatActivity {

  private boolean isFirst;
  private int originWidth;
  private int originHeight;
  private int originY;
  private int originL;
  private RelativeLayout mSearchBGTxt;
  private CollapsingToolbarLayout collapsingtoolbarlayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);
    final AppBarLayout app_bar=(AppBarLayout)findViewById(R.id.app_bar);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("");
    setSupportActionBar(toolbar);

    mSearchBGTxt = (RelativeLayout) findViewById(R.id.rl_search);

    collapsingtoolbarlayout = (CollapsingToolbarLayout) findViewById(
        R.id.toolbar_layout);

    app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.d("verticalOffset",verticalOffset+"");
        Log.w("TotalScrollRange",app_bar.getTotalScrollRange()+"");
        Log.w("mSearchBGTxt.getLeft()", mSearchBGTxt.getLeft()+"");
        Log.w("mSearchBGTxt.getRight()", mSearchBGTxt.getRight()+"");
        Log.w("mSearchBGTxt.getHeight", mSearchBGTxt.getHeight()+"");
        Log.w("mSearchBGTxt.getWidth()", mSearchBGTxt.getWidth()+"");
        Log.w("mSearchBGTxt.getX()", mSearchBGTxt.getX()+"");
        Log.w("mSearchBGTxt.getY()", mSearchBGTxt.getY()+"");
        if (!isFirst){
          originWidth = mSearchBGTxt.getWidth();
          originHeight = mSearchBGTxt.getHeight();
          originY = (int) mSearchBGTxt.getY();
          originL = mSearchBGTxt.getLeft();
          System.out.println("左======"+originWidth+"=====上==="+originHeight+"======右====="+originY+"======下====="+originL);
          isFirst = true;
        }
        //ViewGroup.LayoutParams lp = mSearchBGTxt.getLayoutParams();
        //float s = 1+verticalOffset/app_bar.getTotalScrollRange();
        //double s = 1+verticalOffset*0.025;
        //lp.width = (int) (originWidth*s);
        //lp.width = 500;
        //mSearchBGTxt.setLayoutParams(lp);
        double dw = originWidth*(1+verticalOffset/440.0);
        int w = (int) dw;
        Log.w("originWidth",originWidth+"");
        Log.w("new w",w+"");
        mSearchBGTxt.layout((originWidth-w)/2+2*originL,originY+verticalOffset/6,(originWidth+w)/2,originY+verticalOffset/6+originHeight);
        System.out.println("左1======"+(originWidth-w)/2+2*originL+"=====上1==="+originY+verticalOffset/6+"======右1====="+(originWidth+w)/2+"======下1====="+originY+verticalOffset/6+originHeight);
      }
    });

  }
}
