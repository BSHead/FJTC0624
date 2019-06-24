package com.foorich.auscashier.base.baseadapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 10:58
 * desc   :
 * version: 1.0
 */
public class ScaleInAnimation implements BaseAnimation {

    private static final float DEFAULT_SCALE_FROM = .5f;
    private final float mFrom;

    public ScaleInAnimation() {
        this(DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
        return new ObjectAnimator[]{scaleX, scaleY};
    }
}
