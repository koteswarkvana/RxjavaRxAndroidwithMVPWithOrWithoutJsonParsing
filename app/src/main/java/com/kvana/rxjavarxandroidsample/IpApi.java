package com.kvana.rxjavarxandroidsample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface IpApi {
    String URL = "https://ipapi.co";

    @GET("/json")
    Call<IpApiPojoBean> getIpInfo();

    // without parsing json direct access with pojo class
    @GET("/json")
    Observable<IpApiPojoBean> updateUser();

//  Json parsing with string
//@GET("/json")
//Observable<String> updateUser();
}
