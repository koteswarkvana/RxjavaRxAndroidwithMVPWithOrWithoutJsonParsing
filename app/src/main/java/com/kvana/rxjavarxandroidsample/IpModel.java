package com.kvana.rxjavarxandroidsample;

import retrofit2.Call;
import rx.Observable;

public class IpModel implements IpMvp.Model {
    private NetworkHandler networkHandler;

    public IpModel(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    @Override
    public Call<IpApiPojoBean> fetchIpInfo() {
        return networkHandler.ipInfoApi().getIpInfo();
    }

    //    without json parsing by using pojo class
    @Override
    public Observable<IpApiPojoBean> getCityName() {
        return networkHandler.ipInfoApi().updateUser();
    }

//    with json parsing
//    @Override
//    public Observable<String> getCityName() {
//        return networkHandler.ipInfoApi().updateUser();
//    }
}
