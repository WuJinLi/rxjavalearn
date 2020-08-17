package com.wjl.rxjavalearn.combinationormergeopterator;

import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: wujinli
 * CreateDate: 2020/8/13  16:32
 * Desc: 组合/合并操作符
 */
public class ObservableCombinationMergeOpterator {


    /**
     * concat（）
     * 组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
     * concat（）组合被观察者数量≤4个
     */
    public static void ObservableConcat() {
        Observable.concat(
                Observable.just(1, 2, 3),
                Observable.just(5, 6, 7),
                Observable.just(4, 8, 9),
                Observable.just(10, 11, 12)
        ).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                LogForRxjavaUtils.LogD("接受到的事件:" + value);
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
     * concatArray()
     * 组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
     * concatArray（）组合被观察者数量>4个
     */
    public static void ObservableConcatArray() {
        Observable.concatArray(
                Observable.just(1),
                Observable.just(2),
                Observable.just(3),
                Observable.just(4),
                Observable.just(5),
                Observable.just(6)
        ).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                LogForRxjavaUtils.LogD("接受到的事件:" + value);
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
     * merge（）
     * 组合多个被观察者一起发送数据，合并后 按时间线并行执行
     * merge（）组合被观察者数量≤4个
     */
    public static void ObservableMerge() {
        Observable.merge(
                Observable.intervalRange(1, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(4, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(7, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(11, 3, 1, 1, TimeUnit.SECONDS)

        ).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(Long value) {
                LogForRxjavaUtils.LogD("接受到的事件:" + value);
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
     * mergeArray()
     * 组合多个被观察者一起发送数据，合并后 按时间线并行执行
     * mergeArray（）组合被观察者数量≤4个
     */
    public static void ObservableMergeArray() {
        Observable.mergeArray(
                Observable.intervalRange(1, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(4, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(7, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(10, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(13, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(16, 3, 1, 1, TimeUnit.SECONDS)

        ).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(Long value) {
                LogForRxjavaUtils.LogD("接受到的事件:" + value);
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
     * Zip（）
     * 合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送
     * 特别注意：
     * 事件组合方式 = 严格按照原先事件序列 进行对位合并
     * 最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
     */
    public static void ObservableZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                LogForRxjavaUtils.LogD("被观察者1发送了事件1");
                e.onNext(1);
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);

                LogForRxjavaUtils.LogD("被观察者1发送了事件2");
                e.onNext(2);
                Thread.sleep(1000);

                LogForRxjavaUtils.LogD("被观察者1发送了事件3");
                e.onNext(3);
                Thread.sleep(1000);

                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());


        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogForRxjavaUtils.LogD("被观察者1发送了事件1");
                e.onNext("A");
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);

                LogForRxjavaUtils.LogD("被观察者1发送了事件2");
                e.onNext("B");
                Thread.sleep(1000);

                LogForRxjavaUtils.LogD("被观察者1发送了事件3");
                e.onNext("C");
                Thread.sleep(1000);

                e.onNext("D");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());


        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                LogForRxjavaUtils.LogD("收到事件" + value);
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
     * combineLatest（）
     * 当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据
     * 与Zip（）的区别：Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并
     */
    public static void operatorCombineLatest() {
        Observable.combineLatest(Observable.just(1l, 2l, 3l),
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long aLong, Long aLong2) throws Exception {
                        // aLong = 第1个Observable发送的最新（最后）1个数据
                        // aLong2 = 第2个Observable发送的每1个数据
                        LogForRxjavaUtils.LogD("合并的数据是： " + aLong + " " + aLong2);
                        return aLong + aLong2;
                    }
                }
        ).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogForRxjavaUtils.LogD("onSubscribe");
            }

            @Override
            public void onNext(Long value) {
                LogForRxjavaUtils.LogD("合并的结果是： " + value);
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
     * reduce（）
     * 把被观察者需要发送的事件聚合成1个事件 & 发送
     */
    public static void operatorReduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        LogForRxjavaUtils.LogD("本次操作的数据是" + integer + "," + integer2);
                        return integer * integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogForRxjavaUtils.LogD("使用reduce操作符输出结果 " + integer);
            }
        });
    }

    /**
     * collect（）
     * 将被观察者Observable发送的数据事件收集到一个数据结构里
     */
    public static void operatorCollect() {
//        Observable.just(1, 2, 3, 4, 5, 6)
//                .collect(new Callable<ArrayList<Integer>>() {
//                    @Override
//                    public ArrayList<Integer> call() throws Exception {
//                        return new ArrayList<>();
//                    }
//                }, new BiConsumer<ArrayList<Integer>, Integer>() {
//                    @Override
//                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
//                        integers.add(integer);
//                    }
//                }).subscribe(new Consumer<ArrayList<Integer>>() {
//            @Override
//            public void accept(ArrayList<Integer> integers) throws Exception {
//                LogForRxjavaUtils.LogD("本次接收数据" + integers);
//            }
//        });


        Observable.just(1, 2, 3, 4)
                .collect(new Callable<StringBuilder>() {

                    @Override
                    public StringBuilder call() throws Exception {
                        return new StringBuilder();
                    }
                }, new BiConsumer<StringBuilder, Integer>() {
                    @Override
                    public void accept(StringBuilder stringBuilder, Integer integer) throws Exception {
                        stringBuilder.append(integer);
                    }
                }).subscribe(new Consumer<StringBuilder>() {
            @Override
            public void accept(StringBuilder stringBuilder) throws Exception {
                LogForRxjavaUtils.LogD(stringBuilder.toString());
            }
        });

    }


    /**
     * 发送事件前追加发送事件
     * startWith（） / startWithArray（）
     * 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
     */
    public static void operatorStartWith() {
//        Observable.just(1,2,3,4)
//                .startWith(0)
//                .startWithArray(5,6,7,8)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        LogForRxjavaUtils.LogD("onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        LogForRxjavaUtils.LogD("接收的数据"+value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogForRxjavaUtils.LogD(e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        LogForRxjavaUtils.LogD("onComplete");
//                    }
//                });

        Observable.just(123)
                .startWith(Observable.just(333))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogForRxjavaUtils.LogD("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer value) {
                        LogForRxjavaUtils.LogD("接收数据：" + value);
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
     * count（）
     * 统计被观察者发送事件的数量
     */
    public static void operatorCount() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogForRxjavaUtils.LogD("统计个数:" + aLong);
                    }
                });
    }

}
