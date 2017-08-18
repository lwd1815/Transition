package com.example.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  @InjectView(R.id.translate) Button translate;
  @InjectView(R.id.xuanzhuan) Button xuanzhuan;
  @InjectView(R.id.scale) Button scale;
  @InjectView(R.id.aphle) Button aphle;
  @InjectView(R.id.set) Button set;
  @InjectView(R.id.set2) Button set2;
  @InjectView(R.id.set3) Button set3;
  @InjectView(R.id.other) Button other;
  @InjectView(R.id.other2) Button other2;
  @InjectView(R.id.other3) Button other3;
  @InjectView(R.id.oo) LinearLayout oo;
  @InjectView(R.id.yuan) ImageView yuan;

  /**
   * 相关API
   * Duration 动画持续时间,默认300ms
   * Time interpolation 时间差值
   * AccelerateDecelerateInterpolator 定义动画的变化率
   * Repeat count and behavior 重复次数
   * Animator sets 动画集
   * Frame refresh delay 帧刷新延迟
   *
   * 相关的类
   * ObjectAnimator  动画的执行类，对象动画,直接作用于view
   * ValueAnimator 动画的执行类，这个本身不能对view造成动画效果,它是根据指定属性的起始值,可选指定插值器,添加监听动画回调变化过程中的返回值,根据返回值来动态改变view的属性值
   * AnimatorSet 用于控制一组动画的执行：线性，一起，每个动画的先后执行等。
   * AnimatorInflater 用户加载属性动画的xml文件
   * TypeEvaluator  类型估值，主要用于设置动画操作属性的值。
   * TimeInterpolator 时间插值，上面已经介绍。
   * 总的来说，属性动画就是，动画的执行类来设置动画操作的对象的属性、持续时间，开始和结束的属性值，时间差值等，然后系统会根据设置的参数动态的变化对象的属性。
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //leakcanary配置
    RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
    refWatcher.watch(this);
    ButterKnife.inject(this);
    initClick();
  }

  private void initClick() {
    translate.setOnClickListener(this);
    xuanzhuan.setOnClickListener(this);
    scale.setOnClickListener(this);
    aphle.setOnClickListener(this);
    set.setOnClickListener(this);
    set2.setOnClickListener(this);
    set3.setOnClickListener(this);
    other.setOnClickListener(this);
    other2.setOnClickListener(this);
    other3.setOnClickListener(this);
  }

  @Override public void onClick(View view) {
    switch (view.getId()){
      //平移
      case R.id.translate:
        break;
      //旋转
      case R.id.xuanzhuan:
        ObjectAnimator.ofFloat(yuan,"rotationX",0.0F,360.0F).setDuration(500).start();
        break;
      //透明
      case R.id.aphle:
        ObjectAnimator anim = ObjectAnimator.ofFloat(yuan,"zhy",1.0F,0.0F).setDuration(500);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float cVal = (float) valueAnimator.getAnimatedValue();
            yuan.setAlpha(cVal);
            yuan.setScaleX(cVal);
            yuan.setScaleY(cVal);
          }
        });
        break;
      //缩放
      case R.id.scale:
        //点击测试内存泄露
        startAsyncTask();
        break;
      //平移缩放
      case R.id.set:
        Intent intent = new Intent(this,CollapsingToolbarLayoutActivity.class);
        startActivity(intent);
        break;
      //平移旋转
      case R.id.set2:
        break;
      //平移淡出
      case R.id.set3:
        break;
      //抛物线
      case R.id.other:
        //垂直
        //ValueAnimator animator = ValueAnimator.ofFloat(0,540-yuan.getHeight());
        //animator.setTarget(yuan);
        //animator.setDuration(1000).start();
        //animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        //  @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
        //    yuan.setTranslationY((Float) valueAnimator.getAnimatedValue());
        //  }
        //});
        //抛物线
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(3000);
        animator.setObjectValues(new PointF(0,0));
        animator.setInterpolator(new LinearInterpolator());
        animator.setEvaluator(new TypeEvaluator<PointF>() {
          @Override public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF point = new PointF();
            point.x=200*fraction*3;
            point.y=0.5f*200*(fraction*3)*(fraction*3);
            return point;
          }
        });
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
            PointF point = (PointF) valueAnimator.getAnimatedValue();
            yuan.setX(point.x);
            yuan.setY(point.y);
          }
        });
        break;
      //待定
      case R.id.other2:
        //过个动画联合执行
        //ObjectAnimator animA= ObjectAnimator.ofFloat(yuan,"scaleX",1.0F,2F);
        //ObjectAnimator animB= ObjectAnimator.ofFloat(yuan,"scaleY",1.0F,2F);
        //AnimatorSet animSet = new AnimatorSet();
        //animSet.setDuration(2000);
        //animSet.setInterpolator(new LinearInterpolator());
        //animSet.playTogether(animA,animB);
        //animSet.start();
        //几个动画先后连续执行
        float cx = yuan.getX();
        ObjectAnimator animC = ObjectAnimator.ofFloat(yuan,"scaleX",1.0f,2f);
        ObjectAnimator animD = ObjectAnimator.ofFloat(yuan,"scaleY",1.0f,2f);
        ObjectAnimator animE = ObjectAnimator.ofFloat(yuan,"x",cx,0f);
        ObjectAnimator animF = ObjectAnimator.ofFloat(yuan,"x",cx);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animC).with(animD);
        animSet.play(animD).with(animF);
        animSet.play(animF).with(animE);
        animSet.setDuration(5000);
        animSet.start();
        break;
      //待定
      case R.id.other3:
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(yuan,"zhys",0.0F,1.0F).setDuration(500);
        anim1.start();
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float cVal = (float) valueAnimator.getAnimatedValue();
            yuan.setAlpha(cVal);
            yuan.setScaleX(cVal);
            yuan.setScaleY(cVal);
          }
        });
        break;

    }
  }

  private void async(){
    startAsyncTask();
  }
  //内存泄露测试类
  private void startAsyncTask() {
    // This async task is an anonymous class and therefore has a hidden reference to the outer
    // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
    // the activity instance will leak.
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        // Do some slow work in background
        SystemClock.sleep(20000);
        return null;
      }
    }.execute();
  }
}
