package com.foorich.auscashier.view.refresh.library;

import android.view.View;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 13:39
 * desc   :
 * version: 1.0
 */
public interface IHeaderView {

    View getView();

    /**
     * 下拉准备刷新动作
     *
     * @param fraction      当前下拉高度与总高度的比
     * @param maxHeadHeight
     * @param headHeight
     */
    void onPullingDown(float fraction, float maxHeadHeight, float headHeight);

    /**
     * 下拉释放过程
     *
     * @param fraction
     * @param maxHeadHeight
     * @param headHeight
     */
    void onPullReleasing(float fraction, float maxHeadHeight, float headHeight);

    void startAnim(float maxHeadHeight, float headHeight);

    void onFinish(OnAnimEndListener animEndListener);

    /**
     * 用于在必要情况下复位View，清除动画
     */
    void reset();

}
