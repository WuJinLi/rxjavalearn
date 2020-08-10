package com.wjl.rxjavalearn.simpletouse;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 创建被观察者 （Observable ）& 生产事件
 * 创建观察者 （Observer ）并 定义响应事件的行为
 * 通过订阅（Subscribe）连接观察者和被观察者
 */
public class SimpleToUseRxjava {

    public static final String TAG = "Rxjava";

    /**
     * 简单调用
     */
    public static void simpleToUseRxjava() {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("zhangsan");
                e.onNext("lisi");
                e.onNext("wangwu");
                e.onComplete();
            }
        });


        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };


        observable.subscribe(observer);
    }

    /**
     * 链式调用-》基于事件流
     */
    public static void chainCalls(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(11);
                e.onNext(33);
                e.onNext(44);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

}
