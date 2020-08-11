package com.wjl.rxjavalearn.logd;

import android.util.Log;

import com.wjl.rxjavalearn.constant.AppConstant;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  09:37
 * Desc:
 */
public class LogForRxjavaUtils {
    public static void LogD(String str){
        Log.d(AppConstant.TAG_RXJAVA,str);
    }
}
