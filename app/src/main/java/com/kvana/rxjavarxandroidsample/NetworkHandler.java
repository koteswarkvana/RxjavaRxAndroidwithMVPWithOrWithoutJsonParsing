package com.kvana.rxjavarxandroidsample;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Handles network instances
 */
public class NetworkHandler {
    private static final NetworkHandler ourInstance = new NetworkHandler();
    private Interceptor mInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (response.isSuccessful())
                return response;
            switch (response.code()) {
                case 400:
                    break;
            }

            return response;
        }
    };

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(IpApi.URL)
            .addConverterFactory(StringConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private IpApi mIpApi = mRetrofit.create(IpApi.class);

    public NetworkHandler() {
    }

    public static NetworkHandler instance() {
        return ourInstance;
    }

    public IpApi ipInfoApi() {
        return mIpApi;
    }
}
