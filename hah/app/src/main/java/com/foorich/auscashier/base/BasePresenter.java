package com.foorich.auscashier.base;

import android.content.Context;

import com.foorich.auscashier.baserx.RxManager;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 10:51
 * desc   :
 * version: 1.0
 */
public abstract class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {
    }

    ;

    public void onDestroy() {
        mRxManage.clear();
    }
}
