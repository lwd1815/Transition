package com.example.propertyanimation.customview;

import android.content.Intent;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.example.propertyanimation.customview.fragment.CustomViewFragment;

public class CustomViewActivity extends BaseDeepActivity {
  /**
   * http://blog.csdn.net/aigestudio/article/details/41212583
   * 学习的博客地址
   * http://blog.csdn.net/harvic880925/article/details/50995268
   */

  @Override protected void initActivity() {
    setContentView(R.layout.activity_custom_view);
    Intent intent = getIntent();
    int type = intent.getIntExtra("type", 0);
    loadRootFragment(R.id.custom_fragment, CustomViewFragment.getInstance(type));
  }
}
