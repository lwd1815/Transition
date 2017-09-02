package com.example.propertyanimation.chat.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.propertyanimation.R;
import com.example.propertyanimation.chat.base.BaseFragment;
import com.example.propertyanimation.chat.qq.QQLoginActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by huang on 2017/2/24.
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.bt_logout) Button btLogout;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_news, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        tvTitle.setText("动态");
        btLogout.setText("退("+ EMClient.getInstance().getCurrentUser()+")出");
    }


    @OnClick(R.id.bt_logout)
    public void onClick() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                startActivity(new Intent(mActivity, QQLoginActivity.class));
                mActivity.finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }

}
