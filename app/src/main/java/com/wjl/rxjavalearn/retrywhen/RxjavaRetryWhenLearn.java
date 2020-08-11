package com.wjl.rxjavalearn.retrywhen;

import com.wjl.rxjavalearn.bean.TranslationBean;
import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;
import com.wjl.rxjavalearn.network.RetrofitUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  11:12
 * Desc: 使用rxjava实现重连
 */
public class RxjavaRetryWhenLearn {
    // 可重试次数
    private static int maxConnectCount = 10;
    // 当前已重试次数
    private static int currentRetryCount = 0;
    // 重试等待时间
    private static int waitRetryTime = 0;

    public static void retryWhen() {
        // 注：主要异常才会回调retryWhen（）进行重试
        RetrofitUtils.getInstance().execute(
                RetrofitUtils.getInstance().getService().getCall().retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {

                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {

                                LogForRxjavaUtils.LogD("发生异常=" + throwable.toString());
                                if (throwable instanceof IOException) {
                                    LogForRxjavaUtils.LogD("属于IO异常，需重新尝试");

                                    if (currentRetryCount < maxConnectCount) {
                                        currentRetryCount++;
                                        LogForRxjavaUtils.LogD("重试次数 =" + currentRetryCount);
                                        //通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                        waitRetryTime = 1000 + currentRetryCount * 1000;
                                        LogForRxjavaUtils.LogD("等待时间=" + waitRetryTime);
                                        return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
                                    } else {
                                        // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                        return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));
                                    }
                                } else {
                                    // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                                    return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                                }
                            }
                        });
                    }
                }), new Observer<TranslationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TranslationBean value) {
                        // 接收服务器返回的数据
                        LogForRxjavaUtils.LogD("发送成功");
                        value.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogForRxjavaUtils.LogD(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }


        );
    }
}
