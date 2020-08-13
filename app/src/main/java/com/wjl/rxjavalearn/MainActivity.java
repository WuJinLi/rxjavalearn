package com.wjl.rxjavalearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.wjl.rxjavalearn.combine.RxjavaCombineLatestActivity;
import com.wjl.rxjavalearn.lenovosearch.LenovoSearchActivity;
import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;
import com.wjl.rxjavalearn.mergedatas.MergeMoreData;
import com.wjl.rxjavalearn.observableconversionoperator.ObservableConversionLearn;
import com.wjl.rxjavalearn.observablecreateoperator.ObservableCreateLearn;
import com.wjl.rxjavalearn.polling.RxjavaPollingLearn;
import com.wjl.rxjavalearn.retrywhen.RxjavaRetryWhenLearn;
import com.wjl.rxjavalearn.simpletouse.SimpleToUseRxjava;

import com.wjl.rxjavalearn.flatmap.EventsNested;
import com.wjl.rxjavalearn.threadchange.ThreadExchangeByRxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_clickfast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewsAndActions();
    }

    private void initViewsAndActions() {
        findViewById(R.id.btn_simple_to_use).setOnClickListener(this);
        findViewById(R.id.btn_polling).setOnClickListener(this);
        findViewById(R.id.btn_retry).setOnClickListener(this);
        findViewById(R.id.btn_events_nested).setOnClickListener(this);
        findViewById(R.id.btn_merge_data).setOnClickListener(this);
        findViewById(R.id.btn_combine).setOnClickListener(this);
        findViewById(R.id.btn_thread_exchange).setOnClickListener(this);
        findViewById(R.id.btn_lenovo_search).setOnClickListener(this);
        findViewById(R.id.btn_observable_create).setOnClickListener(this);
        findViewById(R.id.btn_observable_conversion).setOnClickListener(this);
        btn_clickfast = findViewById(R.id.btn_clickfast);
        btn_clickfast.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_to_use:
                SimpleToUseRxjava.chainCalls();
                break;
            case R.id.btn_polling:
                RxjavaPollingLearn.conditionalPolling();
                break;

            case R.id.btn_retry:
                RxjavaRetryWhenLearn.retryWhen();
                break;

            case R.id.btn_events_nested:
                EventsNested.eventsNested();
                break;

            case R.id.btn_merge_data:
                MergeMoreData.mergeDataByZip();
                break;
            case R.id.btn_combine:
                startActivity(new Intent(MainActivity.this, RxjavaCombineLatestActivity.class));
                break;

            case R.id.btn_thread_exchange:
                ThreadExchangeByRxjava.threadExchangeOfAssignThread();
                break;

            case R.id.btn_clickfast:
                RxView.clicks(btn_clickfast)
                        .throttleLast(2, TimeUnit.SECONDS)
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Object value) {
                                LogForRxjavaUtils.LogD("点击事件出发");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogForRxjavaUtils.LogD(e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;

            case R.id.btn_lenovo_search:
                startActivity(new Intent(MainActivity.this, LenovoSearchActivity.class));
                break;

            case R.id.btn_observable_create:
                ObservableCreateLearn.delayCreateObservableByRangeLongMethod();
                break;


            case R.id.btn_observable_conversion:
                ObservableConversionLearn.operatorOfConcatMap();
                break;
            default:
                break;
        }
    }
}
