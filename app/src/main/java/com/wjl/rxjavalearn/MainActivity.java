package com.wjl.rxjavalearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wjl.rxjavalearn.mergedatas.MergeMoreData;
import com.wjl.rxjavalearn.polling.RxjavaPollingLearn;
import com.wjl.rxjavalearn.retrywhen.RxjavaRetryWhenLearn;
import com.wjl.rxjavalearn.simpletouse.SimpleToUseRxjava;

import flatmap.EventsNested;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_to_use:
//                SimpleToUseRxjava.simpleToUseRxjava();
                SimpleToUseRxjava.chainCalls();
                break;
            case R.id.btn_polling:
//                RxjavaPollingLearn.unconditionalPolling();
                RxjavaPollingLearn.conditionalPolling();
                break;

            case R.id.btn_retry:
                RxjavaRetryWhenLearn.retryWhen();
                break;

            case R.id.btn_events_nested:
                EventsNested.eventsNested();
                break;

            case R.id.btn_merge_data:
//                MergeMoreData.mergeMoreDatas();
                MergeMoreData.mergeDataByZip();
                break;
            default:
                break;
        }
    }
}
