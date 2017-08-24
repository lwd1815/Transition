package com.example.propertyanimation.expand;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.propertyanimation.R;

public class BottomTabActivity extends AppCompatActivity implements View.OnClickListener{

  private LinearLayoutCompat mBottomBar;
  private int mTabCount;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bottom_tab);
    mBottomBar = (LinearLayoutCompat) findViewById(R.id.bottom_bar);
    initTab();
    showDot(0);
    showMsg(1,100);
    showMsg(3,1);
    showMsg(4,99);
  }

  private void initTab() {
    mTabCount = 5;
    for (int i = 0; i < mTabCount; i++) {
      if (i == 2){
        LayoutInflater.from(BottomTabActivity.this).inflate(R.layout.bottom_item_null_layout, mBottomBar);
        final TextView item = (TextView) mBottomBar.getChildAt(i);
        item.setEnabled(false);
        item.setTag(i);
        item.setOnClickListener(this);
      }else {
        LayoutInflater.from(BottomTabActivity.this).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
        item.setTag(i);
        item.setOnClickListener(this);
        final ImageView itemIcon = (ImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
      }
    }
  }

  // show MsgTipView
  private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private SparseArray<Boolean> mInitSetMap = new SparseArray<>();

  /**
   * 显示未读消息
   *
   * @param position 显示tab位置
   * @param num      num小于等于0显示红点,num大于0显示数字
   */
  public void showMsg(int position, int num) {
    if (position >= mTabCount) {
      position = mTabCount - 1;
    }

    View tabView = mBottomBar.getChildAt(position);
    MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
    if (tipView != null) {
      UnreadMsgUtils.show(tipView, num);

      if (mInitSetMap.get(position) != null && mInitSetMap.get(position)) {
        return;
      }

      mInitSetMap.put(position, true);
    }
  }

  /**
   * 显示未读红点
   *
   * @param position 显示tab位置
   */
  public void showDot(int position) {
    if (position >= mTabCount) {
      position = mTabCount - 1;
    }
    showMsg(position, 0);
  }

  public void hideMsg(int position) {
    if (position >= mTabCount) {
      position = mTabCount - 1;
    }

    View tabView = mBottomBar.getChildAt(position);
    MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
    if (tipView != null) {
      tipView.setVisibility(View.GONE);
    }
  }



  /** 当前类只提供了少许设置未读消息属性的方法,可以通过该方法获取MsgView对象从而各种设置 */
  public MsgView getMsgView(int position) {
    if (position >= mTabCount) {
      position = mTabCount - 1;
    }
    View tabView = mBottomBar.getChildAt(position);
    MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
    return tipView;
  }

  @Override public void onClick(View v) {
    Toast.makeText(BottomTabActivity.this,"tag : "+v.getTag(), Toast.LENGTH_SHORT).show();
  }
}
