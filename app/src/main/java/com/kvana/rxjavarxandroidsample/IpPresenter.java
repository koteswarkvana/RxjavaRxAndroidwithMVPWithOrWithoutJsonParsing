package com.kvana.rxjavarxandroidsample;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class IpPresenter implements IpMvp.Presenter {

    private IpMvp.View mView;
    private IpMvp.Model mModel;
    private Subscription subscription = null;
    // = new CompositeSubscription();

    public IpPresenter(IpMvp.Model model) {
        this.mModel = model;
    }


    @Override
    public void rxUnSubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    public void getIpInfo() {

        // Using Rxjava & RxAndroid point of view.
        // without json parsing showing info with pojo class...
        subscription = mModel.getCityName().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<IpApiPojoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(IpApiPojoBean ipApiPojoBean) {
                Log.d("city info >> ", "onNext: >> "+ipApiPojoBean.getCity());
            }
        });

        // Json parsing with json data...
      /*  subscription = mModel.getCityName().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String data) {
                Log.d("info >> ", "json data onNext: >>> "+data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String city = jsonObject.getString("city");
                    String region = jsonObject.getString("region");
                    Log.d("info >> ", "json data city & region: >>> "+city +"  "+region);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });*/


                // retrofit point of view by using enqueue
                mModel.fetchIpInfo().enqueue(new Callback<IpApiPojoBean>() {
                    @Override
                    public void onResponse(Call<IpApiPojoBean> call, Response<IpApiPojoBean> response) {
                        if (response.isSuccessful())
                            mView.dispalyInfo(response.body());
                        else
                            mView.displayError(response.message());
                    }

                    @Override
                    public void onFailure(Call<IpApiPojoBean> call, Throwable t) {
                        mView.displayError(t.getMessage());
                    }
                });
    }

    @Override
    public void setView(IpMvp.View view) {
        this.mView = view;
    }
}