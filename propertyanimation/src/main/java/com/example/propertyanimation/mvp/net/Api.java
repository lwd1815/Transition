package com.example.propertyanimation.mvp.net;

import com.example.propertyanimation.mvp.bean.MyIp;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 创建者     李文东
 * 创建时间   2017/12/1 11:50
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public interface Api {
  @GET("iplookup.php") Observable<MyIp> getIp(@Query("format") String format,@Query("Ip") String ip);
}
