package com.wjl.rxjavalearn.lenovosearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.wjl.rxjavalearn.R;
import com.wjl.rxjavalearn.bean.TranslationBean;
import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;
import com.wjl.rxjavalearn.network.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LenovoSearchActivity extends AppCompatActivity {

    private EditText et_content;
    private TextView tv_translate_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_lenovo_search);
        initViews();
        searchTranslate();
    }

    private void searchTranslate() {
        RxTextView.textChanges(et_content)
                .debounce(50, TimeUnit.MICROSECONDS).skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence value) {
                        if (TextUtils.isEmpty(value.toString())) {
                            tv_translate_content.setText("");
                            return;
                        }

                        LogForRxjavaUtils.LogD(value.toString());

                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("a", "fy");
                        dataMap.put("f", "auto");
                        dataMap.put("t", "auto");
                        dataMap.put("w", value.toString());

                        RetrofitUtils.getInstance().execute(RetrofitUtils.getInstance().getService().translate(dataMap), new Observer<TranslationBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(TranslationBean value) {
                                tv_translate_content.setText(value.toString());
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

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initViews() {
        tv_translate_content = findViewById(R.id.tv_translate_content);
        et_content = findViewById(R.id.et_content);
    }
}
