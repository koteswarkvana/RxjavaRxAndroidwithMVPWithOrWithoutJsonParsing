package com.kvana.rxjavarxandroidsample;

import retrofit2.Call;
import rx.Observable;

public class IpMvp {
    public interface View {
        void dispalyInfo(IpApiPojoBean ipApiPojoBean);
        void displayError(String message);
    }

    public interface Presenter {
        void getIpInfo();
        void setView(View view);
        void rxUnSubscribe();
    }

    public interface Model {
        Call<IpApiPojoBean> fetchIpInfo();
        Observable<IpApiPojoBean> getCityName();
    }
}