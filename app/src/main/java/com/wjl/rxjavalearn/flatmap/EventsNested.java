package com.wjl.rxjavalearn.flatmap;

import com.wjl.rxjavalearn.bean.LoginBean;
import com.wjl.rxjavalearn.bean.RegisterBean;
import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;
import com.wjl.rxjavalearn.network.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  14:15
 * Desc: 事件流嵌套问题，多个事件流嵌套执行  事件1（完成）->事件2（完成）->事件3（完成）
 */
public class EventsNested {
    public static void eventsNested() {

        Observable registerObservable = RetrofitUtils.getInstance().getService().register();
        final Observable loginObservable = RetrofitUtils.getInstance().getService().login();


        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.clear();


        registerObservable
                .subscribeOn(Schedulers.io())// （初始被观察者）切换到IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread())// （新观察者）切换到主线程 处理网络请求1的结果
                .doOnNext(new Consumer<RegisterBean>() {
                    @Override
                    public void accept(RegisterBean registerBean) throws Exception {
                        LogForRxjavaUtils.LogD("注册网络请求成功");
                        registerBean.show();
                    }
                })
                .observeOn(Schedulers.io())//新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，它则是新的被观察者
                .flatMap(new Function<RegisterBean, ObservableSource<LoginBean>>() {
                    //实用flatmap进行observable的对象切换
                    @Override
                    public ObservableSource<LoginBean> apply(RegisterBean registerBean) throws Exception {
                        if (registerBean == null) {
                            return Observable.error(new Throwable("注册失败"));
                        } else {
                            return loginObservable;
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        LogForRxjavaUtils.LogD("登陆网络请求成功");
                        loginBean.show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogForRxjavaUtils.LogD(throwable.toString());
                    }
                });

    }
}
