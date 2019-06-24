package com.foorich.auscashier.view.refresh.utils;

import android.util.Log;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 13:49
 * desc   :
 * version: 1.0
 */
public class LogUtil {
    private static final boolean DEBUG = false;

    public static void i(String msg) {
        if (!DEBUG) return;
        Log.i("TwinklingRefreshLayout", msg);
    }
}
