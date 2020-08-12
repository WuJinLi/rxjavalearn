package com.wjl.rxjavalearn.observablecreate;


import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author: wujinli
 * CreateDate: 2020/8/12  16:12
 * Desc:
 */
public class ObservableCreateLearn {


    /**
     * 基本创建方法create()
     */
    public static void createObservableByCreateMethod() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("基本创建方法创建observable对象");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                LogForRxjavaUtils.LogD("observer接收到的信息：" + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * just（）直接发送 传入的事件，最多只能发送10个参数
     * // 在创建后就会发送这些对象，相当于执行了onNext()
     */
    public static void quickCreateObservableByJustMethod() {
        final StringBuilder result = new StringBuilder("just()方法创建的observable发送的事件：");
        Observable.just(1, 2, 3, 4).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                result.append(value).append(" ");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD(result.toString());
            }
        });
    }

    /**
     * fromArray（）
     * 发送事件的特点：直接发送 传入的数组数据
     * 会将数组中的数据转换为Observable对象
     * <p>
     * 可以实现数组元素的遍历
     */
    public static void quickCreateObservableByFromArrayMethod() {
        String[] items = {"a", "b", "c", "d", "e", "f"};

        Observable.fromArray(items).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("遍历开始");
            }

            @Override
            public void onNext(String value) {
                LogForRxjavaUtils.LogD("items元素:" + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("遍历结束");
            }
        });
    }


    /**
     * fromIterable()
     * 发送事件的特点：直接发送 传入的集合List数据
     * 会将数组中的数据转换为Observable对象
     */
    public static void quickCreateObservableByFromIterableMethod() {
        List<Integer> items = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            items.add(i);
        }


        Observable.fromIterable(items).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("遍历开始");
            }

            @Override
            public void onNext(Integer value) {
                LogForRxjavaUtils.LogD("items元素:" + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("遍历结束");
            }
        });
    }

    /**
     * empty()
     * 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
     */
    public static void quickCreateObservableByEmptyMethod() {
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                LogForRxjavaUtils.LogD(value.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogForRxjavaUtils.LogD(e.toString());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("onComplete");
            }
        });
    }


    /**
     * error()
     * 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
     * 可自定义异常
     */
    public static void quickCreateObservableByErrorMethod() {
        Observable.error(new Throwable("异常操作的error")).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                LogForRxjavaUtils.LogD(value.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogForRxjavaUtils.LogD(e.toString());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("onComplete");
            }
        });
    }


    /**
     * <-- never()  -->
     * // 该方法创建的被观察者对象发送事件的特点：不发送任何事件
     * // 即观察者接收后什么都不调用
     */
    public static void quickCreateObservableByNeverMethod() {
        Observable.never().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                LogForRxjavaUtils.LogD(value.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogForRxjavaUtils.LogD(e.toString());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("onComplete");
            }
        });
    }
}
