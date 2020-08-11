package com.wjl.rxjavalearn.bean;

import com.wjl.rxjavalearn.logd.LogForRxjavaUtils;

/**
 * Author: wujinli
 * CreateDate: 2020/8/11  14:19
 * Desc:
 */
public class RegisterBean {
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
        LogForRxjavaUtils.LogD("翻译内容 = " + content.out);
    }
}
