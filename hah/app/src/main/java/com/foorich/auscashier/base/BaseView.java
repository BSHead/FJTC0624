package com.foorich.auscashier.base;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 10:52
 * desc   :
 * version: 1.0
 */
public interface BaseView {

    /*******内嵌加载*******/
    void showLoading(String title);

    void stopLoading();

    void showErrorTip(String msg);
}
