package com.wjl.rxjavalearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wjl.rxjavalearn.simpletouse.SimpleToUseRxjava;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewsAndActions();
    }

    private void initViewsAndActions() {
        findViewById(R.id.btn_simple_to_use).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_to_use:
//                SimpleToUseRxjava.simpleToUseRxjava();
                SimpleToUseRxjava.chainCalls();
                break;
            default:
                break;
        }
    }
}
