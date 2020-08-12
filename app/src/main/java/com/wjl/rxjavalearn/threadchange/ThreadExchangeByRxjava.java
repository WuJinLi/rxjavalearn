package com.wjl.rxjavalearn.threadchange;


import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  17:43
 * Desc:
 */
public class ThreadExchangeByRxjava {


    /**
     * 被观察者 （Observable）/ 观察者（Observer）的工作线程 = 创建自身的线程
     * 不指定obverable与observer线程，则默认为主线程
     */
    public static void threadExchangeofDefaulThreadIn() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                LogForRxjavaUtils.LogD(" 被观察者 Observable的工作线程是: " + Thread.currentThread().getName());
                e.onNext(11);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD(" 观察者 Observer的工作线程是: " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Integer value) {
                LogForRxjavaUtils.LogD("对Next事件" + value + "作出响应");
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


    /**
     * 指定观察者与被观察者所在线程
     * 被观察者 （Observable） 在 子线程 中生产事件（如实现耗时操作等等）
     * 观察者（Observer）在 主线程 接收 & 响应事件（即实现UI操作）
     * <p>
     * 采用 RxJava内置的线程调度器（ Scheduler ），即通过 功能性操作符subscribeOn（） & observeOn（）实现
     * <p>
     * Observable.subscribeOn（Schedulers.Thread）：指定被观察者 发送事件的线程（传入RxJava内置的线程类型）
     * Observable.observeOn（Schedulers.Thread）：指定观察者 接收 & 响应事件的线程（传入RxJava内置的线程类型）
     *
     * 注意：
     * 若Observable.subscribeOn（）多次指定被观察者 生产事件的线程，则只有第一次指定有效，其余的指定线程无效
     * 若Observable.observeOn（）多次指定观察者 接收 & 响应事件的线程，则每次指定均有效，即每指定一次，就会进行一次线程的切换
     */
    public static void threadExchangeOfAssignThread() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        LogForRxjavaUtils.LogD(" 被观察者 Observable的工作线程是: " + Thread.currentThread().getName());
                        e.onNext(11);
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())//指定被观察者，生产线程=新线程
                .observeOn(AndroidSchedulers.mainThread())//指定观察者线程=主线程
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogForRxjavaUtils.LogD(" 观察者 Observer的工作线程是: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer value) {
                        LogForRxjavaUtils.LogD("对Next事件" + value + "作出响应");
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
