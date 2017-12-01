package com.example.propertyanimation.mvp.model;

import com.example.propertyanimation.mvp.presenter.onSearchListener;

/**
 * 创建者     李文东
 * 创建时间   2017/11/30 18:52
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public interface ISearchModel {
  void getIpaddressInfos(String ipAddress,onSearchListener onSearchListener);
}
