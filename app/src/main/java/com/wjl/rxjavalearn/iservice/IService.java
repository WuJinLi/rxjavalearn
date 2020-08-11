package com.wjl.rxjavalearn.iservice;

import com.wjl.rxjavalearn.bean.TranslationBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  09:16
 * Desc:
 */
public interface IService {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<TranslationBean> getCall();
}
