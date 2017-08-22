package com.example.propertyanimation.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.propertyanimation.BaseApplication;
import me.yokeyword.fragmentation.SupportFragment;
import rx.Subscription;

/**
 * 创建者     李文东
 * 创建时间   2017/8/22 14:45
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class BaseFragment extends SupportFragment {
  private Activity activity;
  protected Subscription subscription;
  private int netMobile;
  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  public Context getMContext() {
    if (activity == null) {
      return BaseApplication.getContext();
    }
    return activity;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    activity = getActivity();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unsubscribe();
  }

  protected void unsubscribe() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
