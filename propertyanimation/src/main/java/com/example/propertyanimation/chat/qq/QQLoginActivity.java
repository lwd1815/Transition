package com.example.propertyanimation.chat.qq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.example.propertyanimation.chat.utils.SpUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class QQLoginActivity extends BaseDeepActivity implements View.OnClickListener{

  @BindView(R.id.et_username) EditText etUsername;
  @BindView(R.id.et_password) EditText etPassword;
  @BindView(R.id.bt_login) Button btLogin;
  @BindView(R.id.tv_regist) TextView tvRegist;
  private ProgressDialog dialog;
  @Override protected void initActivity() {
    setContentView(R.layout.activity_qqlogin);
    ButterKnife.bind(this);
    tvRegist.setOnClickListener(this);
    dialog=new ProgressDialog(this);
  }

  @OnClick({ R.id.bt_login, R.id.tv_regist })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.bt_login:
        dialog.setMessage("敌人还有三十秒到达战场");
        dialog.show();
        final  String username=etUsername.getText().toString();
        final  String password=etPassword.getText().toString();
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
          Toast.makeText(getApplicationContext(), "用户名或密码为空", Toast.LENGTH_SHORT).show();
        }

        //登录到环信服务器
        EMClient.getInstance().login(username, password, new EMCallBack() {
          @Override public void onSuccess() {

            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
            //存储用户名和密码
            SpUtil.putString(QQLoginActivity.this,SpUtil.KEY_USERNAME,username);
            SpUtil.putString(QQLoginActivity.this,SpUtil.KEY_PASSWORD,password);

            dialog.dismiss();
            startActivity(new Intent(QQLoginActivity.this, QQMainActivity.class));
            finish();
          }

          @Override public void onError(int i, String s) {

          }

          @Override public void onProgress(int i, String s) {

          }
        });

        break;
    }
  }

  @Override public void onClick(View view) {
    startActivity(new Intent(this,QQRegistActivity.class));
  }

  @Override protected void onResume() {
    super.onResume();
    //获取用户名和密码
    String username = SpUtil.getString(this, SpUtil.KEY_USERNAME, "");
    String password = SpUtil.getString(this, SpUtil.KEY_PASSWORD, "");
    etUsername.setText(username);
    etPassword.setText(password);
  }
}
