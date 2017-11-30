package com.example.propertyanimation.mvp.view;

import com.example.propertyanimation.mvp.bean.MyIp;

/**
 * 创建者     李文东
 * 创建时间   2017/11/30 18:54
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public interface ISearchView {
  String getIPaddress();
  void setMsg(MyIp myIp);
  void hideLoad();
  void showLoad();
}
