package com.wjl.rxjavalearn.mergedatas;

import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  15:08
 * Desc: 使用rxjava的merge实现多个数据源数据的整合和展示
 */
public class MergeMoreData {

    public static String localData = "本地数据源数据";
    public static String netData = "本地数据源数据";
    public static StringBuilder result;


    /**
     * 使用merge操作符完成数据的合并
     */
    public static void mergeMoreDatas() {
        result = new StringBuilder("数据来源：");
        Observable<String> localObservable = Observable.just(localData);
        Observable<String> daraFromNet = Observable.just(netData);
        Observable.merge(localObservable, daraFromNet)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        LogForRxjavaUtils.LogD("当前数据来源：" + value);
                        result.append(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogForRxjavaUtils.LogD(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        LogForRxjavaUtils.LogD(result.toString());
                    }
                });
    }


    /**
     * 使用操作符zip实现数据的合并
     */
    public static void mergeDataByZip() {
        result = new StringBuilder("数据来源：");
        Observable<String> localObservable = Observable.just(localData);
        Observable<String> daraFromNet = Observable.just(netData);

        Observable.zip(localObservable, daraFromNet, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return result.append(s).append(s2).toString();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                LogForRxjavaUtils.LogD(value);
            }

            @Override
            public void onError(Throwable e) {
                LogForRxjavaUtils.LogD(e.toString());
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("onComplete");
            }
        });
    }
}
