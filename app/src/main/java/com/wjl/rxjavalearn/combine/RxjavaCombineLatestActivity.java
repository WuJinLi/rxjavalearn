package com.wjl.rxjavalearn.combine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.wjl.rxjavalearn.R;
import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;


/**
 * Author: wujinli
 * CreateDate: 2020/8/11  16:31
 * Desc: rxjava实现控件内容输入内容监控，实现联合判断多个事件
 */
public class RxjavaCombineLatestActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name, et_age, et_address;
    private Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rxjava_combine);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_address = findViewById(R.id.et_address);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);


        /*
         * 为每个EditText设置被观察者，用于发送监听事件
         * 说明：
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         **/
        Observable<CharSequence> nameObservable = RxTextView.textChanges(et_name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(et_age).skip(1);
        Observable<CharSequence> addressObserable = RxTextView.textChanges(et_address).skip(1);


        Observable.combineLatest(nameObservable, ageObservable, addressObserable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                LogForRxjavaUtils.LogD(charSequence.toString());
                LogForRxjavaUtils.LogD(charSequence2.toString());
                LogForRxjavaUtils.LogD(charSequence3.toString());


                boolean nameflag = !TextUtils.isEmpty(et_name.getText());
                boolean ageflag = !TextUtils.isEmpty(et_age.getText());
                boolean addressflag = !TextUtils.isEmpty(et_address.getText());
                return nameflag && ageflag && addressflag;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                LogForRxjavaUtils.LogD("按钮" + aBoolean);
                btn_submit.setEnabled(aBoolean);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                break;
            default:
                break;
        }
    }
}
