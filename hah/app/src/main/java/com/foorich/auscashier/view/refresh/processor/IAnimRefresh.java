package com.foorich.auscashier.view.refresh.processor;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 13:44
 * desc   :
 * version: 1.0
 */
public interface IAnimRefresh {
    void scrollHeadByMove(float moveY);
    void scrollBottomByMove(float moveY);
    void animHeadToRefresh();
    void animHeadBack(boolean isFinishRefresh);
    void animHeadHideByVy(int vy);
    void animBottomToLoad();
    void animBottomBack(boolean isFinishRefresh);
    void animBottomHideByVy(int vy);
}
