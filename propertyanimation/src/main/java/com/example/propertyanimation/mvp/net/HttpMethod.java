package com.example.propertyanimation.mvp.net;

import com.example.propertyanimation.mvp.bean.MyIp;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建者     李文东
 * 创建时间   2017/12/1 11:56
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class HttpMethod {
  //正式环境
  public static final String BASE_URL="http://int.dpool.sina.com.cn/iplookup/";
  private static final int DEFAULT_TIMEOUT=10;
  private Retrofit mRetrofit;
  private Api mApiNet;

  private HttpMethod(){
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

    mRetrofit = new Retrofit.Builder()
        .client(httpClientBuilder.build())
        //.addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(FastJsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build();

    mApiNet=mRetrofit.create(Api.class);

  }

  private static class singletonHolder{
    private static final HttpMethod INSTANCE = new HttpMethod();
  }

  public static HttpMethod getInstance(){
    return singletonHolder.INSTANCE;
  }

  public void getIp(Subscriber<MyIp> subscriber,String format,String Ip){
    mApiNet.getIp(format,Ip)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }
}
