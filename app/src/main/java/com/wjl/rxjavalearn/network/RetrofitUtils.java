package com.wjl.rxjavalearn.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wjl.rxjavalearn.constant.AppConstant;
import com.wjl.rxjavalearn.iservice.IService;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  09:23
 * Desc:
 */
public class RetrofitUtils {

    private static RetrofitUtils instance;
    private Retrofit retrofit;

    private RetrofitUtils() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.URL_BASE)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static RetrofitUtils getInstance() {
        synchronized (RetrofitUtils.class) {
            if (instance == null) {
                synchronized (RetrofitUtils.class) {
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }

    public IService getService() {
        return retrofit.create(IService.class);
    }


    public void execute(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
