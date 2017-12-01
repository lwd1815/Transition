package com.example.propertyanimation.mvp.presenter;

import com.example.propertyanimation.mvp.bean.MyIp;

/**
 * 创建者     李文东
 * 创建时间   2017/11/30 18:56
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public interface onSearchListener {
  void onSuccess(MyIp myIp);
  void onError();
}
