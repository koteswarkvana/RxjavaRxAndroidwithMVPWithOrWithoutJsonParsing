# Rxjava RxAndroid with MVP With Or Without JsonParsing by using retrofit

 *  Steps:
          *  1. Add dependencies:
          *  // RxAndroid
            implementation 'io.reactivex:rxandroid:1.2.0'
            //RxJava
            implementation 'io.reactivex:rxjava:1.1.5'
            //retrofit
            implementation 'com.squareup.retrofit2:retrofit:2.1.0'
            implementation 'com.squareup.retrofit2:converter-gson:2.0.0-beta4'
            implementation 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
            implementation 'com.google.code.gson:gson:2.7'

            2. in AndroidManifest.xml file
             <uses-permission android:name="android.permission.INTERNET" />

            3. create IpApi interface place url and create get or post or put or delete call

            String URL = "https://ipapi.co";
            @GET("/json")
            Call<IpApiPojoBean> getIpInfo();

            4. Create NetworkHandler file and retrofit object.

             private static final NetworkHandler ourInstance = new NetworkHandler();
             private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(IpApi.URL)
            .addConverterFactory(StringConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

            5. create IpMvp file and create 3 interfaces for model, view, presenter

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

            6. create IpModel file and implements model interface methods, create model constructor

            class IpModel implements IpMvp.Model {
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

            7. create IpPresenter class and implement presenter methods
              *** Look in presenter rxjava, rxandroid and retrofit enqueue service call doing
              * while using subscribe in rxjava, rxandroid need to unsubscribe onStop() or onDestroy() method.

            class IpPresenter implements IpMvp.Presenter {

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
            }

            8. create mainactivity class and implement view interface
            call presenter class methods for json data and stop unsubscribe onStop() or onDestroy() method.
          *
          *

