package com.foorich.auscashier.view.refresh.processor;

import android.view.MotionEvent;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 13:45
 * desc   :
 * version: 1.0
 */
public interface IDecorator {

    boolean dispatchTouchEvent(MotionEvent ev);

    boolean interceptTouchEvent(MotionEvent ev);

    boolean dealTouchEvent(MotionEvent ev);

    void onFingerDown(MotionEvent ev);

    void onFingerUp(MotionEvent ev, boolean isFling);

    void onFingerScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY, float velocityX, float velocityY);

    void onFingerFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);

}
