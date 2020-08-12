package com.wjl.rxjavalearn.iservice;

import com.wjl.rxjavalearn.bean.LoginBean;
import com.wjl.rxjavalearn.bean.RegisterBean;
import com.wjl.rxjavalearn.bean.TranslationBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  09:16
 * Desc:
 */
public interface IService {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<TranslationBean> getCall();


    // 网络请求1
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<RegisterBean> register();

    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<LoginBean> login();




    @GET("ajax.php?")
    Observable<TranslationBean> translate(@QueryMap Map<String,Object> data);
}
