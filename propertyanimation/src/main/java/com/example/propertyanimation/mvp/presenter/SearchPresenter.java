package com.example.propertyanimation.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import com.example.propertyanimation.mvp.bean.MyIp;
import com.example.propertyanimation.mvp.model.SearchModel;
import com.example.propertyanimation.mvp.view.ISearchView;

/**
 * 创建者     李文东
 * 创建时间   2017/11/30 18:56
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class SearchPresenter implements ISearchPresenter,onSearchListener{
  //presenter分别持有view和model对象
  ISearchView searchView;
  SearchModel searchModel;
  //Handler必须是os包中的
  Handler handler;
  public SearchPresenter(ISearchView iSearchView){
    this.searchView=iSearchView;
    searchModel=new SearchModel();
    handler = new Handler(Looper.getMainLooper());
  }

  @Override public void Search() {
    searchView.showLoad();
    searchModel.getIpaddressInfos(searchView.getIPaddress(),this);
  }

  @Override public void onSuccess(final MyIp myIp) {
    handler.post(new Runnable() {
      @Override public void run() {
        searchView.setMsg(myIp);
        searchView.hideLoad();
      }
    });
  }

  @Override public void onError() {
    handler.post(new Runnable() {
      @Override public void run() {
        searchView.setMsg(null);
        searchView.hideLoad();
      }
    });
  }
}
