package com.wjl.rxjavalearn.observablecreateoperator;


import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author: wujinli
 * CreateDate: 2020/8/12  16:12
 * Desc: Observable创建操作符
 */
public class ObservableCreateLearn {
    static String content = "第一次赋值";
    static Disposable disposable;


    /****************************基本创建********************************/
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


    /****************************快速创建********************************/

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

    /****************************延迟创建********************************/

    /**
     * defer()
     * 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
     * 通过 Observable工厂方法创建被观察者对象（Observable）
     * 每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
     */
    public static void delayCreateObservableByDeferMethod() {

        Observable<String> observable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just(content);
            }
        });

        content = "第二次赋值";

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("开始采用subscribe连接");
            }

            @Override
            public void onNext(String value) {
                LogForRxjavaUtils.LogD("observable发送的事件：" + value);
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
     * timer（）
     * 快速创建1个被观察者对象（Observable）
     * 发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）
     */
    public static void delayCreateObservableByTimerMethod() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogForRxjavaUtils.LogD("开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        LogForRxjavaUtils.LogD("observable发送的事件：" + value);
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
     * interval（）
     * 发送事件的特点：每隔指定时间 就发送 事件
     * 发送的事件序列 = 从0开始、无限递增1的的整数序列
     * 参数说明：
     * 参数1 = 第1次延迟时间；
     * 参数2 = 间隔时间数字；
     * 参数3 = 时间单位；
     */
    public static void delayCreateObservableByIntervalMethod() {

        /**
         * 使用interval实现倒计时10秒
         */
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        LogForRxjavaUtils.LogD("开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        if (value > 9) {
                            disposable.dispose();
                            onComplete();
                            return;
                        }
                        LogForRxjavaUtils.LogD("observable发送的事件：" + value);
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
     * intervalRange（）
     * 发送事件的特点：每隔指定时间 就发送 事件，可指定发送的数据的数量
     * a. 发送的事件序列 = 从0开始、无限递增1的的整数序列
     * b. 作用类似于interval（），但可指定发送的数据的数量
     * <p>
     * /参数说明：
     * 参数1 = 事件序列起始点；
     * 参数2 = 事件数量；
     * 参数3 = 第1次事件延迟发送时间；
     * 参数4 = 间隔时间数字；
     * 参数5 = 时间单位
     */
    public static void delayCreateObservableByIntervalRangeMethod() {
        Observable.intervalRange(1, 10, 0, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogForRxjavaUtils.LogD("onSubscribe");
                    }

                    @Override
                    public void onNext(Long value) {
                        LogForRxjavaUtils.LogD("observable发送的对象" + value);
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
     * range（）
     * 连续发送 1个事件序列，可指定范围
     * <p>
     * 发送的事件序列 = 从0开始、无限递增1的的整数序列
     * 作用类似于intervalRange（），但区别在于：无延迟发送事件
     * <p>
     * 参数说明：
     * 参数1 = 事件序列起始点；
     * 参数2 = 事件数量；
     * 注：若设置为负数，则会抛出异常
     */
    public static void delayCreateObservableByRangeMethod() {
        Observable.range(1, 10).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                if (value == 1) {
                    LogForRxjavaUtils.LogD("value的数据类型" + value.getClass().getName());
                }
                LogForRxjavaUtils.LogD("observable发送事件:" + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LogForRxjavaUtils.LogD(e.toString());
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("onCompelte");
            }
        });
    }


    /**
     * rangeLong（）
     * 作用：类似于range（），区别在于该方法支持数据类型 = Long
     * 具体使用
     * 与range（）类似，此处不作过多描述
     */
    public static void delayCreateObservableByRangeLongMethod() {
        Observable.rangeLong(1, 6).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(Long value) {
                if (value == 1) {
                    LogForRxjavaUtils.LogD("value的数据类型" + value.getClass().getName());
                }
                LogForRxjavaUtils.LogD("observable发送事件:" + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LogForRxjavaUtils.LogD(e.toString());
            }

            @Override
            public void onComplete() {
                LogForRxjavaUtils.LogD("onCompelte");
            }
        });
    }
}
