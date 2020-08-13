package com.wjl.rxjavalearn.observableconversionoperator;

import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Author: wujinli
 * CreateDate: 2020/8/13  11:12
 * Desc:observable变换操作符
 */
public class ObservableConversionLearn {

    /**
     * Map（）
     * 对 被观察者发送的每1个事件都通过 指定的函数 处理，从而变换成另外一种事件,即，将被观察者发送的事件转换为任意的类型事件
     * <p>
     * 应用场景：数据类型转换
     */
    public static void operatorOfMap() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(2);
                        e.onComplete();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        LogForRxjavaUtils.LogD("转换前的数据格式" + integer.getClass().getName());
                        //通过map方法实现数据类型的转换
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogForRxjavaUtils.LogD("转换后的数据格式" + s.getClass().getName());
                    }
                });
    }

    /**
     * FlatMap（）
     * 作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
     * <p>
     * 原理
     * 为事件序列中每个事件都创建一个 Observable 对象；
     * 将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
     * 新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
     * <p>
     * 注：
     * 新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关
     */
    public static void operatorOfFlatMap() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
                        e.onComplete();
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                        for (int index = 0; index < 2; index++) {
                            list.add("我是事件 " + integer + "拆分后的子事件" + index);
                        }
                        // 最终合并，再发送给被观察者
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogForRxjavaUtils.LogD(s);
                    }
                });
    }


    /**
     * concatMap（）
     * 作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
     * <p>
     * 原理
     * 为事件序列中每个事件都创建一个 Observable 对象；
     * 将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
     * 新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
     * <p>
     * 注：
     * 新合并生成的事件序列顺序是有序的，即 严格按照旧序列发送事件的顺序
     */
    public static void operatorOfConcatMap() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
                        e.onComplete();
                    }
                })
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                        for (int index = 0; index < 2; index++) {
                            list.add("我是事件 " + integer + "拆分后的子事件" + index);
                        }
                        // 最终合并，再发送给被观察者
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogForRxjavaUtils.LogD(s);
                    }
                });
    }
}
