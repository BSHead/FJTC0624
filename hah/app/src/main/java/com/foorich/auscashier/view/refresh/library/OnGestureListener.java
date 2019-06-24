package com.foorich.auscashier.view.refresh.library;

import android.view.MotionEvent;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 13:40
 * desc   :
 * version: 1.0
 */
public interface OnGestureListener {
    void onDown(MotionEvent ev);

    void onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY);

    void onUp(MotionEvent ev, boolean isFling);

    void onFling(MotionEvent downEvent, MotionEvent upEvent, float velocityX, float velocityY);
}
