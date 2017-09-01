package com.example.propertyanimation.chat.qq;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.example.propertyanimation.chat.bean.UserBean;
import com.example.propertyanimation.chat.utils.SpUtil;
import com.example.propertyanimation.chat.utils.ThreadUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class QQRegistActivity extends BaseDeepActivity {

  @BindView(R.id.et_username) EditText etUsername;
  @BindView(R.id.et_password) EditText etPassword;
  @BindView(R.id.bt_regist) Button btRegist;

  @Override protected void initActivity() {
    setContentView(R.layout.activity_qqregist);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.bt_regist) public void onViewClicked() {
    final String username = etUsername.getText().toString();
    final String password = etPassword.getText().toString();
    if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
      Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
      return;
    }

    //bomb注册账户,成功后注册环信服务
    //注册到环信服务器
    UserBean userBean = new UserBean();
    userBean.setUsername(username);
    userBean.setPassword(password);
    userBean.signUp(new SaveListener<UserBean>() {

      @Override public void done(final UserBean userBean, BmobException e) {
        if(e==null){
          //

          //注册到环信服务器
          ThreadUtil.executeSubThread(new Runnable() {
            @Override
            public void run() {
              try {
                EMClient.getInstance().createAccount(username, password);//同步方法
                ThreadUtil.executeMainThread(new Runnable() {
                  @Override
                  public void run() {
                    // 存储用户名密码
                    SpUtil.putString(QQRegistActivity.this,SpUtil.KEY_USERNAME,username);
                    SpUtil.putString(QQRegistActivity.this,SpUtil.KEY_PASSWORD,password);
                    Toast.makeText(QQRegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                  }
                });
              } catch (HyphenateException e1) {
                e1.printStackTrace();
                // 环信注册失败
                userBean.delete();
              }
            }
          });

        }else{
          e.printStackTrace();
        }
      }
    });
  }
}
