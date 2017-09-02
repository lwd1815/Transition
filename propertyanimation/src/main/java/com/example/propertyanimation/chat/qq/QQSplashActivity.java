package com.example.propertyanimation.chat.qq;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.hyphenate.chat.EMClient;

public class QQSplashActivity extends BaseDeepActivity {

  @BindView(R.id.splash_iv) ImageView splashIv;
  private boolean isLogin;
  @Override protected void initActivity() {
    setContentView(R.layout.activity_qqsplash);
    ButterKnife.bind(this);

    isLogin= EMClient.getInstance().isLoggedInBefore();

    ObjectAnimator animator = ObjectAnimator.ofFloat(splashIv,"alpha",0,1,3000);
    animator.addListener(new Animator.AnimatorListener() {

      @Override public void onAnimationStart(Animator animator) {

      }

      @Override public void onAnimationEnd(Animator animator) {
        if (isLogin){
          //跳转到聊天界面
         startActivity(new Intent(QQSplashActivity.this,QQMainActivity.class));
        }else {
          //跳转到登录界面
          startActivity(new Intent(QQSplashActivity.this,QQLoginActivity.class));
        }
      }

      @Override public void onAnimationCancel(Animator animator) {

      }

      @Override public void onAnimationRepeat(Animator animator) {

      }
    });
    animator.start();
  }
}
