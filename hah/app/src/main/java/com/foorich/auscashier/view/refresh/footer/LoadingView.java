package com.foorich.auscashier.view.refresh.footer;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.view.refresh.library.IBottomView;
import com.foorich.auscashier.view.refresh.utils.DensityUtil;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 11:47
 * desc   :
 * version: 1.0
 */
public class LoadingView extends ImageView implements IBottomView {

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int size = DensityUtil.dp2px(context,34);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size,size);
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        setImageResource(R.drawable.anim_loading_view);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        ((AnimationDrawable)getDrawable()).start();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
