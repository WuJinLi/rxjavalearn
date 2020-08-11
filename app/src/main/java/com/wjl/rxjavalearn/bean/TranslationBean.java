package com.wjl.rxjavalearn.bean;

import android.util.Log;

import com.wjl.rxjavalearn.constant.AppConstant;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  09:21
 * Desc:
 */
public class TranslationBean {
    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d(AppConstant.TAG_RXJAVA, content.out );
    }
}
