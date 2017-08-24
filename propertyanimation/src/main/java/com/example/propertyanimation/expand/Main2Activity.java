package com.example.propertyanimation.expand;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.propertyanimation.R;

public class Main2Activity extends AppCompatActivity {
  private TextView mSearchBGTxt;
  private TextView title;
  private FrameLayout frameBg;

  private boolean finishing;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    mSearchBGTxt = (TextView) findViewById(R.id.tv_search_bg);
    title = (TextView) findViewById(R.id.tv_title);
    frameBg = (FrameLayout) findViewById(R.id.frame_bg);

    findViewById(R.id.tv_in).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        performEnterAnimation();
      }
    });
    findViewById(R.id.tv_out).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        performExitAnimation();
      }
    });
    mSearchBGTxt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        mSearchBGTxt.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        //performEnterAnimation();
      }
    });
  }

  private void performEnterAnimation() {
    //float originY = getIntent().getIntExtra("y", 0);
    //
    //int location[] = new int[2];
    //mSearchBGTxt.getLocationOnScreen(location);
    //
    //final float translateY = originY - (float) location[1];
    //
    ////放到前一个页面的位置
    //mSearchBGTxt.setY(mSearchBGTxt.getY() + translateY);
    //mHintTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mHintTxt.getHeight()) / 2);
    //mSearchTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mSearchTxt.getHeight()) / 2);
    final float top = getResources().getDisplayMetrics().density * 40;
    final ValueAnimator translateVa = ValueAnimator.ofFloat(mSearchBGTxt.getY(),mSearchBGTxt.getY()- top);
    translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mSearchBGTxt.setY((Float) valueAnimator.getAnimatedValue());
        //title.setY(title.getY()-top);
        //frameBg.setY(frameBg.getY()-top);
      }
    });

    ValueAnimator scaleVa = ValueAnimator.ofFloat(1, 0.7f);
    scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mSearchBGTxt.setScaleX((Float) valueAnimator.getAnimatedValue());
      }
    });

    ValueAnimator alphaVa = ValueAnimator.ofFloat(1f, 0);
    alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        //mContentFrame.setAlpha((Float) valueAnimator.getAnimatedValue());
        title.setAlpha((Float) valueAnimator.getAnimatedValue());
      }
    });

    alphaVa.setDuration(2000);
    translateVa.setDuration(2000);
    scaleVa.setDuration(2000);

    alphaVa.start();
    translateVa.start();
    scaleVa.start();

  }

  //@Override
  //public void onBackPressed() {
  //  if (!finishing){
  //    finishing = true;
  //    performExitAnimation();
  //  }
  //}

  private void performExitAnimation() {
    //float originY = getIntent().getIntExtra("y", 0);
    //
    //int location[] = new int[2];
    //mSearchBGTxt.getLocationOnScreen(location);
    //
    //final float translateY = originY - (float) location[1];

    final float top = getResources().getDisplayMetrics().density * 40;
    final ValueAnimator
        translateVa = ValueAnimator.ofFloat(mSearchBGTxt.getY(),mSearchBGTxt.getY() +top);
    translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mSearchBGTxt.setY((Float) valueAnimator.getAnimatedValue());
        //title.setY(title.getY()+top);
        //frameBg.setY(frameBg.getY()+top);
      }
    });


    ValueAnimator scaleVa = ValueAnimator.ofFloat(0.7f, 1f);
    scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mSearchBGTxt.setScaleX((Float) valueAnimator.getAnimatedValue());
      }
    });

    ValueAnimator alphaVa = ValueAnimator.ofFloat(0, 1f);
    alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        //mContentFrame.setAlpha((Float) valueAnimator.getAnimatedValue());
        title.setAlpha((Float) valueAnimator.getAnimatedValue());

      }
    });


    alphaVa.setDuration(2000);
    translateVa.setDuration(2000);
    scaleVa.setDuration(2000);

    alphaVa.start();
    translateVa.start();
    scaleVa.start();

  }
}
