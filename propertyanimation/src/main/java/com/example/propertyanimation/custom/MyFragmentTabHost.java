package com.example.propertyanimation.custom;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

/**
 * 创建者     李文东
 * 创建时间   2017/8/22 9:35
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class MyFragmentTabHost extends FragmentTabHost {
  private String mCruuentTag;
  private String mNoTabChangeeTag;

  public MyFragmentTabHost(Context context, AttributeSet attributeSet) {
    super(context,attributeSet);
  }

  @Override public void onTabChanged(String tabId) {
    super.onTabChanged(tabId);
    if (tabId.equals(mNoTabChangeeTag)) {
      setCurrentTabByTag(mCruuentTag);
    } else {
      super.onTabChanged(tabId);
      mCruuentTag = tabId;
    }
  }

  public void setNoTabChangedTag(String tag) {
    this.mNoTabChangeeTag = tag;
  }
}
