package com.wjl.rxjavalearn.polling;


import com.wjl.rxjavalearn.bean.TranslationBean;
import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;
import com.wjl.rxjavalearn.network.RetrofitUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  09:33
 * Desc: rxjava轮询-》无条件，有条件
 */
public class RxjavaPollingLearn {
    private static int i;

    /**
     * 无条件轮询
     * 采用interval（）延迟发送
     * 此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
     */
    public static void unconditionalPolling() {
        Observable.interval(5, 3, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogForRxjavaUtils.LogD("第 " + aLong + " 次轮询");

                        RetrofitUtils.getInstance().execute(RetrofitUtils.getInstance().getService().getCall(), new Observer<TranslationBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(TranslationBean value) {
                                value.show();
                            }


                            @Override
                            public void onError(Throwable e) {
                                LogForRxjavaUtils.LogD("请求失败");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                LogForRxjavaUtils.LogD("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("对Complete事件作出响应");
            }
        });
    }


    /**
     * 有条件轮询，repeatWhen实现将上游的Observable进行转换处理完成制定条件的的操作
     */
    public static void conditionalPolling() {

        RetrofitUtils.getInstance().execute(RetrofitUtils.getInstance().getService().getCall().repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        if (i > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });
            }
        }), new Observer<TranslationBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TranslationBean value) {
                value.show();
                i++;
            }

            @Override
            public void onError(Throwable e) {
                LogForRxjavaUtils.LogD(e.toString());
            }

            @Override
            public void onComplete() {

            }
        });


    }
}
