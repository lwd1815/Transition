package com.example.propertyanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;

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
   * ObjectAnimator  动画的执行类，后面详细介绍
   * ValueAnimator 动画的执行类，后面详细介绍 
   * AnimatorSet 用于控制一组动画的执行：线性，一起，每个动画的先后执行等。
   * AnimatorInflater 用户加载属性动画的xml文件
   * TypeEvaluator  类型估值，主要用于设置动画操作属性的值。
   * TimeInterpolator 时间插值，上面已经介绍。
   * 总的来说，属性动画就是，动画的执行类来设置动画操作的对象的属性、持续时间，开始和结束的属性值，时间差值等，然后系统会根据设置的参数动态的变化对象的属性。
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
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
        break;
      //透明
      case R.id.aphle:
        break;
      //缩放
      case R.id.scale:
        break;
      //平移缩放
      case R.id.set:
        break;
      //平移旋转
      case R.id.set2:
        break;
      //平移淡出
      case R.id.set3:
        break;
      //抛物线
      case R.id.other:
        break;
      //待定
      case R.id.other2:
        break;
      //待定
      case R.id.other3:
        break;

    }
  }
}
