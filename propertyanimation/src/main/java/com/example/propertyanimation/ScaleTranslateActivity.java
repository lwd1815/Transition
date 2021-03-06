package com.example.propertyanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.adapter.MyAdapter;

public class ScaleTranslateActivity extends AppCompatActivity {

  @BindView(R.id.et) EditText et;
  @BindView(R.id.ll) RelativeLayout ll;
  @BindView(R.id.rv) RecyclerView rv;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scale_translate);
    ButterKnife.bind(this);

    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    rv.setLayoutManager(linearLayoutManager);
    MyAdapter adapter = new MyAdapter();
    rv.setAdapter(adapter);
    rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        System.out.println("dy====" + dy);
      }
    });
  }
}
