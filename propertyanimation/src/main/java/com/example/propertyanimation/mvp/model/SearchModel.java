package com.example.propertyanimation.mvp.model;

import com.example.propertyanimation.mvp.bean.MyIp;
import com.example.propertyanimation.mvp.net.HttpMethod;
import com.example.propertyanimation.mvp.presenter.onSearchListener;
import rx.Subscriber;

/**
 * 创建者     李文东
 * 创建时间   2017/11/30 18:53
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class SearchModel implements ISearchModel{

  @Override public void getIpaddressInfos(final String ipAddress, final onSearchListener onSearchListener) {
    new Thread(new Runnable() {
      @Override public void run() {
        //String apiUrl = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=Json&ip=";
        final MyIp myIp=new MyIp();
        //try {
        //  URL url=new URL(apiUrl+ipAddress);
        //  HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        //  httpURLConnection.setRequestMethod("GET");
        //  InputStreamReader isp=new InputStreamReader(httpURLConnection.getInputStream());
        //  BufferedReader bf=new BufferedReader(isp);
        //  String line;
        //  StringBuilder stringBuilder=new StringBuilder();
        //  while ((line=bf.readLine())!=null){
        //    stringBuilder.append(line).append("\n");
        //  }
        //
        //  JSONObject jsonObject=new JSONObject(String.valueOf(stringBuilder));
        //  myIp.setCountry(jsonObject.getString("country"));
        //  myIp.setProvince(jsonObject.getString("province"));
        //  myIp.setCity(jsonObject.getString("city"));
        //  onSearchListener.onSuccess(myIp);
        //} catch (Exception e) {
        //  e.printStackTrace();
        //  onSearchListener.onError();
        //}


          Subscriber mSubscriber = new Subscriber<MyIp>() {
            @Override public void onCompleted() {
            }

            @Override public void onError(Throwable e) {
              e.printStackTrace();
              onSearchListener.onError();
            }

            @Override public void onNext(MyIp s) {
              System.out.println("s==" + s);
              myIp.setCountry(s.getCountry());
              myIp.setProvince(s.getProvince());
              myIp.setCity(s.getCity());
              onSearchListener.onSuccess(myIp);
            }
          };
          HttpMethod.getInstance().getIp(mSubscriber, "Json",ipAddress);
        }

    }).start();
  }
}
