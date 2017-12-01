package com.example.propertyanimation.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import com.example.propertyanimation.mvp.bean.MyIp;
import com.example.propertyanimation.mvp.presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity implements ISearchView{

  @BindView(R.id.et_ip) EditText etIp;
  @BindView(R.id.btn_search) Button btnSearch;
  @BindView(R.id.tv_msg) TextView tvMsg;
  @BindView(R.id.pb_load) ProgressBar pbLoad;
  //view层持有presenter层的对象,同时把view自身传过去
  private SearchPresenter msearchpresenter=new SearchPresenter(this);
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    btnSearch.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        msearchpresenter.Search();
      }
    });
  }
  //获取ip地址,显示在
  @Override public String getIPaddress() {
    return etIp.getText().toString().trim();
  }
  //设置ip地址
  @Override public void setMsg(MyIp myIp) {
    if (myIp!=null){
      tvMsg.setText(myIp.toString());
    }else {
      tvMsg.setText("获取失败");
    }
  }
  //隐藏进度
  @Override public void hideLoad() {
    pbLoad.setVisibility(View.GONE);
  }
  //显示进度
  @Override public void showLoad() {
    pbLoad.setVisibility(View.VISIBLE);
  }
}
